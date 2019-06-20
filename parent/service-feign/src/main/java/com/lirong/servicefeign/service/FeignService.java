package com.lirong.servicefeign.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Title: FeignService <br>
 * Description: FeignService <br>
 * Date: 2019年04月15日
 *
 * @author LiRong
 * @version 1.0.0
 * @since jdk8
 */
@FeignClient(value = "service-hi")
public interface FeignService {
    /**
     * 返回值关注body中的数据结构
     *
     * @param name
     * @return
     */
    @GetMapping("hi")
    String wenServiceHi(@RequestParam(value = "name") String name);

}
