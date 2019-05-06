package com.lirong.servicehi.aws;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.Protocol;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.lirong.servicehi.aws.config.AwsConfiguration;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;

import java.io.UnsupportedEncodingException;

/**
 * Title: awsServer 连接处理<br>
 * Description: <br>
 * Date:2019年03月21日 <br>
 *
 * @author yangpeng
 * @version 1.0.0
 * @since jdk8
 */
//@Component
public class AwsS3ClientService implements AwsS3Client {

    private AwsConfiguration awsConfiguration;

    public AwsS3ClientService(AwsConfiguration awsConfiguration) {
        this.awsConfiguration = awsConfiguration;
    }

    @Override
    public AmazonS3 connectionAwsS3Server() throws UnsupportedEncodingException {
        return AmazonS3ClientBuilder.standard().withClientConfiguration(clientConfiguration())
                .withCredentials(awsCredentialsProvider()).withEndpointConfiguration(endpointConfiguration()).build();
    }

    @Override
    public ClientConfiguration clientConfiguration() {
        ClientConfiguration clientConfiguration = new ClientConfiguration();
        clientConfiguration.setProtocol(Protocol.HTTP);
        clientConfiguration.setConnectionTimeout(6000);
        clientConfiguration.setRequestTimeout(6000);
        clientConfiguration.setUseTcpKeepAlive(true);
        clientConfiguration.addHeader("Content-Type", "application/octet-stream");
        // 签名方式
        clientConfiguration.setSignerOverride(awsConfiguration.getSignerType());
        return clientConfiguration;
    }

    @Override
    public AwsClientBuilder.EndpointConfiguration endpointConfiguration() {
        return new AwsClientBuilder.EndpointConfiguration(awsConfiguration.getServiceEndpoint(),
                awsConfiguration.getRegion());
    }

    @Override
    public AWSCredentialsProvider awsCredentialsProvider() throws UnsupportedEncodingException {
        return new AWSStaticCredentialsProvider(
                new BasicAWSCredentials(Base64.encodeBase64String(awsConfiguration.getAccessKey().getBytes("UTF-8")),
                        DigestUtils.md5Hex(awsConfiguration.getSecretKey())));
    }
}
