package com.lirong.servicehi.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;

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

    @Value("${server.port}")
    String port;

    @GetMapping("/hi")
    public String hi(@RequestParam(value = "name", defaultValue = "forezp") String name){
        return "hi " + name + " ,i am from port:" + port;
    }

    @GetMapping("/down/file")
    public void file(HttpServletResponse response, HttpServletRequest request) throws Exception {

        File file = new File("F:\\music.m4a");
        try (InputStream in = new FileInputStream(file)) {
            int len = 0;
            byte[] buffer = new byte[200];
            OutputStream out = response.getOutputStream();
//            response.setContentType("audio/x-m4a");
            response.addHeader("Accept-Ranges", "bytes");
            response.addHeader("Content-Length", ""+file.length());
            while ((len = in.read(buffer)) > 0) {
                out.write(buffer, 0, len);
            }
            out.flush();
        }
    }
}
