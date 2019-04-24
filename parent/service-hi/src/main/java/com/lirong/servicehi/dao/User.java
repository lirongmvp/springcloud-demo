package com.lirong.servicehi.dao;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * Title: User <br>
 * Description: User <br>
 * Date: 2019年04月21日
 *
 * @author 王东旭
 * @version 1.0.0
 * @since jdk8
 */
@Data
public class User {
    @JsonProperty(value = "id")
    private String id;
    //ObjectMapper会根据value字段找
    @JsonProperty(value = "na_me")
    private String name;

    public User(String id, String name) {
        this.id = id;
        this.name = name;
    }
}
