package cn.lemon.server.handler;

import cn.lemon.context.ProxyContext;
import cn.lemon.proxy.RpcResponseProxy;
import cn.lemon.server.RequestServer;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class RpcResponseHandler extends SimpleChannelInboundHandler<RpcResponseProxy> {

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, RpcResponseProxy msg) throws Exception {
		int id = msg.getId();
		int status = msg.getStstus();
		if (status == 0) {
			throw new RuntimeException(msg.getMsg());
		}
		ProxyContext.load(id).setDone(msg.getResultObj());
	}

	@Override
	public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
		RequestServer.countDown();
		super.channelRegistered(ctx);
	}

}
