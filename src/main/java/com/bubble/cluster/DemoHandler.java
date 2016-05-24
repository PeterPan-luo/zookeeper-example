package com.bubble.cluster;

import java.util.Date;

import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelUpstreamHandler;

/**
 * @author hxpwangyi@163.com
 * @date 2013-2-10
 */
public class DemoHandler extends SimpleChannelUpstreamHandler {

	@Override
	public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) throws Exception {
		Thread.sleep(5000);
		System.out.println(e.getMessage());
		ctx.getChannel().write("bbb");
	}
	@Override
    public void channelConnected(ChannelHandlerContext ctx, ChannelStateEvent e) {
        e.getChannel().write("abcd");
    }


}
