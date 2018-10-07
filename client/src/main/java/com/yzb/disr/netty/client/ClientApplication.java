package com.yzb.disr.netty.client;

import com.yzb.disr.netty.client.cli.NettyClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(ClientApplication.class, args);
        new NettyClient().sendData();
    }
}
