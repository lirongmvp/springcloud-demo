package com.lirong.servicehi.aws.config;

import com.amazonaws.services.s3.model.Region;

/**
 * Title:Aws配置 <br>
 * Description:Aws配置 <br>
 * Date:2019年03月21日 <br>
 *
 * @author yangpeng
 * @version 1.0.0
 * @since jdk8
 */
//@Configuration
//@ConfigurationProperties(prefix = "aws")
public class AwsConfiguration {

    private String accessKey;
    private String secretKey;
    /**
     *
     */
    private String serviceEndpoint;
    private String region = Region.CN_Northwest_1.getFirstRegionId();
    private String bucketName = "wonder";
    /**
     * 签名方式
     */
    private String SignerType = "S3SignerType";

    public String getAccessKey() {
        return accessKey;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public String getServiceEndpoint() {
        return serviceEndpoint;
    }

    public void setServiceEndpoint(String serviceEndpoint) {
        this.serviceEndpoint = serviceEndpoint;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getBucketName() {
        return bucketName;
    }

    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }

    public String getSignerType() {
        return SignerType;
    }

    public void setSignerType(String signerType) {
        SignerType = signerType;
    }

    @Override
    public String toString() {
        return "AwsConfiguration{" + "accessKey='" + accessKey + '\'' + ", secretKey='" + secretKey + '\''
                + ", serviceEndpoint='" + serviceEndpoint + '\'' + ", region='" + region + '\'' + ", bucketName='"
                + bucketName + '\'' + ", SignerType='" + SignerType + '\'' + '}';
    }
}
