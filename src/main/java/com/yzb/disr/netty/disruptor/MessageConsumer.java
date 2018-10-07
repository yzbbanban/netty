package com.yzb.disr.netty.disruptor;

import com.lmax.disruptor.WorkHandler;
import com.yzb.disr.netty.entity.TranslatorDataWapper;

/**
 * Created by brander on 2018/10/7
 */
public abstract class MessageConsumer implements WorkHandler<TranslatorDataWapper> {

    protected String consumerId;

    public MessageConsumer(String consumerId) {
        this.consumerId = consumerId;
    }

    public String getConsumerId() {
        return consumerId;
    }

    public void setConsumerId(String consumerId) {
        this.consumerId = consumerId;
    }
}
