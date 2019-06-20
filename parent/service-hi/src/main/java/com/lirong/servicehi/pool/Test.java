package com.lirong.servicehi.pool;

import org.apache.commons.pool2.ObjectPool;
import org.apache.commons.pool2.PooledObjectFactory;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Title: Test <br>
 * Description: Test <br>
 * Date: 2019年06月11日
 *
 * @author lirong
 * @version 1.0.0
 * @since jdk8
 */
public class Test {
    private static Logger logger = LoggerFactory.getLogger(Test.class);
    public static void main(String[] args) {
        test1();
    }

    private static void test1() {
        PooledObjectFactory<MyConnection> factory = new MyConnectionPoolableObjectFactory();

        GenericObjectPoolConfig<MyConnection> config = new GenericObjectPoolConfig<>();
        //池中最大空闲
        config.setMaxIdle(5);
        //池中最小空闲
        config.setMinIdle(1);
        //池中最大“连接数”，比如线程池中最大线程数
        config.setMaxTotal(5);
        //从线程池中获取对象需要等待的最大时间
        config.setMaxWaitMillis(20000L);
        //是否后进先出
        config.setLifo(false);
        ObjectPool pool = new GenericObjectPool<>(factory, config);
        for (int i = 0; i < 10; i++) {
            Thread thread = new Thread(new MyTask(pool));
            thread.start();
        }
//        closePool(pool);
    }

    private static void closePool(ObjectPool pool) {
        try {
            pool.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static class MyTask implements Runnable {
        private ObjectPool pool;

        public MyTask(ObjectPool pool) {
            this.pool = pool;
        }
        @Override
        public void run() {
            MyConnection myConn = null;
            try {
                myConn = (MyConnection)pool.borrowObject();
                try {
                    myConn.print("线程：");

                } catch(Exception ex) {
                    pool.invalidateObject(myConn);
                    myConn = null;
                }
                Thread.sleep(1000L);
            } catch(Exception ex) {
                logger.error("Cannot borrow connection from pool.", ex);
            } finally {
                if (myConn != null) {
                    try {
                        pool.returnObject(myConn);
                        logger.info("归还给pool");
                    } catch (Exception ex) {
                        logger.error("Cannot return connection from pool.", ex);
                    }
                }
            }
        }
    }
}
