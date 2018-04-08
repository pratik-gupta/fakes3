package com.org.pratik;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.S3ClientOptions;

/**
 * Configuration class to load AWS credentials properties and instantiate the
 * helper beans.
 * 
 * @author VGup899
 *
 */
@Configuration
public class AppConfig {
	
	@Bean(name = "awsRegion")
	public Region getAWSPollyRegion() {
		return Region.getRegion(Regions.fromName("us-east-1"));
	}

	@Bean(name = "awsCredentialsProvider")
	public AWSCredentialsProvider getAWSCredentials() {
		BasicAWSCredentials awsCredentials = new BasicAWSCredentials("", "");
		return new AWSStaticCredentialsProvider(awsCredentials);
	}
	
	@Bean(name = "s3Client")
	public AmazonS3 ProcessReport(@Autowired Region awsRegion) {
		BasicAWSCredentials credentials = new BasicAWSCredentials("foo", "bar");
		AmazonS3Client s3Client = new AmazonS3Client(credentials);
		s3Client.setEndpoint("http://localhost:4567");
		s3Client.setS3ClientOptions(new S3ClientOptions().withPathStyleAccess(true));
		s3Client.setS3ClientOptions(
				  S3ClientOptions.builder()
				    .setPathStyleAccess(true).disableChunkedEncoding().build());
		return s3Client;
	}

}