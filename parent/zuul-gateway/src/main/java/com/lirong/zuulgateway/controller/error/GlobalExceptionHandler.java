package com.lirong.zuulgateway.controller.error;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Title: GlobalExceptionHandler <br>
 * Description: GlobalExceptionHandler <br>
 * Date: 2019年04月16日
 *
 * @author LiRong
 * @version 1.0.0
 * @since jdk8
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    public void cacheError(Exception e) {
        if (e.getMessage().equals("Filter threw Exception")) {

        } else {
            e.printStackTrace();
        }
    }

}
