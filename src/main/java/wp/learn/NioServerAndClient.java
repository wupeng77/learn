/*
 * 文件名: NioServerAndClient.java
 * 文件编号:
 * 版权: Copyright (c) 2019, YAN Co.Ltd. and/or its affiliates. All rights
 * reserved.Use is subject to license terms.
 * 描述: TODO
 * 创建人: wpeng
 * 创建时间: 2019-9-18
 * 修改人:
 * 修改时间: 2019-9-18
 * 修改变更号:
 * 修改内容: TODO
 */
package wp.learn;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ByteChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;

/**
 * @Title NioServerAndClient
 * @Description TODO
 * @author wpeng
 * @date 2019-9-18
 * @version V1.0
 * @see
 * @since V1.0
 * 
 */
public class NioServerAndClient {

    /**
     * @Title main
     * @Description TODO
     * @author wpeng
     * @date 2019-9-18
     * @version V1.0
     * @param args
     * @since V1.0
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub

    }

}

class NioClient {
    public static void main(String[] args) throws IOException {
        System.out.println("客户端开启…………");
        //创建通道
        SocketChannel socketChannel = SocketChannel.open(new InetSocketAddress("127.0.0.1", 8080));
        socketChannel.configureBlocking(false);
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            String inStr = scanner.next();
            byteBuffer.put(inStr.getBytes());
            byteBuffer.flip();
            socketChannel.write(byteBuffer);
            byteBuffer.clear();
        }
        socketChannel.close();
    }

}

class NioServer {
    public static void main(String[] args) throws IOException {

        System.out.println("服务器端开启…………");
        //创建通道
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        //切换读取模式
        serverSocketChannel.configureBlocking(false);
        //设置端口
        serverSocketChannel.bind(new InetSocketAddress(8080));
        //获取选择器
        Selector selector = Selector.open();
        //注册通道
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        //轮询式 
        while (selector.select() > 0) {
            //获取当前选择器所有注册的
            Iterator<SelectionKey> it = selector.selectedKeys().iterator();
            while (it.hasNext()) {
                //获取准备就绪的事件
                SelectionKey selectionKey = it.next();
                //判断具体是什么事件准备就绪
                if (selectionKey.isAcceptable()) {
                    //若“接受就绪”，获取客户端连接
                    SocketChannel socketChannel = serverSocketChannel.accept();
                    //设置阻塞模式
                    socketChannel.configureBlocking(false);
                    //将该通道注册到服务器上
                    socketChannel.register(selector, SelectionKey.OP_READ);
                } else if (selectionKey.isReadable()) {
                    //获取当前选择器“就绪”状态的通道
                    SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
                    ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                    int len = 0;
                    while ((len = socketChannel.read(byteBuffer)) > 0) {
                        byteBuffer.flip();
                        System.out.println(new String(byteBuffer.array(), 0, len));
                        byteBuffer.clear();
                    }
                }
            }

        }
    }
}