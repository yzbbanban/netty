package com.yzb.disr.netty.client.cli;

import com.yzb.disr.netty.disruptor.MessageProducer;
import com.yzb.disr.netty.disruptor.RingBufferWorkerPoolFactory;
import com.yzb.disr.netty.entity.TranslatorData;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;
import io.netty.util.concurrent.EventExecutorGroup;

/**
 * Created by brander on 2018/10/7
 */
public class ClientHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
//        try {
//            TranslatorData response = (TranslatorData) msg;
//            System.err.println("ClientHandler = [" + ctx + "], o = [" + msg + "]");
//            System.err.println("response getName = " + response.getName() + " ,getMessage" + response.getMessage());
//        } finally {
//            //一定要释放
//            ReferenceCountUtil.release(msg);
//        }

        TranslatorData response = (TranslatorData) msg;
        String producerId = "code:sessionId:002";
        MessageProducer messageProducer = RingBufferWorkerPoolFactory.getInstantce().getMessageProducer(producerId);
        messageProducer.onData(response, ctx);

    }
}
