package com.yzb.disr.netty.server.ser;

import com.yzb.disr.netty.disruptor.MessageConsumer;
import com.yzb.disr.netty.entity.TranslatorData;
import com.yzb.disr.netty.entity.TranslatorDataWapper;

/**
 * Created by brander on 2018/10/7
 */
public class MessageConsumerImpl4Server extends MessageConsumer {

    public MessageConsumerImpl4Server(String consumerId) {
        super(consumerId);
    }

    @Override
    public void onEvent(TranslatorDataWapper event) throws Exception {
        TranslatorData request = event.getData();
        System.err.println("MessageConsumerImpl4Server =  [" + event + "]");
        System.err.println("getName = " + request.getName() + " ,getMessage" + request.getMessage());
        TranslatorData response = new TranslatorData();
        response.setId("resp: " + request.getId());
        response.setName("resp: " + request.getName());
        response.setMessage("resp: " + request.getMessage());
        //写出 response 相应信息
        event.getCtx().writeAndFlush(response);

    }
}
