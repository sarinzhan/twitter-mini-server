package com.sarinzhan.Service;

import com.sarinzhan.entitties.User;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;

import java.beans.PropertyChangeListener;
import java.util.*;

public class Authenticator {
    private static Map<User,Channel> usersChannel;
    private static List<Channel> channels;
    private User authUser;

    public Authenticator() {
        this.usersChannel = new HashMap<>();
    }
    public void removeAuthUser() throws Exception {
        if(Objects.nonNull(this.authUser)) {
            System.out.println("[Authenticator][removeAuthUser][before] " + userChannelToString());
            usersChannel.remove(this.authUser);
            this.authUser = null;
            System.out.println("[Authenticator][removeAuthUser][after] " + userChannelToString());
            return;
        }
        throw new Exception("Ошибка при удалении авторизованного пользователя");
    }
    public List<User> getAuthUsers(){
        return new ArrayList<>( usersChannel.keySet().stream().toList());
    }

    public User getAuthUser() {
        return authUser;
    }

    public void setAuthUser(User authUser, ChannelHandlerContext ctx) throws Exception {
        if(Objects.isNull(this.authUser)) {
            System.out.println("[Authenticator][setAuthUser][before] " + userChannelToString());
            this.authUser = authUser;
            usersChannel.put(authUser, ctx.channel());
            System.out.println("[Authenticator][setAuthUser][after] " + userChannelToString());
            return;
        }
        throw new Exception("Ошибка при добавлении авторизованного пользователя");    }
    public Channel getChannelByUser(User user){
       return usersChannel.get(user);
    }
    public List<Channel> getAllChannels(){
        List<Channel> channelList = new ArrayList<>();
        usersChannel.keySet().forEach(x -> channelList.add(usersChannel.get(x)));
        return channelList;
    }

    private String userChannelToString(){
        StringBuilder res = new StringBuilder();
        for(User user : usersChannel.keySet()){
            res.append(String.format("User: %s; \tchannel: %s",user.getFirstName(),usersChannel.get(user).id()));
        }
        return  res.toString();
    }
}
