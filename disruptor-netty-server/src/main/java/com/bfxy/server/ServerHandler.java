package com.bfxy.server;

import com.bfxy.disruptor.MessageProducer;
import com.bfxy.disruptor.RingBufferWorkerPoolFactory;
import com.bfxy.entity.TranslatorData;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class ServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
    	/**
    	TranslatorData request = (TranslatorData)msg;
    	System.err.println("Sever端: id= " + request.getId() 
    					+ ", name= " + request.getName() 
    					+ ", message= " + request.getMessage());
    	//数据库持久化操作 IO读写 ---> 交给一个线程池 去异步的调用执行
    	TranslatorData response = new TranslatorData();
    	response.setId("resp: " + request.getId());
    	response.setName("resp: " + request.getName());
    	response.setMessage("resp: " + request.getMessage());
    	//写出response响应信息:
    	ctx.writeAndFlush(response);
    	*/
    	TranslatorData request = (TranslatorData)msg;
    	//自已的应用服务应该有一个ID生成规则
    	String producerId = "code:sessionId:001";
    	MessageProducer messageProducer = RingBufferWorkerPoolFactory.getInstance().getMessageProducer(producerId);
    	//netty服务端收到消息   给到disruptor生产者 然后生产者投递到ringbuffer
    	messageProducer.onData(request, ctx);
    	
    	
    }
    
}
