package gpt.Handler;

import gpt.Service.Authenticator;
import gpt.Service.UserService;
import gpt.utils.TextDecorator;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Objects;

public class UserHandler extends SimpleChannelInboundHandler<String> {
    private UserService userService;
    private Authenticator au;
    public UserHandler(UserService userService, Authenticator au){
        this.userService = userService;
        this.au = au;
    }
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush(TextDecorator.greeter());
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("[UserHandler][channelIncative]");
        if(Objects.nonNull(au.getAuthUser())) {
            System.out.println("[LOG][ChannelInactive] Удаление из списка авторизованых пользователей " + au.getAuthUser().getFirstName());
            au.removeAuthUser();
        }
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String s) throws Exception {
        String remoteAdress = ctx.channel().remoteAddress().toString();
        System.out.println("[Message from " + remoteAdress.substring(remoteAdress.length() - 5) + "]: ");
        if (s.startsWith("/register")) {
            ctx.writeAndFlush( userService.register(s));
        } else if (s.startsWith("/login")){
            //      /login <login> <password>
            ctx.writeAndFlush( userService.login(s,ctx));
        }else if(s.startsWith("/logout")){
            ctx.writeAndFlush(userService.logout(ctx));
        }else if(s.startsWith("/info")){
            ctx.writeAndFlush( userService.curUserInfo());
        }else if(s.startsWith("/info by login")){// TODO косяк с info совпадает
            //      /info by login <login>
            ctx.writeAndFlush( userService.infoByUserLogin(s));
        }else if(s.startsWith("/all online users")){
            ctx.writeAndFlush( userService.allOnlineUsers());
        }else if(s.startsWith("/msg")){
            //      /msg <user_id> <text>
            ctx.writeAndFlush( userService.textTo(s));
        }else if(s.startsWith("/msg all online users")){ //msg all online users <text>

        }
        else{
            ctx.pipeline().fireChannelRead(s);
        }

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("[UserHandler][exceptionCaught]");
        //TODO проверить при исключении удаляется ли пользователь
        if(Objects.nonNull(au.getAuthUser())) {
            System.out.println("[LOG][Exception] Удаление из списка авторизованых пользователей " + au.getAuthUser().getFirstName());
            au.removeAuthUser();
        }

        cause.printStackTrace();
        ctx.close();
    }
}
