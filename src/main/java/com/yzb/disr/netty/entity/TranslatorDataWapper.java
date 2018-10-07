package com.yzb.disr.netty.entity;

import io.netty.channel.ChannelHandlerContext;

import java.io.Serializable;

/**
 * Created by brander on 2018/10/7
 */
public class TranslatorDataWapper {

    private TranslatorData data;

    private ChannelHandlerContext ctx;

    public TranslatorData getData() {
        return data;
    }

    public void setData(TranslatorData data) {
        this.data = data;
    }

    public ChannelHandlerContext getCtx() {
        return ctx;
    }

    public void setCtx(ChannelHandlerContext ctx) {
        this.ctx = ctx;
    }

    @Override
    public String toString() {
        return "TranslatorDataWapper{" +
                "data=" + data +
                ", ctx=" + ctx +
                '}';
    }
}
