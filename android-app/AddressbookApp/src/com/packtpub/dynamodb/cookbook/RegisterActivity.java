package com.packtpub.dynamodb.cookbook;

import com.packtpub.dynamodb.cookbook.DynamoDBManager.AppUser;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class RegisterActivity extends Activity {
	public static AmazonClientManager clientManager = null;
	private static final String TAG = "RegisterActivity";

	EditText name;
	EditText email;
	EditText password;

	AppUser user = new AppUser();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		Log.d(TAG, "On Create");
		super.onCreate(savedInstanceState);
		// Set View to register.xml
		setContentView(R.layout.register);
		clientManager = new AmazonClientManager(this);
		TextView loginScreen = (TextView) findViewById(R.id.link_to_login);

		// Listening to Login Screen link
		loginScreen.setOnClickListener(new View.OnClickListener() {

			public void onClick(View arg0) {
				finish();
			}
		});
	}

	public void onClickReg(View v) {
		Log.d(TAG, "Registering user");
		name = (EditText) findViewById(R.id.reg_fullname);
		email = (EditText) findViewById(R.id.reg_email);
		password = (EditText) findViewById(R.id.reg_password);

		user.setEmail(email.getText().toString());
		user.setName(name.getText().toString());
		user.setPassword(password.getText().toString());
		new DynamoDBManagerTask().execute(DynamoDBManagerType.INSERT_USER);
		redirectToLogin(v);

	}
	
	public void redirectToLogin(View view) {
	    Intent intent = new Intent(this, LoginActivity.class);
	    startActivity(intent);
	}

	private class DynamoDBManagerTask extends
			AsyncTask<DynamoDBManagerType, Void, DynamoDBManagerTaskResult> {

		protected DynamoDBManagerTaskResult doInBackground(
				DynamoDBManagerType... types) {

			DynamoDBManagerTaskResult result = new DynamoDBManagerTaskResult();

			result.setTaskType(types[0]);

			if (types[0] == DynamoDBManagerType.INSERT_USER) {

				DynamoDBManager.register(user);

			}

			return result;
		}

		protected void onPostExecute(DynamoDBManagerTaskResult result) {

			if (result.getTaskType() == DynamoDBManagerType.INSERT_USER) {
				Toast.makeText(RegisterActivity.this,
						"Registration Success!! Please Login.", Toast.LENGTH_SHORT)
						.show();
			}
		}
	}

	private enum DynamoDBManagerType {
		INSERT_USER
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
