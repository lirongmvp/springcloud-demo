package com.lirong.servicehi.thread;

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

    public MyThread() {

    }

    @Override
    public void run() {
        for(int a=0;a<3;a++){
            i++;
            System.out.println(Thread.currentThread().getName()+"===="+i);
        }
    }

    public static void init(){
        i=1;
    }

    public static void main(String[] args) {
        //多线程针对共享数据需要加锁，非共享数据不需要加锁，只是多线程并发
        Thread thread1 = new Thread(new MyThread());
        Thread thread2 = new Thread(new MyThread());
        thread1.start();
        thread2.start();
    }
}
