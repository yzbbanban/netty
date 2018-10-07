package com.yzb.disr.netty.disruptor;

import com.lmax.disruptor.*;
import com.lmax.disruptor.dsl.ProducerType;
import com.yzb.disr.netty.entity.TranslatorDataWapper;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;

/**
 * Created by brander on 2018/10/7
 */
public class RingBufferWorkerPoolFactory {

    private static class SingletonHolder {
        static final RingBufferWorkerPoolFactory instance = new RingBufferWorkerPoolFactory();
    }

    public RingBufferWorkerPoolFactory() {
    }

    public static RingBufferWorkerPoolFactory getInstantce() {
        return SingletonHolder.instance;
    }


    private static Map<String, MessageProducer> producers = new ConcurrentHashMap<>();

    private static Map<String, MessageConsumer> consumers = new ConcurrentHashMap<>();

    private RingBuffer<TranslatorDataWapper> ringBuffer;

    private SequenceBarrier sequenceBarrier;

    private WorkerPool<TranslatorDataWapper> workerPool;

    public void initAndStart(ProducerType type, int bufferSize, WaitStrategy waitStrategy, MessageConsumer[] messageConsumers) {

        //1 构建 ringBuffer
        this.ringBuffer = RingBuffer.create(type, new EventFactory<TranslatorDataWapper>() {
            @Override
            public TranslatorDataWapper newInstance() {
                return new TranslatorDataWapper();
            }
        }, bufferSize, waitStrategy);
        //2 设置序号栅栏
        this.sequenceBarrier = this.ringBuffer.newBarrier();

        //3 设置工作池
        this.workerPool = new WorkerPool<TranslatorDataWapper>(this.ringBuffer,
                this.sequenceBarrier,
                new EventExceptionHandler(),
                messageConsumers);
        //4 把所构建的消费者置入池中
        for (MessageConsumer mc : messageConsumers) {
            this.consumers.put(mc.getConsumerId(), mc);
        }
        //5 添加我们的 sequences
        this.ringBuffer.addGatingSequences(this.workerPool.getWorkerSequences());
        //6 启动我们的工作池 应该使用自定义线程池
        this.workerPool.start(Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() / 2));
    }

    public MessageProducer getMessageProducer(String producerId) {
        //先从池中获取
        MessageProducer messageProducer = this.producers.get(producerId);
        if (messageProducer == null) {
            //没有放入池中
            messageProducer = new MessageProducer(producerId, this.ringBuffer);
            this.producers.put(producerId, messageProducer);
        }
        return messageProducer;
    }

    /**
     * 异常静态类
     */
    static class EventExceptionHandler implements ExceptionHandler<TranslatorDataWapper> {
        @Override
        public void handleEventException(Throwable throwable, long l, TranslatorDataWapper tdw) {

        }

        @Override
        public void handleOnStartException(Throwable throwable) {

        }

        @Override
        public void handleOnShutdownException(Throwable throwable) {

        }
    }

}
