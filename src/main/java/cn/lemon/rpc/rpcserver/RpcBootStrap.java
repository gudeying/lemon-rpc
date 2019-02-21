package cn.lemon.rpc.rpcserver;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import io.netty.handler.stream.ChunkedWriteHandler;

public class RpcBootStrap {
	public static void main(String[] args) {
		try {
			EventLoopGroup work = new NioEventLoopGroup();
			EventLoopGroup boss = new NioEventLoopGroup();
			ServerBootstrap serverBootstrap = new ServerBootstrap();
			serverBootstrap.group(boss, work).channel(NioServerSocketChannel.class)
					.childHandler(new ChannelInitializer<Channel>() {

						@Override
						protected void initChannel(Channel channel) throws Exception {
							ChannelPipeline pipeline = channel.pipeline();
						    pipeline.addLast(new ChunkedWriteHandler());
//							pipeline.addLast(new ChannelDefaultHandler());
							pipeline.addLast(new ObjectEncoder());
							pipeline.addLast(new ObjectDecoder(ClassResolvers.cacheDisabled(null)));
							pipeline.addLast(new ChannelDefaultHandler());
							
						}
					});
			serverBootstrap.bind(5206).sync();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

		}

	}
}
