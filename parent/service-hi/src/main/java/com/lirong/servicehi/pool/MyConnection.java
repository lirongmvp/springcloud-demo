package com.lirong.servicehi.pool;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Title: MyConnection <br>
 * Description: MyConnection <br>
 * Date: 2019年06月11日
 *
 * @author lirong
 * @version 1.0.0
 * @since jdk8
 */
public class MyConnection  {
    private static Logger logger = LoggerFactory.getLogger(MyConnection.class);

    private String name;
    private boolean connected;

    public MyConnection(String name) {
        this.name = name;
    }

    public void connect() {
        this.connected = true;
        logger.info(name + ": " + connected);
    }

    public void close() {
        this.connected = false;
        logger.info(name + ": " + connected);
    }

    public boolean isConnected() {
        return this.connected;
    }

    public String getName() {
        return this.name;
    }

    public void print(String s) {
        logger.info(s+this.name);
    }
}
