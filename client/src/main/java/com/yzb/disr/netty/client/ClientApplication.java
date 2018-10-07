package com.yzb.disr.netty.client;

import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.dsl.ProducerType;
import com.yzb.disr.netty.client.cli.MessageConsumerImpl4Client;
import com.yzb.disr.netty.client.cli.NettyClient;
import com.yzb.disr.netty.disruptor.MessageConsumer;
import com.yzb.disr.netty.disruptor.RingBufferWorkerPoolFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(ClientApplication.class, args);
        MessageConsumer[] consumers = new MessageConsumer[4];
        for (int i = 0; i < consumers.length; i++) {
            MessageConsumer messageConsumer = new MessageConsumerImpl4Client("code:clientId:" + i);
            consumers[i] = messageConsumer;
        }
        RingBufferWorkerPoolFactory.getInstantce().initAndStart(ProducerType.MULTI,
                1024 * 1024, new BlockingWaitStrategy(), consumers);
        new NettyClient().sendData();
    }
}
