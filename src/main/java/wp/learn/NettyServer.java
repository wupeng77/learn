/*
 * 文件名: NettyServer.java
 * 文件编号:
 * 版权: Copyright (c) 2019, YAN Co.Ltd. and/or its affiliates. All rights
 * reserved.Use is subject to license terms.
 * 描述: TODO
 * 创建人: wpeng
 * 创建时间: 2019-9-19
 * 修改人:
 * 修改时间: 2019-9-19
 * 修改变更号:
 * 修改内容: TODO
 */
package wp.learn;

import java.net.InetSocketAddress;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelHandler;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;
import org.jboss.netty.handler.codec.string.StringDecoder;
import org.jboss.netty.handler.codec.string.StringEncoder;

/**
 * @Title NettyServer
 * @Description TODO
 * @author wpeng
 * @date 2019-9-19
 * @version V1.0
 * @see
 * @since V1.0
 * 
 */
public class NettyServer {

    /**
     * @Title main
     * @Description TODO
     * @author wpeng
     * @date 2019-9-19
     * @version V1.0
     * @param args
     * @throws InterruptedException 
     * @since V1.0
     */
    public static void main(String[] args) throws InterruptedException {

        //创建服务对象
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        //创建线程池，分别是监听端口，nio监听
        ExecutorService bools = Executors.newCachedThreadPool();
        ExecutorService wook = Executors.newCachedThreadPool();
        //将线程池放入工程
        serverBootstrap.setFactory(new NioServerSocketChannelFactory(bools, wook));
        //设置管道工程
        serverBootstrap.setPipelineFactory(new ChannelPipelineFactory() {
            @Override
            public ChannelPipeline getPipeline() throws Exception {
                // 设置管道
                ChannelPipeline channelPipeline = Channels.pipeline();
                //传输数据时设为String 类型
                channelPipeline.addLast("decoder", new StringDecoder());
                channelPipeline.addLast("encoder", new StringEncoder());
                //设置事件监听
                channelPipeline.addLast("serverHanlder", new ServerHanlder());
                return channelPipeline;
            }
        });
        //设置端口
        serverBootstrap.bind(new InetSocketAddress(8080));
        System.out.println("netty服务器开启……");

    }

}

class ServerHanlder extends SimpleChannelHandler {
    //关闭通道时触发
    @Override
    public void channelClosed(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
        super.channelClosed(ctx, e);
        System.out.println("channelClosed");
    }

    //必须是建立连接，关闭通道时触发
    @Override
    public void channelDisconnected(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
        super.channelDisconnected(ctx, e);
        System.out.println("channelDisconnected");
    }

    //监听异常
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) throws Exception {
        super.exceptionCaught(ctx, e);
        System.out.println("exceptionCaught");

    }

    //监听接受信息
    @Override
    public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) throws Exception {
        super.messageReceived(ctx, e);
        System.out.println("messageReceived");
        System.out.println("接受客戶端信息："+e.getMessage());
        ctx.getChannel().write("你好啊！");
    }

}