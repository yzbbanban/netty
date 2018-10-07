package com.yzb.disr.netty.entity;

import java.io.Serializable;

/**
 * Created by brander on 2018/10/7
 */
public class TranslatorData implements Serializable {


    private static final long serialVersionUID = 5969510880848937240L;

    private String id;
    private String name;
    private String message;//传输消息体内容

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
