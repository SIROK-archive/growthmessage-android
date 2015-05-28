package com.growthbeat.message.view;

import java.util.HashMap;
import java.util.Map;

import com.growthbeat.message.model.BannerMessage;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.util.Log;
import android.view.Gravity;
import android.view.WindowManager;
import android.widget.FrameLayout;

public class BannerMessageView extends FrameLayout{
	
	private BannerMessage bannerMessage = null;
	
	Map<String, Bitmap> cachedImages = new HashMap<String, Bitmap>();

	public BannerMessageView(Context context, Object message) {
		super(context);
		
		if (message == null || !(message instanceof BannerMessage))
			return;

		this.bannerMessage = (BannerMessage) message;
		
		Log.w("", "############################");
		Log.w("", "" + bannerMessage.getButtons().size());
		Log.w("", "" + bannerMessage.getType());
		Log.w("", "" + bannerMessage.getBannerType());
		Log.w("", "" + bannerMessage.getCaption());
		Log.w("", "" + bannerMessage.getText());
		Log.w("", "" + bannerMessage.getPosition());
		Log.w("", "" + bannerMessage.getDuration());
		Log.w("", "" + bannerMessage.getPicture().getName());
		Log.w("", "############################");
		
		FrameLayout ve = new FrameLayout(context);
		ve.setBackgroundColor(Color.argb(128, 12, 52, 0));
		addView(ve);
		
		WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
		layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
		layoutParams.height = 100;
		layoutParams.gravity = Gravity.BOTTOM;
		layoutParams.type = WindowManager.LayoutParams.TYPE_SYSTEM_ERROR;
		layoutParams.flags = WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
				| WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL;
		layoutParams.format = PixelFormat.TRANSLUCENT;
		
		getWindowsManager().addView(this, layoutParams);
	}
	
	public WindowManager getWindowsManager() {
		return (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
	}

}
