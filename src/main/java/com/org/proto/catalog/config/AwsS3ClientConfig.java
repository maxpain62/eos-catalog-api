package com.org.proto.catalog.config;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.FileWriter;
import java.io.IOException;

@Configuration
public class AwsS3ClientConfig {

    @Value("${cloud.aws.credentials.accessKey}")
    private String awsId;

    @Value("${cloud.aws.credentials.secretKey}")
    private String awsKey;

    @Value("${cloud.aws.region.static}")
    private String region;

    @Bean
    /*public AmazonS3 s3client() {

        BasicAWSCredentials awsCredentials = new BasicAWSCredentials(awsId, awsKey);
        AmazonS3 amazonS3Client = AmazonS3ClientBuilder.standard()
                .withRegion(Regions.fromName(region))
                .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
                .build();

        return amazonS3Client;
    }*/
    public AmazonS3 s3client() {

    try (FileWriter writer = new FileWriter("/tmp/aws-debug.txt", true)) {
        writer.write("ACCESS KEY USED: [" + awsId + "]\n");
        writer.write("SECRET KEY LENGTH: " + 
            (awsKey != null ? awsKey.length() : 0) + "\n");
        writer.write("REGION USED: " + region + "\n");
        writer.write("----\n");
    } catch (IOException e) {
        e.printStackTrace();
    }

    BasicAWSCredentials awsCredentials =
        new BasicAWSCredentials(awsId, awsKey);

    return AmazonS3ClientBuilder.standard()
            .withRegion(Regions.fromName(region))
            .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
            .build();
    }
}
