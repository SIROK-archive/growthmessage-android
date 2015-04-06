package com.growthbeat.message.view;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.FragmentActivity;
import android.view.Window;
import android.view.WindowManager;

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

		getWindow().clearFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);

		super.onDestroy();

	}

}
