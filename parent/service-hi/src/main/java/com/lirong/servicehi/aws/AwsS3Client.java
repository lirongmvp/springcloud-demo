package com.lirong.servicehi.aws;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;

import java.io.UnsupportedEncodingException;

/**
 * Title: <br>
 * Description: <br>
 * Date:2019年03月21日 <br>
 *
 * @author yangpeng
 * @version 1.0.0
 * @since jdk8
 */
public interface AwsS3Client {

    /**
     * 连接aws
     *
     * @return
     */
    AmazonS3 connectionAwsS3Server() throws UnsupportedEncodingException;

    /**
     * 客户端配置
     *
     * @return
     */
    ClientConfiguration clientConfiguration();

    /**
     * 端点配置
     *
     * @return
     */
    AwsClientBuilder.EndpointConfiguration endpointConfiguration();

    /**
     * 登录凭证配置
     *
     * @return
     */
    AWSCredentialsProvider awsCredentialsProvider() throws UnsupportedEncodingException;
}
