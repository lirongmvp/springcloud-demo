package com.lirong.servicehi.resp;

import com.lirong.servicehi.error.HttpError;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Title: HttpErrorResp <br>
 * Description: HttpErrorResp <br>
 * Date: 2019年07月08日
 *
 * @author lirong
 * @version 1.0.0
 * @since jdk8
 */
public interface HttpErrorResp extends MongoRepository<HttpError,String> {

}
