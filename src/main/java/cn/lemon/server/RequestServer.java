package cn.lemon.server;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.util.concurrent.CountDownLatch;

import com.sun.org.apache.bcel.internal.generic.NEW;

import cn.lemon.context.CommonConstants;
import cn.lemon.proxy.RequestProxy;
import cn.lemon.server.handler.ServerHandlerInitializer;
import cn.lemon.web.service.TestEntity;
import cn.lemon.web.service.UserService;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

public class RequestServer {
	private Channel channel = null;
	private static CountDownLatch LATCH = null;

	private String url = "127.0.0.1";
	private int port = 5204;

	public RequestServer(String url, int port) {
		this.port = port;
		this.url = url;
		start();
	}

	public void start() {
		System.out.println("start netty");
		LATCH = new CountDownLatch(1);
		EventLoopGroup group = new NioEventLoopGroup();
		Bootstrap bootstrap = new Bootstrap();
		bootstrap.group(group).channel(NioSocketChannel.class).handler(new ServerHandlerInitializer());
		try {
			channel = bootstrap.connect(this.url, this.port).sync().channel();
//			LATCH.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {

		}

	}

	public void request(Request request) {
		try {
			System.out.println("发送消息");
			Request request2 = new Request();
			request2.setId(request.getId());
			request2.setMethodName(request.getMethodName());
			request2.setParams(request.getParams());
			request2.setTarget(request.getTarget());
			request2.setParamTypes(request.getParamTypes());
			channel.writeAndFlush(request);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void countDown() {
		LATCH.countDown();
	}
}
