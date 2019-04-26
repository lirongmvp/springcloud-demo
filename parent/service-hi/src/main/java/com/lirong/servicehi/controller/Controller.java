package com.lirong.servicehi.controller;

import com.lirong.servicehi.dao.User;
import com.lirong.servicehi.distributedlock.RedisTool;
import com.lirong.servicehi.distributedlock.lock.RedisDistributedLock;
import com.lirong.servicehi.distributedlock.mylock.MyDistributedLock;
import com.lirong.servicehi.distributedlock.mylock.MyRedisDistributedLock;
import com.lirong.servicehi.sevice.myService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

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
    private static final Logger LOGGER = LoggerFactory.getLogger(Controller.class);
    @Value("${server.port}")
    private String port;
    @Autowired
    private myService myService;

    private RedisTemplate<String, String> redisTemplate;

    private MyDistributedLock myDistributedLock;

    private RedisDistributedLock redisDistributedLock;
    @Autowired
    public Controller(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
        this.myDistributedLock = new MyRedisDistributedLock(redisTemplate);
        this.redisDistributedLock = new RedisDistributedLock(redisTemplate);
    }

    @GetMapping("/test")
    public void hi2() {
        myService.test();
    }


    @GetMapping("/hi")
    public String hi(@RequestParam(value = "name", defaultValue = "forezp") String name) {
        return "hi " + name + " ,i am from port:" + port;
    }

    @GetMapping("/down/file")
    public void file(HttpServletResponse response, HttpServletRequest request) throws Exception {

        File file = new File("F:\\music.m4a");
        try (InputStream in = new FileInputStream(file)) {
            int len = 0;
            byte[] buffer = new byte[1024];
            OutputStream out = response.getOutputStream();
//            response.setContentType("audio/x-m4a");
            response.addHeader("Accept-Ranges", "bytes");
            response.addHeader("Content-Length", "" + file.length());
            while ((len = in.read(buffer)) > 0) {
                out.write(buffer, 0, len);
            }
            out.flush();
        }
    }

    @GetMapping("/flux")
    public Mono<User> hi2(@RequestParam(value = "name", defaultValue = "forezp") String name) {

        return Mono.just(new User("1", name));
    }

    @GetMapping("/redisLock/{name}")
    public String test(@PathVariable(value = "name") String name) {
        String s = UUID.randomUUID().toString();
        int i = 0;
        //拿3次锁，没有拿到返回指定信息
        while (i < 3) {
            boolean b = RedisTool.tryGetDistributedLock(redisTemplate, name, s, 60);
            try {
                if (b) {
                    //模拟方法执行
                    Thread.sleep(5000L);
                    RedisTool.releaseDistributedLock(redisTemplate, name, s);
                    LOGGER.info("i={}",i);
                    return name;
                }
                i++;

                Thread.sleep(100L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        LOGGER.info("i={}",i);
        return "没有拿到锁";
    }

    @GetMapping("/Lock/{name}")
    public String test2(@PathVariable(value = "name") String name) {

        try {
            boolean lock = myDistributedLock.getLock(name,3);
            if(!lock){
                LOGGER.warn("warn no lock");
                return "no lock";
            }
//            Thread.sleep(20000L);
            //可读性高
            TimeUnit.SECONDS.sleep(10);

            boolean b = myDistributedLock.releaseLock(name);
            if(b){
                return "OK";
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "false";
    }

}
