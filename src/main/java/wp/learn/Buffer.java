/*
 * 文件名: Buffer.java
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

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

/**
 * @Title Buffer
 * @Description 直接缓冲区与非直接缓冲区
 * @author wpeng
 * @date 2019-9-17
 * @version V1.0
 * @see
 * @since V1.0
 * 
 */
public class Buffer {

    /**
     * @Title main
     * @Description TODO
     * @author wpeng
     * @date 2019-9-17
     * @version V1.0
     * @param args
     * @since V1.0
     */
    public static void main(String[] args) {
        //非直接缓冲区
        IntBuffer intBuffer = IntBuffer.allocate(10);
        //写入
        intBuffer.put(101);
        //切换到读模式
        intBuffer.flip();
        //读取
        int i = intBuffer.get();
        //清除，数据不会被删除，只会被遗忘
        intBuffer.clear();
        System.out.println(i);
        //直接缓冲区
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(1024);
        byteBuffer.put("wpeng123456".getBytes());
        byteBuffer.flip();
        //开始读取位置
        System.out.println("position:"+byteBuffer.position());
        //可
        System.out.println("limit:"+byteBuffer.limit());
        //表示Buffer最大数据容量，缓冲区容量不能为负，并且建立后不能修改。
        System.out.println("capacity:"+byteBuffer.capacity());


    }

}
