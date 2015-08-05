package com.packtpub.dynamodb.cookbook;

import android.util.Log;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBAttribute;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBAutoGeneratedKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBHashKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBRangeKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBScanExpression;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBTable;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.PaginatedParallelScanList;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ComparisonOperator;
import com.amazonaws.services.dynamodbv2.model.Condition;


import java.util.ArrayList;
import java.util.List;

public class DynamoDBManager {

	private static final String TAG = "DynamoDBManager";

	public static void register(AppUser appUser) {
		AmazonDynamoDBClient ddb = RegisterActivity.clientManager.ddb();
		DynamoDBMapper mapper = new DynamoDBMapper(ddb);

		try {

			Log.d(TAG, "Registering user");
			mapper.save(appUser);
			Log.d(TAG, "User inserted");

		} catch (AmazonServiceException ex) {
			Log.e(TAG, "Error inserting users");
			RegisterActivity.clientManager.wipeCredentialsOnAuthError(ex);
		}
	}

	public static void addNewContact(AppContact appContact) {
		AmazonDynamoDBClient ddb = ContactActivity.clientManager.ddb();
		DynamoDBMapper mapper = new DynamoDBMapper(ddb);

		try {

			Log.d(TAG, "Adding new contact");
			mapper.save(appContact);
			Log.d(TAG, "Contact saved");

		} catch (AmazonServiceException ex) {
			Log.e(TAG, "Error adding new contact");
			ContactActivity.clientManager.wipeCredentialsOnAuthError(ex);
		}
	}

	public static List<AppContact> getAllContacts(String email) {
		AmazonDynamoDBClient ddb = ContactListActivity.clientManager.ddb();
		DynamoDBMapper mapper = new DynamoDBMapper(ddb);
		List<AppContact> contactList = new ArrayList<AppContact>();

		try {

			Log.d(TAG, "Fetching contacts");
			// Condition to get records for given userEmailId
			Condition condition = new Condition().withComparisonOperator(
					ComparisonOperator.EQ.toString()).withAttributeValueList(
					new AttributeValue().withS(email));
			DynamoDBScanExpression scanExpression = new DynamoDBScanExpression();
			scanExpression.addFilterCondition("parentId", condition);

			// Paginated scan in case no. of contacts are too much
			PaginatedParallelScanList<AppContact> contacts = mapper
					.parallelScan(AppContact.class, scanExpression, 4);
			for (AppContact contact : contacts) {
				contactList.add(contact);
			}

		} catch (AmazonServiceException ex) {
			Log.e(TAG, "Error adding new contact");
			ContactListActivity.clientManager.wipeCredentialsOnAuthError(ex);
		}
		return contactList;
	}

	public static AppUser getUserByEmail(String email) {

		AmazonDynamoDBClient ddb = LoginActivity.clientManager.ddb();
		DynamoDBMapper mapper = new DynamoDBMapper(ddb);

		try {
			AppUser appUser = mapper.load(AppUser.class, email);

			return appUser;

		} catch (AmazonServiceException ex) {
			LoginActivity.clientManager.wipeCredentialsOnAuthError(ex);
		}

		return null;
	}
	@DynamoDBTable(tableName = Constants.USER_TABLE)
	public static class AppUser {

		private String email;
		private String name;
		private String password;

		@DynamoDBHashKey
		public String getEmail() {
			return email;
		}

		public void setEmail(String email) {
			this.email = email;
		}

		@DynamoDBAttribute(attributeName = "name")
		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		@DynamoDBAttribute(attributeName = "password")
		public String getPassword() {
			return password;
		}

		public void setPassword(String password) {
			this.password = password;
		}

	}

	@DynamoDBTable(tableName = Constants.CONTACT_TABLE)
	public static class AppContact {

		private String id;
		private String email;
		private String name;
		private String phone;
		private String parentId;

		@DynamoDBHashKey
		public String getEmail() {
			return email;
		}

		public void setEmail(String email) {
			this.email = email;
		}

		@DynamoDBAttribute(attributeName = "name")
		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		@DynamoDBHashKey(attributeName = "id")
		@DynamoDBAutoGeneratedKey
		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		@DynamoDBAttribute(attributeName = "phone")
		public String getPhone() {
			return phone;
		}

		public void setPhone(String phone) {
			this.phone = phone;
		}

		@DynamoDBAttribute(attributeName = "parentId")
		@DynamoDBRangeKey
		public String getParentId() {
			return parentId;
		}

		public void setParentId(String parentId) {
			this.parentId = parentId;
		}

	}

}
