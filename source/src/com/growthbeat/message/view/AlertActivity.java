package com.growthbeat.message.view;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.FragmentActivity;
import android.view.Window;

public class AlertActivity extends FragmentActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setTheme(android.R.style.Theme_Translucent);

		final AlertFragment fragment = new AlertFragment();
		fragment.setCancelable(false);

		Bundle bundle = new Bundle();
		bundle.putParcelable("message", (Parcelable) getIntent().getExtras().get("message"));
		fragment.setArguments(bundle);

		fragment.show(getSupportFragmentManager(), getClass().getName());

	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

}
