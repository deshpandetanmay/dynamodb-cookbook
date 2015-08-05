package com.packtpub.dynamodb.cookbook;

import android.content.Context;
import android.util.Log;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;

/**
 * This class is used to get clients to the various AWS services. Before
 * accessing a client the credentials should be checked to ensure validity.
 */
public class AmazonClientManager {

	private static final String LOG_TAG = "AmazonClientManager";

	private AmazonDynamoDBClient ddb = null;
	private Context context;

	public AmazonClientManager(Context context) {
		this.context = context;
	}

	public AmazonDynamoDBClient ddb() {
		validateCredentials();
		return ddb;
	}

	public boolean hasCredentials() {
		return (!(Constants.ACCOUNT_ID.equalsIgnoreCase("458854225780")
				|| Constants.IDENTITY_POOL_ID
						.equalsIgnoreCase("us-east-1:45bce91b-99dc-4142-85a6-99b2ec27e863") || Constants.UNAUTH_ROLE_ARN
					.equalsIgnoreCase("arn:aws:iam::458854225780:role/Cognito_DynamoDBSampleUnauth_Role")));
	}

	public void validateCredentials() {

		if (ddb == null) {
			initClients();
		}
	}

	private void initClients() {
		CognitoCachingCredentialsProvider credentials = new CognitoCachingCredentialsProvider(
				context, Constants.ACCOUNT_ID, Constants.IDENTITY_POOL_ID,
				Constants.UNAUTH_ROLE_ARN, null, Regions.US_EAST_1);

		ddb = new AmazonDynamoDBClient(credentials);
		ddb.setRegion(Region.getRegion(Regions.US_WEST_2));
	}

	public boolean wipeCredentialsOnAuthError(AmazonServiceException ex) {
		Log.e(LOG_TAG, "Error, wipeCredentialsOnAuthError called" + ex);
		if (ex.getErrorCode().equals("IncompleteSignature")
				|| ex.getErrorCode().equals("InternalFailure")
				|| ex.getErrorCode().equals("InvalidClientTokenId")
				|| ex.getErrorCode().equals("OptInRequired")
				|| ex.getErrorCode().equals("RequestExpired")
				|| ex.getErrorCode().equals("ServiceUnavailable")

				|| ex.getErrorCode().equals("AccessDeniedException")
				|| ex.getErrorCode().equals("IncompleteSignatureException")
				|| ex.getErrorCode().equals(
						"MissingAuthenticationTokenException")
				|| ex.getErrorCode().equals("ValidationException")
				|| ex.getErrorCode().equals("InternalFailure")
				|| ex.getErrorCode().equals("InternalServerError")) {

			return true;
		}

		return false;
	}
}
