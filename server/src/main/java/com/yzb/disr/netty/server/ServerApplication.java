package com.yzb.disr.netty.server;

import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.dsl.ProducerType;
import com.yzb.disr.netty.disruptor.MessageConsumer;
import com.yzb.disr.netty.disruptor.RingBufferWorkerPoolFactory;
import com.yzb.disr.netty.server.ser.MessageConsumerImpl4Server;
import com.yzb.disr.netty.server.ser.NettyServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServerApplication.class, args);

        MessageConsumer[] consumers = new MessageConsumer[4];
        for (int i = 0; i < consumers.length; i++) {
            MessageConsumer messageConsumer = new MessageConsumerImpl4Server("code:serverId:" + i);
            consumers[i] = messageConsumer;
        }
        RingBufferWorkerPoolFactory.getInstantce().initAndStart(ProducerType.MULTI,
                1024 * 1024, new BlockingWaitStrategy(), consumers);

        new NettyServer();
    }
}
