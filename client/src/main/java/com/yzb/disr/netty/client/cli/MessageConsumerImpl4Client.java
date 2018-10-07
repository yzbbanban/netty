package com.yzb.disr.netty.client.cli;

import com.yzb.disr.netty.disruptor.MessageConsumer;
import com.yzb.disr.netty.entity.TranslatorData;
import com.yzb.disr.netty.entity.TranslatorDataWapper;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.ReferenceCountUtil;

/**
 * Created by brander on 2018/10/7
 */
public class MessageConsumerImpl4Client extends MessageConsumer {

    public MessageConsumerImpl4Client(String consumerId) {
        super(consumerId);
    }

    @Override
    public void onEvent(TranslatorDataWapper event) throws Exception {
        TranslatorData response = event.getData();
        ChannelHandlerContext ctx = event.getCtx();
        try {
            System.err.println("MessageConsumerImpl4Client = [" + ctx + "], o = [" + response + "]");
            System.err.println("response getName = " + response.getName() + " ,getMessage" + response.getMessage());
        } finally {
            ReferenceCountUtil.release(response);
        }
    }
}
