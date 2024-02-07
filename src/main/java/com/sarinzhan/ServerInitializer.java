package com.sarinzhan;

import com.sarinzhan.Handler.PostHandler;
import com.sarinzhan.Handler.UserHandler;
import com.sarinzhan.data.PostData;
import com.sarinzhan.data.UserData;
import com.sarinzhan.Service.Authenticator;
import com.sarinzhan.Service.PostService;
import com.sarinzhan.Service.UserService;
import com.sarinzhan.entitties.User;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.time.LocalDate;

public class ServerInitializer extends ChannelInitializer<SocketChannel> {
//    Authenticator authenticator = new Authenticator();
    private UserData userData = new UserData();
    private PostData postData = new PostData();
//    private UserService userService = new UserService(userData,authenticator);
//    private PostService postService = new PostService(postData);

    public ServerInitializer() {
        userData.addNewUser(new User(1L,"sarinzhan","1","Саринжан","Казбеков", LocalDate.of(2003,5,14)));
        userData.addNewUser(new User(2L,"vova","1","Владимир","Ганин",LocalDate.of(2003,9,20)));
        userData.addNewUser(new User(3L,"rishat","1","Ришат","Габдулин",LocalDate.of(2003,01,01)));
    }

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        System.out.println("[Server initializer] initChannel");
        Authenticator authenticator = new Authenticator();
        UserService userService = new UserService(userData,authenticator);
        PostService postService = new PostService(postData,authenticator);
        ChannelPipeline pipeline = socketChannel.pipeline();
        pipeline.addLast(new StringDecoder());
        pipeline.addLast(new StringEncoder());
        pipeline.addLast(new UserHandler(userService,authenticator));
        pipeline.addLast(new PostHandler(postService,authenticator));
    }
}
