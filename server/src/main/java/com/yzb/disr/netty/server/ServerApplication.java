package com.yzb.disr.netty.server;

import com.yzb.disr.netty.server.ser.NettyServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServerApplication.class, args);
		new NettyServer();
	}
}
