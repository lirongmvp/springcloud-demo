package com.lirong.servicehi.sevice;

import com.mongodb.Cursor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.util.CloseableIterator;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

/**
 * Title: MongoOps <br>
 * Description: MongoOps <br> 获取mongo所有数据
 * Date: 2019年04月30日
 *
 * @author lirong
 * @version 1.0.0
 * @since jdk8
 */
@Component
public class MongoOps {
    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     *
     * @param collectionClass 数据库中的类
     * @return CloseableIterator  需要关闭{@link CloseableIterator#close()}
     * Returns a {@link CloseableIterator} that wraps the a Mongo DB {@link Cursor} that needs to be closed
     */
    public <T> CloseableIterator<T> getAllAsStream(Class<T> collectionClass){

        mongoTemplate.getCollection("user_test");
        Criteria criteria=new Criteria();
        return mongoTemplate.stream(new Query(criteria), collectionClass);
    }

    public <T> void getAllAsStream(Class<T> collectionClass, Consumer<? super T> action){
        Criteria criteria=new Criteria();
        CloseableIterator<T> stream = mongoTemplate.stream(new Query(criteria), collectionClass);
        stream.forEachRemaining(action);
        stream.close();
    }
}
