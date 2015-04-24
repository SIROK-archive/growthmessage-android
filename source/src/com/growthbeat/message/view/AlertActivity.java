package com.growthbeat.message.view;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.FragmentActivity;
import android.view.Window;

import com.growthbeat.message.model.Message;

public class AlertActivity extends FragmentActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setTheme(android.R.style.Theme_Translucent);

		Message message = (Message) getIntent().getExtras().get("message");
		Bundle bundle = new Bundle();
		bundle.putParcelable("message", (Parcelable) message);

		switch (message.getType()) {
		case plain:
			AlertFragment alertFragment = new AlertFragment();
			alertFragment.setCancelable(false);
			alertFragment.setArguments(bundle);
			alertFragment.show(getSupportFragmentManager(), getClass().getName());
			break;

		case image:
			ImageFragment imageFragment = new ImageFragment();
			imageFragment.setArguments(bundle);
			getSupportFragmentManager().beginTransaction().replace(android.R.id.content, imageFragment).commitAllowingStateLoss();
			break;
			
		default:
			break;
		}

	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

}
