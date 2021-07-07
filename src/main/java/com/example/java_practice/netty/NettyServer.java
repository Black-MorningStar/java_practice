package com.example.java_practice.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;


@Slf4j
public class NettyServer {

    public static void serverStart(int port) {
        NioEventLoopGroup bossGroup = new NioEventLoopGroup(); //接受连接线程组
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();//处理请求线程组
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup,workerGroup)
                    .channel(NioServerSocketChannel.class) //确定是server服务端
                    .childHandler(new ChannelInitializer() {
                        @Override
                        protected void initChannel(Channel channel) throws Exception {
                            channel.pipeline().addLast(new DecodeHandler()); //解码器
                            channel.pipeline().addLast(new EncodeHandler()); //编码器
                            channel.pipeline().addLast(new BusinessHandler()); //请求业务处理器
                        }
                    })
                    .option(ChannelOption.SO_BACKLOG,128) //服务端存放完成三次握手连接的队列长度
                    .childOption(ChannelOption.SO_KEEPALIVE,true); //和客户端保持活跃
            ChannelFuture future = bootstrap.bind(port).sync();
            future.channel().closeFuture().sync();
        } catch (Exception e) {
            log.error("NettyServer start fail...",e);
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }
}
