package com.yzb.disr.netty.disruptor;

import com.lmax.disruptor.RingBuffer;
import com.yzb.disr.netty.entity.TranslatorData;
import com.yzb.disr.netty.entity.TranslatorDataWapper;
import io.netty.channel.ChannelHandlerContext;

/**
 * Created by brander on 2018/10/7
 */
public class MessageProducer {

    private RingBuffer<TranslatorDataWapper> ringBuffer;

    private String producerId;

    public MessageProducer(String producerId, RingBuffer<TranslatorDataWapper> ringBuffer) {
        this.ringBuffer = ringBuffer;
        this.producerId = producerId;
    }

    /**
     * 发送数据
     *
     * @param data 实际对象
     * @param ctx  ctx 对象
     */
    public void onData(TranslatorData data, ChannelHandlerContext ctx) {
        long sequence = ringBuffer.next();
        try {
            TranslatorDataWapper translatorDataWapper = ringBuffer.get(sequence);
            translatorDataWapper.setData(data);
            translatorDataWapper.setCtx(ctx);
        } finally {
            ringBuffer.publish(sequence);
        }

    }
}
