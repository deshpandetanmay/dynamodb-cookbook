package com.packtpub.dynamodb.cookbook.addressbook.services;

import org.springframework.stereotype.Component;

import com.amazonaws.auth.ClasspathPropertiesFileCredentialsProvider;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.datamodeling.AttributeEncryptor;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig;
import com.packtpub.dynamodb.cookbook.addressbook.domain.User;

@Component
public class UserServiceImpl implements UserService {
	static AmazonDynamoDBClient client = new AmazonDynamoDBClient(
			new ClasspathPropertiesFileCredentialsProvider());

	static DynamoDBMapper mapper = new DynamoDBMapper(client,
			DynamoDBMapperConfig.DEFAULT, new AttributeEncryptor(
					EncryptorProvider.getProvider()));

	static {
		client.setRegion(Region.getRegion(Regions.US_WEST_2));
	}

	public void createUser(User user) {

		mapper.save(user);
	}

	public User getUserByEmail(String email) {
		return mapper.load(User.class, email);

	}

}
