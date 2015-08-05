package com.packtpub.dynamodb.cookbook;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class HomeActivity extends Activity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home);

	}

	public void onClickAddNewContact(View v) {
		redirectToActivity(v, ContactActivity.class);
	}

	public void onClickViewAll(View v) {
		redirectToActivity(v, ContactListActivity.class);
	}

	public void redirectToActivity(View view, Class<?> cls) {
		Intent intent = new Intent(this, cls);
		startActivity(intent);
	}
}
