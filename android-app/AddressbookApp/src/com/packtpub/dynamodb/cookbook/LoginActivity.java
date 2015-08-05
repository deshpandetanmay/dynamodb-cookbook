package com.packtpub.dynamodb.cookbook;

import com.packtpub.dynamodb.cookbook.DynamoDBManager.AppUser;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends Activity {
	public static AmazonClientManager clientManager = null;
	private static final String TAG = "LoginActivity";
	EditText email;
	EditText password;

	AppUser user = new AppUser();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// setting default screen to login.xml
		setContentView(R.layout.login);
		clientManager = new AmazonClientManager(this);

		TextView registerScreen = (TextView) findViewById(R.id.link_to_register);

		// Listening to register new account link
		registerScreen.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				// Switching to Register screen
				Intent i = new Intent(getApplicationContext(),
						RegisterActivity.class);
				startActivity(i);
			}
		});
	}

	public void userLogin(View v) {
		Log.d(TAG, "Logging in user");
		email = (EditText) findViewById(R.id.login_email);
		password = (EditText) findViewById(R.id.login_password);

		// Save Creds in shared preferences

		SharedPreferences sp = getSharedPreferences("addressbook",
				Activity.MODE_PRIVATE);
		SharedPreferences.Editor editor = sp.edit();
		editor.putString("parentEmailId", email.getText().toString());
		editor.commit();

		new DynamoDBManagerTask().execute(DynamoDBManagerType.GET_USER);

	}

	private class DynamoDBManagerTask extends
			AsyncTask<DynamoDBManagerType, Void, DynamoDBManagerTaskResult> {

		protected DynamoDBManagerTaskResult doInBackground(
				DynamoDBManagerType... types) {

			DynamoDBManagerTaskResult result = new DynamoDBManagerTaskResult();

			result.setTaskType(types[0]);

			if (types[0] == DynamoDBManagerType.GET_USER) {

				user = DynamoDBManager.getUserByEmail(email.getText()
						.toString());

			}

			return result;
		}

		protected void onPostExecute(DynamoDBManagerTaskResult result) {

			if (result.getTaskType() == DynamoDBManagerType.GET_USER) {
				if (user.getPassword().equalsIgnoreCase(
						password.getText().toString())) {

					Toast.makeText(LoginActivity.this, "Login Successfull.",
							Toast.LENGTH_SHORT).show();
					redirectToActivity(null, HomeActivity.class);
				} else {

					Toast.makeText(
							LoginActivity.this,
							"Username or Password is incorrect. Please try again",
							Toast.LENGTH_SHORT).show();
					redirectToActivity(null, LoginActivity.class);
				}

			}
		}
	}

	public void redirectToActivity(View view, Class<?> cls) {
		Intent intent = new Intent(this, cls);
		startActivity(intent);
	}

	private enum DynamoDBManagerType {
		GET_USER
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
