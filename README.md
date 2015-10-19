# Growth Message SDK for Android

:rotating_light: **This SDK is no longer maintained** :rotating_light:  
You can use Growthbeat sdk insead:
* [Growthbeat SDK for iOS](https://github.com/SIROK/growthbeat-ios/)
* [Growthbeat SDK for Android](https://github.com/SIROK/growthbeat-android/)

---

[Growth Message](https://message.growthbeat.com/) is in-app message service for mobile apps.

## Usage 

1. Install [Growthbeat Core SDK](https://github.com/SIROK/growthbeat-core-android).

1. Install [Growthbeat Analytics SDK](https://github.com/SIROK/growthanalytics-android).

1. Add growthmessage.jar into libs directory in your project.

1. Write initialization code in MainActivity's onCreate.

	```java
	GrowthMessage.getInstance().initialize(getApplicationContext(), "APPLICATION_ID", "CREDENTIAL_ID");
	```

1. Add Permission code in AndroidManifest.xml

	```xml
	<uses-permission android:name="android.permission.INTERNET" >
	```

1. Add following code as a element of `<application/>` in AndroidManifest.xml

	```xml
	<activity
	    android:name="com.growthbeat.message.view.MessageActivity"
	    android:theme="@android:style/Theme.Translucent" />
	````

1. Write following code in the place to display a message.

	```java
	GrowthAnalytics.getInstance().track("EVENT_ID");
	```

## Growthbeat Full SDK

You can use Growthbeat SDK instead of this SDK. Growthbeat is growth hack tool for mobile apps. You can use full functions include Growth Message when you use the SDK.

* [Growthbeat SDK for iOS](https://github.com/SIROK/growthbeat-ios/)
* [Growthbeat SDK for Android](https://github.com/SIROK/growthbeat-android/)

## License

Apache License, Version 2.0
