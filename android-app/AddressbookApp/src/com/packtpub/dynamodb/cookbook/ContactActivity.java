package com.packtpub.dynamodb.cookbook;

import com.packtpub.dynamodb.cookbook.DynamoDBManager.AppContact;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class ContactActivity extends Activity {
	public static AmazonClientManager clientManager = null;
	private static final String TAG = "ContactActivity";
	EditText name;
	EditText email;
	EditText phone;

	AppContact contact = new AppContact();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.contacts);
		clientManager = new AmazonClientManager(this);

	}

	public void onClickAddNew(View v) {
		Log.d(TAG, "Saving Contact");
		email = (EditText) findViewById(R.id.contact_email);
		name = (EditText) findViewById(R.id.contact_fullname);
		phone = (EditText) findViewById(R.id.contact_phone);
		SharedPreferences sp = getSharedPreferences("addressbook",
				Activity.MODE_PRIVATE);
		String parentdId = sp.getString("parentEmailId", "default");
		contact.setEmail(email.getText().toString());
		contact.setName(name.getText().toString());
		contact.setPhone(phone.getText().toString());
		contact.setParentId(parentdId);

		new DynamoDBManagerTask().execute(DynamoDBManagerType.SAVE_CONTACT);

	}

	private class DynamoDBManagerTask extends
			AsyncTask<DynamoDBManagerType, Void, DynamoDBManagerTaskResult> {

		protected DynamoDBManagerTaskResult doInBackground(
				DynamoDBManagerType... types) {

			DynamoDBManagerTaskResult result = new DynamoDBManagerTaskResult();

			result.setTaskType(types[0]);

			if (types[0] == DynamoDBManagerType.SAVE_CONTACT) {

				DynamoDBManager.addNewContact(contact);

			}

			return result;
		}

		protected void onPostExecute(DynamoDBManagerTaskResult result) {

			if (result.getTaskType() == DynamoDBManagerType.SAVE_CONTACT) {

				Toast.makeText(ContactActivity.this,
						"Contact Saved Successfully !!", Toast.LENGTH_SHORT)
						.show();
				redirectToActivity(null, ContactListActivity.class);
			}
		}
	}

	public void redirectToActivity(View view, Class<?> cls) {
		Intent intent = new Intent(this, cls);
		startActivity(intent);
	}

	private enum DynamoDBManagerType {
		SAVE_CONTACT
	}

	private class DynamoDBManagerTaskResult {
		private DynamoDBManagerType taskType;

		public DynamoDBManagerType getTaskType() {
			return taskType;
		}

		public void setTaskType(DynamoDBManagerType taskType) {
			this.taskType = taskType;
		}

	}
}
