package com.lirong.servicehi.config;

import lombok.Data;

import java.util.List;

/**
 * Title:限制字段 <br>
 * Description:限制字段 <br>
 * Date:2019年02月22日 <br>
 *
 * @author yangpeng
 * @version 1.0.0
 * @since jdk8
 */
@Data
public class LimitFieldConfig {

    private List<ContainWord> limitFields;
}
