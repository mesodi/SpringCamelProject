package com.robkaya.SpringCamelProject.Cognito;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.cognitoidentityprovider.CognitoIdentityProviderClient;

@Component
public class AwsCredentials {
    @Value("${aws.cognito.region}")
    private String awsRegion;
    @Value("${aws.cognito.pool_id}")
    private String cognitoPool_id;
    @Value("${aws.cognito.client_id}")
    private String cognitoClient_id;
    @Value("${aws.access-key}")
    private String awsAccessKey;
    @Value("${aws.secret-key}")
    private String awsSecretKey;

    public AwsCredentials() {
    }

    public AwsCredentials(String awsRegion, String cognitoPool_id, String cognitoClient_id, String awsAccessKey, String awsSecretKey) {
        this.awsRegion = awsRegion;
        this.cognitoPool_id = cognitoPool_id;
        this.cognitoClient_id = cognitoClient_id;
        this.awsAccessKey = awsAccessKey;
        this.awsSecretKey = awsSecretKey;
    }
    public String getCognitoPool_id() {
        return cognitoPool_id;
    }

    public String getCognitoClient_id() {
        return cognitoClient_id;
    }
    public CognitoIdentityProviderClient getCognitoClient(){
       return  CognitoIdentityProviderClient.builder()
               .region(Region.of(awsRegion))
               .credentialsProvider(StaticCredentialsProvider.create(AwsBasicCredentials.create(awsAccessKey,awsSecretKey)))
               .build();
    }


}
