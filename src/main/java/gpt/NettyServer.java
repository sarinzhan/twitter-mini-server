package gpt;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class NettyServer {
    private final Integer PORT;
    private ServerInitializer serverInitializer;

    public NettyServer(Integer PORT, ServerInitializer serverInitializer) {
        this.PORT = PORT;
        this.serverInitializer = serverInitializer;
    }
    public void run() throws InterruptedException {
        //пул поток для обработки подключении
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        //пул для обработки данных(логика)
        EventLoopGroup workerGroup = new NioEventLoopGroup(4);

        try{
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup,workerGroup)
                    .channel(NioServerSocketChannel.class)//
                    .childHandler(this.serverInitializer);

            ChannelFuture ch = serverBootstrap.bind(this.PORT).sync();
            ch.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
    public static void main(String [] args) throws InterruptedException {
        new NettyServer(8089, new ServerInitializer()).run();
    }

}
