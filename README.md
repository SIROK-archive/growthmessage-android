# Growth Message SDK for Android

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
	    android:name="com.growthbeat.message.view.AlertActivity"
        android:theme="@android:style/Theme.Translucent" />
    ````

1. (Optional) Track event to receive a message such as following code.

	```java
	GrowthAnalytics.getInstance().open();
	```

## Growthbeat Full SDK

You can use Growthbeat SDK instead of this SDK. Growthbeat is growth hack tool for mobile apps. You can use full functions include Growth Message when you use the SDK.

* [Growthbeat SDK for iOS](https://github.com/SIROK/growthbeat-ios/)
* [Growthbeat SDK for Android](https://github.com/SIROK/growthbeat-android/)

## License

Apache License, Version 2.0
