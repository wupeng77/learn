/*
 * 文件名: Channel.java
 * 文件编号:
 * 版权: Copyright (c) 2019, YAN Co.Ltd. and/or its affiliates. All rights
 * reserved.Use is subject to license terms.
 * 描述: TODO
 * 创建人: wpeng
 * 创建时间: 2019-9-17
 * 修改人:
 * 修改时间: 2019-9-17
 * 修改变更号:
 * 修改内容: TODO
 */
package wp.learn;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;

/**
 * @Title Channel
 * @Description TODO
 * @author wpeng
 * @date 2019-9-17
 * @version V1.0
 * @see
 * @since V1.0
 * 
 */
public class Channel {

    /**
     * @Title main
     * @Description 使用直接缓冲区实现文件的复制操作（内存映射文件）
     * @author wpeng
     * @date 2019-9-17
     * @version V1.0
     * @param args
     * @throws IOException
     * @since V1.0
     */
    public static void main(String[] args) throws IOException {

        long start = System.currentTimeMillis();
        //创建通道，并赋读权限
        FileChannel inChannel = FileChannel.open(Paths
                .get("E://BaiduNetdiskDownload/蚂蚁金服资料/0009-蚂蚁课堂(每特学院)-2期-NIO编程基础/0009-蚂蚁课堂(每特学院)-2期-NIO编程基础/总结.mp4"),
                StandardOpenOption.READ);
        //创建通道，并赋予读写权限
        FileChannel outChannel = FileChannel.open(Paths.get("E://总结1.mp4"), StandardOpenOption.READ,
                StandardOpenOption.WRITE, StandardOpenOption.CREATE);
        //创建直接缓冲区
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(1024);
        //内存映射文件
        MappedByteBuffer inByteBuffer = inChannel.map(MapMode.READ_ONLY, 0, inChannel.size());
        MappedByteBuffer outByteBuffer = outChannel.map(MapMode.READ_WRITE, 0, inChannel.size());
        //直接对缓冲区精选读写操作
        byte[] bytes = new byte[inByteBuffer.limit()];
        inByteBuffer.get(bytes);
        outByteBuffer.put(bytes);
        inChannel.close();
        outChannel.close();
        long end = System.currentTimeMillis();
        System.out.println("耗时：" + (end - start) + "ms");
        test();
    }

    /**
     * 
     * @Title test
     * @Description 1.利用通道完成文件的复制(非直接缓冲区)
     * @author wpeng
     * @date 2019-9-17
     * @version V1.0
     * @throws IOException
     * @since V1.0
     */
    public static void test() throws IOException {

        long start = System.currentTimeMillis();

        FileInputStream fileInputStream = new FileInputStream(
                "E://BaiduNetdiskDownload/蚂蚁金服资料/0009-蚂蚁课堂(每特学院)-2期-NIO编程基础/0009-蚂蚁课堂(每特学院)-2期-NIO编程基础/总结.mp4");
        FileOutputStream fileOutputStream = new FileOutputStream("E://总结2.mp4");
        //获取通道
        FileChannel inFileChannel = fileInputStream.getChannel();
        FileChannel OutFileChannel = fileOutputStream.getChannel();
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        while (inFileChannel.read(byteBuffer) != -1) {
            //切换到读状态
            byteBuffer.flip();
            //把缓冲区数据写到通道中
            OutFileChannel.write(byteBuffer);
            //清除缓冲数据
            byteBuffer.clear();
        }
        fileInputStream.close();
        fileOutputStream.close();
        inFileChannel.close();
        OutFileChannel.close();
        long end = System.currentTimeMillis();
        System.out.println("耗时：" + (end - start) + "ms");
    }
}
