/*
 * 文件名: Singleton.java
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

/**
 * @Title Singleton
 * @Description 单例 --懒汉式双重校验
 * @author wpeng
 * @date 2019-9-17
 * @version V1.0
 * @see
 * @since V1.0
 * 
 */
public class Singleton {

    //私有化，禁止访问
    private Singleton() {

    }

    private static Singleton singleton = null;

    public static Singleton getSingleton() {
        //第一重校验,提高效率，减少锁资源竞争
        if (singleton == null) {
            //同步锁
            synchronized (Singleton.class) {
                //第二重检验
                if (singleton == null) {
                    singleton = new Singleton();
                }
            }
        }
        return singleton;
    }
}
