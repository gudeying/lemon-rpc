package cn.lemon.rpc.rpcserver;

import cn.lemon.proxy.RpcResponseProxy;
import cn.lemon.proxy.RpcResponseProxy.Builder;
import cn.lemon.server.Request;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import net.sf.cglib.reflect.FastClass;
import net.sf.cglib.reflect.FastMethod;

public class ChannelDefaultHandler extends ChannelInboundHandlerAdapter {

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		if (msg instanceof Request) {
			Request request = (Request) msg;
			
			Class<?> target = request.getTarget();

			String methodName = request.getMethodName();

			Class<?>[] paramTypes = request.getParamTypes();

			Object[] args = request.getParams();
			Builder resultBuilder = RpcResponseProxy.Builder();
			try {
				FastClass fastClass = FastClass.create(target);
				FastMethod method = fastClass.getMethod(methodName, paramTypes);
				Object result = method.invoke(fastClass.newInstance(), args);
				System.out.println(target.getName()+"执行"+methodName+"，传入参数："+args.toString()+"的结果是："+result);
				resultBuilder.Status(1).Result(result);
			} catch (Exception e) {
				e.printStackTrace();
				resultBuilder.Status(0).Msg(e.getMessage());
			}
			resultBuilder.Id(request.getId());
			ctx.channel().writeAndFlush(resultBuilder.Build());
		}
		super.channelRead(ctx, msg);
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		RpcResponseProxy responseProxy = RpcResponseProxy.Builder().Status(0).Msg(cause.getMessage()).Build();
		ctx.writeAndFlush(responseProxy);
		cause.printStackTrace();
	}

}
