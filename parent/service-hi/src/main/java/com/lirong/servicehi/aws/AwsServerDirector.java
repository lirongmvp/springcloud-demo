package com.lirong.servicehi.aws;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.util.List;

/**
 * Title: 处理 对象、bucket 的执行者<br>
 * Description:处理 对象、bucket 的执行者 <br>
 * Date:2019年03月21日 <br>
 *
 * @author yangpeng
 * @version 1.0.0
 * @since jdk8
 */
//@Component
public class AwsServerDirector {
    private final Logger log = LoggerFactory.getLogger(AwsServerDirector.class);
    private AmazonS3 amazonS3;

    @Autowired
    public AwsServerDirector(AwsS3ClientService awsS3ClientService) {
        try {
            this.amazonS3 = awsS3ClientService.connectionAwsS3Server();
        } catch (Exception e) {
            log.error("connection AWS-S3 server Error:{}", e);
        }
    }

    /**
     * 创建bucket
     * 
     * @param bucketName
     *            名称
     */
    public void createBucket(String bucketName) {
        if (!amazonS3.doesBucketExistV2(bucketName)) {
            amazonS3.createBucket(bucketName);
            log.info("build  bucket: {} success", bucketName);
        }
        log.info("bucket ：{}  exist ", bucketName);
    }

    /**
     * 上传对象
     * 
     * @param bucketName
     *            存储桶名称
     * @param key
     *            键
     * @param file
     *            文件
     * @return
     */
    public void putObject(String bucketName, String key, File file) {
        createBucket(bucketName);
        if (amazonS3.doesObjectExist(bucketName, key)) {
            log.info("object  exist, delete object：{} in bucket：{}", key, bucketName);
            amazonS3.deleteObject(new DeleteObjectRequest(bucketName, key));
        }
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, key, file);
        amazonS3.putObject(putObjectRequest);
        log.info("create new object : {} in bucket： {} , file: {}", key, bucketName, file);
    }

    /**
     * 批量删除key前缀的对象
     * 
     * @param bucketName
     *            存储桶
     * @param prefix
     *            key前缀
     */
    public void deleteObjectsByPrefix(String bucketName, String prefix) {
        ObjectListing objectListing = amazonS3.listObjects(bucketName, prefix);
        if (objectListing != null) {
            List<S3ObjectSummary> objectSummaries = objectListing.getObjectSummaries();
            if (objectSummaries != null && !objectSummaries.isEmpty()) {
                objectSummaries.stream().forEach(s3ObjectSummary -> {
                    amazonS3.deleteObject(new DeleteObjectRequest(bucketName, s3ObjectSummary.getKey()));
                    log.info("delete object by key :{} success", s3ObjectSummary.getKey());
                });
            } else {
                log.info("no objects in  prefix key： {}  of bucket： {}", prefix, bucketName);
            }
        } else {
            log.info("no prefix key： {} in the bucket： {}", prefix, bucketName);
        }
    }

    /**
     * 删除单个文件
     * 
     * @param bucketName
     *            桶名称
     * @param key
     *            对象键
     */
    public void deleteObject(String bucketName, String key) {
        amazonS3.deleteObject(bucketName, key);
        log.info("delete object :{}  in bucketName:{} ", key, bucketName);
    }

    /**
     * 对象是否存在
     * 
     * @param bucketName
     *            桶名称
     * @param key
     *            对象键
     * @return true 存在 false 不存在
     */
    public Boolean existObject(String bucketName, String key) {
        return amazonS3.doesObjectExist(bucketName, key);
    }

}
