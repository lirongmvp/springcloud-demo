package com.lirong.servicehi.error;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

/**
 * Title: HttpError <br>
 * Description: HttpError <br>
 * Date: 2019年07月08日
 *
 * @author lirong
 * @version 1.0.0
 * @since jdk8
 */
@Data
@Document(value = "http_error")
public class HttpError {
    @Id
    private String id;

    private String url;

    private String method;

    private String body;

    private String error;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime time = LocalDateTime.now();

    public HttpError(String url, String method, String body, String error) {
        this.url = url;
        this.method = method;
        this.body = body;
        this.error = error;
    }
}
