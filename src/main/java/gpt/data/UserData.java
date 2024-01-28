package gpt.data;

import gpt.entitties.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserData {
    private List<User> userList;
    private Long index = 0L;

    public UserData() {
        this.userList = new ArrayList<>();
    }
    public Long addNewUser(User user){
        user.setId(++index);
        this.userList.add(user);
        return this.index;
    }
    public Optional<User> getUserByLogin(String login){
            return userList.stream().filter(x -> x.getLogin().equals(login)).findFirst();
    }
    public Optional<User> getUserById(Long id){
        return userList.stream().filter(x -> x.getId().equals(id)).findFirst();
    }
    public List<User> getAllUsers(){
        return this.userList;
    }
    public boolean deleteByLogin(String login){
        Optional<User> first = this.userList.stream().filter(x -> x.getLogin().equals(login)).findFirst();
        if(first.isEmpty()) {
            return false;
        }
        this.userList.remove(first.get());
        return true;
    }




}
