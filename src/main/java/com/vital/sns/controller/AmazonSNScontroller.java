package com.vital.sns.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.amazonaws.services.sns.AmazonSNSClient;
import com.amazonaws.services.sns.model.ListPlatformApplicationsResult;
import com.amazonaws.services.sns.model.ListSubscriptionsByTopicResult;
import com.amazonaws.services.sns.model.ListTopicsResult;
import com.amazonaws.services.sns.model.PlatformApplication;
import com.amazonaws.services.sns.model.PublishRequest;
import com.amazonaws.services.sns.model.SubscribeRequest;
import com.amazonaws.services.sns.model.Subscription;
import com.amazonaws.services.sns.model.Topic;
import com.amazonaws.services.sns.model.UnsubscribeRequest;

@RestController
public class AmazonSNScontroller {

	private static final String TOPIC_ARN = "";
	private static final String EMAIL = "email";

	@Autowired
	private AmazonSNSClient amazonSNSClient;

	@GetMapping("/subscribe/{email}")
	public String subscribeToSNSTopic(@PathVariable("email") String email) {
		SubscribeRequest subscribeRequest = new SubscribeRequest(TOPIC_ARN, EMAIL, email);
		this.amazonSNSClient.subscribe(subscribeRequest);
		return "check your email";
	}

	@DeleteMapping("/unsubscribe/{arn}")
	public String unSubscribeToSNSTopic(@PathVariable("arn") String arn) {
		UnsubscribeRequest unsubscribeRequest = new UnsubscribeRequest(arn);
		this.amazonSNSClient.unsubscribe(unsubscribeRequest);
        return "unsubscribed sccessfully";
	}

	@GetMapping("/publish/{msg}")
	public String publishMessageToTopic(@PathVariable("msg") String msg) {
		PublishRequest publishRequest = new PublishRequest(TOPIC_ARN, msg, "sns email notification");
		this.amazonSNSClient.publish(publishRequest);
		return "Message published";
	}

	@GetMapping("/subscribtion/list/{topic}")
	public List<Subscription> subscribtionlist(@PathVariable("topic") String topic) {
		ListSubscriptionsByTopicResult result = this.amazonSNSClient.listSubscriptionsByTopic(TOPIC_ARN);
		return result.getSubscriptions();
	}

	@GetMapping("/topicArns")
	public List<Topic> listAllTopics() {
		List<Topic> topicArns = new ArrayList<>();
		ListTopicsResult result = this.amazonSNSClient.listTopics();
		topicArns.addAll(result.getTopics());
		while (result.getNextToken() != null) {
			result = this.amazonSNSClient.listTopics(result.getNextToken());
			topicArns.addAll(result.getTopics());
		}
		return topicArns;
	}

	@GetMapping("/topicArns/platform")
	public List<PlatformApplication> listAllPlatformApplication() {
		List<PlatformApplication> topicArns = new ArrayList<>();
		ListPlatformApplicationsResult result = this.amazonSNSClient.listPlatformApplications();
		topicArns.addAll(result.getPlatformApplications());
		return topicArns;
	}

}
