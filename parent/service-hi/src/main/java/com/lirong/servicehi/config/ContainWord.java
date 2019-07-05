package com.lirong.servicehi.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.Set;

/**
 * Title: ContainWord <br>
 * Description: ContainWord <br>
 * Date: 2019年01月25日
 *
 * @author 王东旭
 * @version 1.0.0
 * @since jdk8
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ContainWord {
    private String word;
    private String name;
    private Set<ContainWordValue> values;
}
