package com.lirong.servicehi.dao;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

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
@Document(value = "user_test")
public class User {

//    @JsonProperty(value = "id")
    @Id
    private String id;
    //ObjectMapper会根据value字段找
//    @JsonProperty(value = "na_me")
    private String name;

    public User(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public User(String name) {
        this.name = name;
    }
    public User() {

    }

}
