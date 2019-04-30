package com.lirong.servicehi.thread;

import com.lirong.servicehi.distributedlock.sequence.SequenceId;

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
    }

}
