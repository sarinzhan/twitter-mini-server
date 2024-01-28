package gpt.Service;

import gpt.data.UserData;
import gpt.entitties.User;
import gpt.utils.TextDecorator;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class UserService {
    private UserData userData;
    private  Authenticator au;

    public UserService(UserData userData, Authenticator authenticator) {
        this.userData = userData;
        this.au = authenticator;
    }
    public String register(String req){
        try {
            String[] reg = req.substring(10).split(" ");
            User user = new User();
            user.setLogin(reg[0]);
            Optional<User> login = userData.getUserByLogin(user.getLogin());
            if(login.isPresent()){
                return TextDecorator.error("Пользователь с таким логин уже существует");
            }
            user.setPassword(reg[1]);
            user.setFirstName(reg[2]);
            user.setSecondName(reg[3]);
            System.out.println(reg[4].substring(0, 10) + "/");
            LocalDate birhDay = LocalDate.parse(reg[4].substring(0, 10), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            user.setBirthday(birhDay);
            Long l = userData.addNewUser(user);
            Optional<User> userByLogin = userData.getUserByLogin(user.getLogin());
            if(userByLogin.isPresent()){
                return TextDecorator.success("Успешная регистрация") +userByLogin.get();
            }
        }catch (Exception e){}

        return TextDecorator.error("Ошибка при регистрации");
    }

    public String login(String s, ChannelHandlerContext ctx) {
        try {
            String[] reg = s.substring(7, s.length() - 2).split(" ");
            String login = reg[0];
            String password = reg[1];
            Optional<User> userOptional = userData.getUserByLogin(login);
            if (userOptional.isPresent()) {
                User user = userOptional.get();
                if (user.getPassword().equals(password) && user.getLogin().equals(login)) {
                    au.setAuthUser(user,ctx);
                    return TextDecorator.success("Вы вошли в аккаунт");
                }
            }
            return TextDecorator.error("Неверный логин или пароль");
        }catch (Exception e){
            return TextDecorator.error("Ошибка при попытке авторизации");
        }


    }

    public String logout(ChannelHandlerContext ctx) throws Exception {
        if(Objects.isNull(au.getAuthUser())){
            return TextDecorator.error("Вы не авторизованы");
        }
        au.removeAuthUser();
        return  TextDecorator.success("Вы вышли из аккаунта");
    }

    public String curUserInfo() {
        if(Objects.isNull(au.getAuthUser())){
            return TextDecorator.error("Вы не авторизованы");
        }else{
            return TextDecorator.success("Текущий пользователь:\n") + au.getAuthUser().toString();
        }
    }

    public String infoByUserLogin(String s) {
        if(Objects.isNull(au.getAuthUser())){
            return TextDecorator.error("Вы не авторизованы");
        }
        //TODO проверить парсер и не только
        String query = s.substring(14,s.length()-2);
        Optional<User> optionalUser = userData.getUserByLogin(query);
        if(optionalUser.isEmpty()){
            return TextDecorator.error("Пользователь с таким логином не существует");
        }
        User user = optionalUser.get();
        return TextDecorator.success("Информация по логину " + query + ":\n") + user.toString();
    }

    public String allOnlineUsers() {
//        if(Objects.isNull(au.getAuthUser())){
//            return TextDecorator.error("Вы не авторизованы");
//        }
        //TODO проверить парсер и не только
        List<User> authUsers = au.getAuthUsers();
        authUsers.remove(au.getAuthUser());
        StringBuilder res = new StringBuilder();
        res.append(TextDecorator.success("Авторизованные пользователи:\n"));
        if(authUsers.isEmpty()){
            return TextDecorator.error("Никто не авторизован");
        }
        for(User user : authUsers){
            res.append(String.format("ID: %d; Name: %s\n", user.getId(), user.getFirstName()));
        }
        return res.toString();
    }

    public String textTo(String s) {
        if(Objects.isNull(au.getAuthUser())){
            return TextDecorator.error("Вы не авторизованы");
        }
        //TODO проверить парсер и не только
        String [] query = s.substring(5,s.length()-2).split(" ");
        String id = query[0];
        String text = query[1];
        Optional<User> userOptional = au.getAuthUsers()
                .stream().filter(x -> x.getId().equals(Long.parseLong(id)))
                .findFirst();
        if(userOptional.isEmpty()){
            return TextDecorator.error(String.format("Пользователь с id %s не найден", id));
        }
        Channel userChannel = au.getChannelByUser(userOptional.get());
        String formatedText = String.format("Message from %s: %s", au.getAuthUser().getFirstName(), text);
        userChannel.writeAndFlush(formatedText);
        return "";
    }
}
