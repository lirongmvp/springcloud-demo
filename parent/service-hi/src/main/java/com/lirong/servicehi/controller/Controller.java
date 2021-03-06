package com.lirong.servicehi.controller;

import com.lirong.servicehi.config.LimitFieldConfig;
import com.lirong.servicehi.dao.User;
import com.lirong.servicehi.distributedlock.RedisTool;
import com.lirong.servicehi.distributedlock.lock.RedisDistributedLock;
import com.lirong.servicehi.distributedlock.mylock.MyDistributedLock;
import com.lirong.servicehi.distributedlock.mylock.MyRedisDistributedLock;
import com.lirong.servicehi.distributedlock.sequence.SequenceId;
import com.lirong.servicehi.resp.UserResp;
import com.lirong.servicehi.sevice.MongoOps;
import com.lirong.servicehi.sevice.myService;
import com.lirong.servicehi.thread.MyThread;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.util.CloseableIterator;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
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
@Slf4j
public class Controller {
    private static final Logger LOGGER = LoggerFactory.getLogger(Controller.class);
    @Value("${server.port}")
    private String port;
    @Autowired
    private myService myService;

    private RedisTemplate<String, String> redisTemplate;

    private MyDistributedLock myDistributedLock;

    private RedisDistributedLock redisDistributedLock;

    private SequenceId sequenceId;
    @Autowired
    private MongoOps mongoOps;
    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private UserResp userResp;
    @Autowired
    private LimitFieldConfig limitFieldConfig;

    @Autowired
    public Controller(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
        this.myDistributedLock = new MyRedisDistributedLock(redisTemplate);
        this.redisDistributedLock = new RedisDistributedLock(redisTemplate);
    }
    @GetMapping("/apollo")
    public void apollo(){
        System.out.println(limitFieldConfig.toString());
    }

    @GetMapping("/mongo")
    public void mongo() {
        CloseableIterator<User> stream = mongoOps.getAllAsStream(User.class);
        stream.forEachRemaining(System.out::println);
//        mongoOps.getAllAsStream(User.class, System.out::println);
    }


    @GetMapping("/getparm")
    public void getparm(HttpServletRequest request) {
        String name1 = request.getParameter("name");
        if (name1 instanceof String) {
            System.out.println(name1 + "字符串");
        }
        System.out.println(name1);
    }

    @GetMapping("/mongoInit")
    public void mongoInit() {
        for (int i = 0; i < 10; i++) {
            mongoTemplate.insert(new User("lirong" + i));
        }
    }
    @GetMapping("/add")
    public void add() {
        userResp.save(new User("lirong"));
    }


    /**
     * test 生成ID是否规范
     */
    @GetMapping("/getId")
    public void getId() {
        int i = 100;
        MyThread.setSequenceId(sequenceId);
        while (i-- > 0) {
            MyThread myThread = new MyThread();
            new Thread(myThread).start();
        }
    }

    /**
     * 不加注解，只有是表单数据格式"Content type 'multipart/form-data;charset=UTF-8'
     * 加注解,"Content type 'application/json;charset=UTF-8'
     */
    @PostMapping("/test")
    public void hi2(@RequestBody @Validated User user) throws Exception  {
        myService.test();
        System.out.println(user);
    }
    @GetMapping("/test1")
    public void hi21(@Validated User user) throws Exception  {
        myService.test();
        System.out.println(user);
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
        String requestId = UUID.randomUUID().toString();
        int i = 0;
        //拿3次锁，没有拿到返回指定信息
        while (i < 3) {
            boolean b = RedisTool.tryGetDistributedLock(redisTemplate, name, requestId, 60);
            try {
                if (b) {
                    //模拟方法执行
                    Thread.sleep(5000L);
                    RedisTool.releaseDistributedLock(redisTemplate, name, requestId);
                    LOGGER.info("i={}", i);
                    return name;
                }
                i++;
                Thread.sleep(100L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        LOGGER.info("i={}", i);
        return "没有拿到锁";
    }

    @GetMapping("/Lock/{name}")
    public String test2(@PathVariable(value = "name") String name) {
        try {
            System.out.println("================");
            String requestId = UUID.randomUUID().toString();
            boolean lock = myDistributedLock.getLock(name, 3, requestId);
            if (!lock) {
                LOGGER.warn("warn no lock");
                return "no lock";
            }
            TimeUnit.SECONDS.sleep(10);
            boolean b = myDistributedLock.releaseLock(name, requestId);
            if (b) {
                return "OK";
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "false";
    }


}
