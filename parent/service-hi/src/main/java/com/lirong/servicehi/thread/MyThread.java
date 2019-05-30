package com.lirong.servicehi.thread;

import com.github.pgcomb.pool.PoolUtil;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.lirong.servicehi.distributedlock.sequence.SequenceId;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Title: MyThread <br>
 * Description: MyThread <br>
 * Date: 2019年04月22日
 *
 * @author 王东旭
 * @version 1.0.0
 * @since jdk8
 */
public class MyThread implements Runnable {


    private static int i;

    public static boolean b = false;
    private static SequenceId sequenceId;

    public static void setSequenceId(SequenceId sequenceId) {
        MyThread.sequenceId = sequenceId;
    }

    @Override
    public void run() {
        Long id = sequenceId.getId(SequenceId.SEQUENCEID);
        System.out.println(id);
        ThreadPoolExecutor ll = PoolUtil.getPool("ll", 1, 2, 0, 5);
    }

    public static void main(String[] args) throws InterruptedException {
//        ThreadPoolExecutor ll = PoolUtil.getPool("ll", 5, 10, 0, 50);
        //自定义线程池拒接策略
        ThreadFactory namedThreadFactory = new ThreadFactoryBuilder()
                .setNameFormat("" + "-pool-%d").build();
        ThreadPoolExecutor ll = new ThreadPoolExecutor(2, 3, 0, TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(4),(r, executor1) -> {
            try {
                while (executor1.getQueue().size() > 4 * 0.8) {
                    System.out.println("线程池睡眠...");
                    Thread.sleep(50L);
                }
            } catch (InterruptedException e) {

                Thread.currentThread().interrupt();
            }
            executor1.submit(r);
        });
        int i =40;
        while (i-->0){
            System.out.println("=======");
            ll.submit(()->{
                System.out.println("时间："+System.currentTimeMillis());
            });
        }
        ll.shutdown();
        while (!ll.awaitTermination(30,TimeUnit.SECONDS)){
            System.out.println("等待线程池关闭");
        }


    }
}
