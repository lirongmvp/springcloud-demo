package com.lirong.zuulgateway.controller;

/**
 * Title: User <br>
 * Description: User <br>
 * Date: 2019年04月17日
 *
 * @author LiRong
 * @version 1.0.0
 * @since jdk8
 */
public class User {
    private String id;

    public User(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
