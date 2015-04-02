# Growth Message SDK for Android

[Growth Message](https://message.growthbeat.com/) is message service for mobile apps.

## Usage 

1. Install [Growthbeat Core SDK](https://github.com/SIROK/growthbeat-core-android).

1. Add growthmessage.jar into libs directory in your project.

1. Write initialization code in MainActivity's onCreate.

	```java
	GrowthMessage.getInstance().initialize(getApplicationContext(), "APPLICATION_ID", "CREDENTIAL_ID");
	```

1. Write following code in MainActivity's onStart

	```java
	GrowthMessage.getInstance().openMessageIfAvailable();
	```

1. Add Permission code in AndroidManifest.xml

	```xml
	<uses-permission android:name="android.permission.INTERNET" >
	```

## Growthbeat Full SDK

You can use Growthbeat SDK instead of this SDK. Growthbeat is growth hack tool for mobile apps. You can use full functions include Growth Push when you use the following SDK.

* [Growthbeat SDK for iOS](https://github.com/SIROK/growthbeat-ios/)
* [Growthbeat SDK for Android](https://github.com/SIROK/growthbeat-android/)

## License

Apache License, Version 2.0
