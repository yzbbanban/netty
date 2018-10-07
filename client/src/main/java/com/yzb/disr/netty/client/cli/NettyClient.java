package com.yzb.disr.netty.client.cli;

import com.yzb.disr.netty.codec.MarshallingCodeCFactory;
import com.yzb.disr.netty.entity.TranslatorData;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/**
 * Created by brander on 2018/10/7
 */
public class NettyClient {


    public static final String HOST = "127.0.0.1";
    public static final int PORT = 8675;

    //1 创建工作线程组用于实际处理业务的线程组
    private EventLoopGroup workGroup = new NioEventLoopGroup();
    private ChannelFuture cf;

    public NettyClient() {
        connect(HOST, PORT);
    }

    private Channel channel; // 扩展完善 ConcurrentHashMap<>

    private void connect(String host, int port) {


        Bootstrap bootstrap = new Bootstrap();
        try {
            //配置构建
            bootstrap.group(workGroup)
                    .channel(NioSocketChannel.class)
                    //表示缓存区动态调配（自适应） 数据包传递大小差距不大
                    .option(ChannelOption.RCVBUF_ALLOCATOR, AdaptiveRecvByteBufAllocator.DEFAULT)
                    //缓存区 赤化操作
                    .option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT)
                    .handler(new LoggingHandler(LogLevel.INFO))

                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast(MarshallingCodeCFactory.buildMarshallingDecoder());
                            socketChannel.pipeline().addLast(MarshallingCodeCFactory.buildMarshallingEncoder());
                            socketChannel.pipeline().addLast(new ClientHandler());
                        }
                    });
            //绑定端口，同步等待请求连接
            cf = bootstrap.connect(host, port).sync();
            System.err.println("client connected ...");

            //接下来
            this.channel = cf.channel();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void sendData() {
        for (int i = 0; i < 10; i++) {
            TranslatorData request = new TranslatorData();
            request.setId("" + i);
            request.setName("请求消息名称：" + i);
            request.setMessage("请求消息信息： " + i);
            this.channel.writeAndFlush(request);
        }
    }

    public void close() {
        //优雅停机
        try {
            cf.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            workGroup.shutdownGracefully();
            System.err.println("client shutDown");
        }
    }
}
