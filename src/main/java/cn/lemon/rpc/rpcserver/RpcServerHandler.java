package cn.lemon.rpc.rpcserver;

import cn.lemon.proxy.RpcResponseProxy;
import cn.lemon.server.Request;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class RpcServerHandler extends SimpleChannelInboundHandler<Object> {

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
//		int id = msg.getId();
		System.out.println(msg.getClass().getName());
		RpcResponseProxy responseProxy = RpcResponseProxy.Builder().Id(9).Result("rpc Result").Build();
//		System.out.println("收到请求信息，信息id是：" + id);
//		System.out.println(msg.getTarget().getName());
//		System.out.println(msg.getMethod().getName());
		ctx.channel().writeAndFlush(responseProxy);
	}
	
	
	
	
	@Override
	public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
		System.out.println("收到连接信息");
		System.out.println(ctx.channel().remoteAddress().toString());
		super.channelRegistered(ctx);
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		System.out.println(cause.getMessage());
		super.exceptionCaught(ctx, cause);
	}
	

}
