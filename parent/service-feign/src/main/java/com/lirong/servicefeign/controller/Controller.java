package com.lirong.servicefeign.controller;

import com.lirong.servicefeign.service.FeignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Title: Controller <br>
 * Description: Controller <br>
 * Date: 2019年04月15日
 *
 * @author LiRong
 * @version 1.0.0
 * @since jdk8
 */
@RestController
public class Controller {
    @Autowired
    private FeignService feignService;

    @GetMapping(value = "/hi")
    public String sayHi(@RequestParam String name) {
        return feignService.wenServiceHi(name);
    }
}
