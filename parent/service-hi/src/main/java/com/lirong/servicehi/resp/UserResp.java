package com.lirong.servicehi.resp;

import com.lirong.servicehi.dao.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Title: UserResp <br>
 * Description: UserResp <br>
 * Date: 2019年06月13日
 *
 * @author lirong
 * @version 1.0.0
 * @since jdk8
 */
@Repository
public interface UserResp extends MongoRepository<User,String> {
}
