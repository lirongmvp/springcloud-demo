package com.lirong.servicehi.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Title: UserAnno <br>
 * Description: UserAnno <br>
 * Date: 2019年05月05日
 *
 * @author lirong
 * @version 1.0.0
 * @since jdk8
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface UserAnno {

    String name();
}
