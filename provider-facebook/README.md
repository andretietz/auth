# AndroidAuthProvider: Facebook
is an implementation of ```AndroidAuthProvider```. If you're using
multiple ```AndroidAuthProvider``` I recommend using the ```CompositeAndroidCredentialProvider```
## 1. Setup
* Add the dependency
```gradle
implementation 'com.andretietz.auth:provider-facebook:X.Y.Z'
```
* Go [here](https://developers.facebook.com/apps/) and create the
facebook application
* Click on Settings and click "Add Platform" and add the android
application using the ApplicationId of your app
* Note the Facebook AppId and add it into the string.xml of your project:
```xml
<string name="facebook_app_id">[YOUR-APP-ID]</string>
<string name="fb_login_protocol_scheme">fb[YOUR-APP-ID]</string>
```
* Extend the Manifest file of your project:
```xml
<meta-data android:name="com.facebook.sdk.ApplicationId"
        android:value="@string/facebook_app_id"/>

    <activity android:name="com.facebook.FacebookActivity"
        android:configChanges=
                "keyboard|keyboardHidden|screenLayout|screenSize|orientation"
        android:label="@string/app_name" />
    <activity
        android:name="com.facebook.CustomTabActivity"
        android:exported="true">
        <intent-filter>
            <action android:name="android.intent.action.VIEW" />
            <category android:name="android.intent.category.DEFAULT" />
            <category android:name="android.intent.category.BROWSABLE" />
            <data android:scheme="@string/fb_login_protocol_scheme" />
        </intent-filter>
    </activity>
```
## 2. Implementation
Create an instance
```kotlin
val facebookProvider = FacebookAuthProvider(activity)
```
and make sure you reach the onActivityResult call to the provider:
```kotlin
public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    super.onActivityResult(requestCode, resultCode, data)
    facebookProvider.onActivityResult(requestCode, resultCode, data)
}
```
## Ready to go
```kotlin
facebookProvider.authenticate()
    .subscribe{facebookCredential -> ...}
```