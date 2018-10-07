package com.yzb.disr.netty.server.ser;

import com.yzb.disr.netty.entity.TranslatorData;
import io.netty.channel.*;

/**
 * Created by brander on 2018/10/7
 */
public class ServerHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext channelHandlerContext, Object o) throws Exception {
        TranslatorData request = (TranslatorData) o;
        System.err.println("channelHandlerContext = [" + channelHandlerContext + "], o = [" + o + "]");
        System.err.println("getName = " + request.getName() + " ,getMessage" + request.getMessage());


        TranslatorData response = new TranslatorData();
        response.setId("resp: " + request.getId());
        response.setName("resp: " + request.getName());
        response.setMessage("resp: " + request.getMessage());
        //写出 response 相应信息
        channelHandlerContext.writeAndFlush(response);

    }
}
