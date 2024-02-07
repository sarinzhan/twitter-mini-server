package com.sarinzhan.Handler;

import com.sarinzhan.Service.Authenticator;
import com.sarinzhan.Service.PostService;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class PostHandler extends SimpleChannelInboundHandler<String> {
    private PostService postService;
    private Authenticator au;

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, String s) throws Exception {

    }


    public PostHandler(PostService postService, Authenticator au) {
        this.postService = postService;
        this.au = au;
    }
}
