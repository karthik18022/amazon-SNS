package com.vital.sns.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.sns.AmazonSNSClient;
import com.amazonaws.services.sns.AmazonSNSClientBuilder;

@Configuration
public class AmazonSNSConfiguration {

	@Primary
	@Bean
	public AmazonSNSClient amazonSNSClient() {
		return (AmazonSNSClient) AmazonSNSClientBuilder.standard()
				.withRegion(Regions.US_EAST_1)
				.withCredentials(
						new AWSStaticCredentialsProvider(
								new BasicAWSCredentials("AKIATSZ5F2LXD4RHG7VR","Qg5MIx+3lbiao6P9zbIDLuUiPlbBuhBxGOZjqFIl")) 
						).build();
	}
}
