/*
 * 文件名: NettyClient.java
 * 文件编号: 
 * 版权: Copyright (c) 2019, YAN Co.Ltd. and/or its affiliates. All rights reserved.Use is subject to license terms.
 * 描述: TODO
 * 创建人: wpeng
 * 创建时间: 2019-9-20
 * 修改人:
 * 修改时间: 2019-9-20
 * 修改变更号: 
 * 修改内容: TODO
 */
package wp.learn;

import java.net.InetSocketAddress;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelHandler;
import org.jboss.netty.channel.socket.nio.NioClientSocketChannelFactory;
import org.jboss.netty.handler.codec.string.StringDecoder;
import org.jboss.netty.handler.codec.string.StringEncoder;

/**
 * @Title NettyClient
 * @Description TODO
 * @author wpeng
 * @date 2019-9-20
 * @version V1.0
 * @see 
 * @since V1.0
 * 
 */
public class NettyClient {

    /**
     * @Title main
     * @Description TODO
     * @author wpeng
     * @date 2019-9-20
     * @version V1.0
     * @param args 
     * @since V1.0
     */
    public static void main(String[] args) {
       System.out.println("客户端开启……");
        ClientBootstrap clientBootstrap = new ClientBootstrap();
        ExecutorService bools = Executors.newCachedThreadPool();
        ExecutorService wook = Executors.newCachedThreadPool();
        clientBootstrap.setFactory(new NioClientSocketChannelFactory(bools, wook));
        //设置管道工程
        clientBootstrap.setPipelineFactory(new ChannelPipelineFactory() {
            @Override
            public ChannelPipeline getPipeline() throws Exception {
                // 设置管道
                ChannelPipeline channelPipeline = Channels.pipeline();
                //传输数据时设为String 类型
                channelPipeline.addLast("decoder", new StringDecoder());
                channelPipeline.addLast("encoder", new StringEncoder());
                //设置事件监听
                channelPipeline.addLast("clientHanlder", new ClientHanlder());
                return channelPipeline;
            }
        });
         ChannelFuture connect =  clientBootstrap.connect(new InetSocketAddress("127.0.0.1",8080));      
         Channel channel = connect.getChannel();
         Scanner scanner = new Scanner(System.in);
         while(true){
             System.out.println("开始输入内容：");
             String str = scanner.next();
             channel.write(str);
         }
    }

}

class ClientHanlder  extends SimpleChannelHandler {
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
        System.out.println("接受服务端返回信息："+e.getMessage());
    }

}