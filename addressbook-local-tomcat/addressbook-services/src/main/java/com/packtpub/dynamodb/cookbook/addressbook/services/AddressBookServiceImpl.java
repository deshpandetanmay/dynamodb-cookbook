package com.packtpub.dynamodb.cookbook.addressbook.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.amazonaws.auth.ClasspathPropertiesFileCredentialsProvider;

import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.PaginatedParallelScanList;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ComparisonOperator;
import com.amazonaws.services.dynamodbv2.model.Condition;
import com.packtpub.dynamodb.cookbook.addressbook.domain.Contact;

@Component
public class AddressBookServiceImpl implements AddressBookService {

	static AmazonDynamoDBClient client = new AmazonDynamoDBClient(
			new ClasspathPropertiesFileCredentialsProvider());

	static {
		client.setRegion(Region.getRegion(Regions.US_WEST_2));

	}
	DynamoDBMapper mapper = new DynamoDBMapper(client);

	public void addContact(Contact contact) {
		mapper.save(contact);

	}

	public List<Contact> getAllContacts(String userEmailId) {
		List<Contact> contactList = new ArrayList<Contact>();
		// Condition to get records for given userEmailId
		Condition condition = new Condition().withComparisonOperator(
				ComparisonOperator.EQ.toString()).withAttributeValueList(
				new AttributeValue().withS(userEmailId));
		DynamoDBScanExpression scanExpression = new DynamoDBScanExpression();
		scanExpression.addFilterCondition("parentId", condition);

		// Paginated scan in case no. of contacts are too much
		PaginatedParallelScanList<Contact> contacts = mapper.parallelScan(
				Contact.class, scanExpression, 4);
		for (Contact contact : contacts) {
			contactList.add(contact);
		}
		return contactList;

	}

}
