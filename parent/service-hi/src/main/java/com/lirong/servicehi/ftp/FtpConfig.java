package com.lirong.servicehi.ftp;

import com.github.pgcomb.download.ftp.FtpProp;

/**
 * Title: FtpConfig <br>
 * Description: FtpConfig <br>
 * Date: 2018年09月21日
 *
 * @author 王东旭
 * @version 1.0.0
 * @since jdk8
 */
public class FtpConfig implements FtpProp, PoolProp {

    private int maxTotal;

    private int maxIdle;

    private int minIdle;

    private String username;

    private String password;

    private String encode;

    private boolean localActive;

    private String ip;

    private int port;

    public FtpConfig(int maxTotal, int maxIdle, int minIdle, String username, String password, String encode,
                     boolean localActive, String ip, int port) {
        this.maxTotal = maxTotal;
        this.maxIdle = maxIdle;
        this.minIdle = minIdle;
        this.username = username;
        this.password = password;
        this.encode = encode;
        this.localActive = localActive;
        this.ip = ip;
        this.port = port;
    }

    @Override
    public int maxTotal() {
        return maxTotal;
    }

    @Override
    public int maxIdle() {
        return maxIdle;
    }

    @Override
    public int minIdle() {
        return minIdle;
    }

    @Override
    public String username() {
        return username;
    }

    @Override
    public String password() {
        return password;
    }

    @Override
    public String encode() {
        return encode;
    }

    @Override
    public boolean localActive() {
        return localActive;
    }

    @Override
    public String ip() {
        return ip;
    }

    @Override
    public int port() {
        return port;
    }
}
