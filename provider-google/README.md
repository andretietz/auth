# AndroidAuthProvider: Google
is an implementation of ```AndroidAuthProvider```. If you're using
multiple ```AndroidAuthProvider``` I recommend using the ```CompositeAndroidAuthProvider```
## 1. Setup
* Add the dependency
```gradle
implementation "com.andretietz.auth:provider-google:X.Y.Z"
```
* Create a google-firebase project (if you havn't already)
* Add an Android app to the project
* Enable Google Authentication (SHA-1 Fingerprint required!)
* Download the google-services.json and put it into the app's directory
* Add the google services plugin in your root gradle:
```gradle
classpath 'com.google.gms:google-services:3.1.2'
```
* Apply the plugin in the apps build.gradle on the bottom of the file
```gradle
apply plugin: 'com.google.gms.google-services'
```


## 2. Implementation
Create an instance
```kotlin
val googleProvider = GoogleAuthProvider(activity, "web-client-id") // R.string.default_web_client_id
```
and make sure you reach the onActivityResult call to the provider:
```kotlin
public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    super.onActivityResult(requestCode, resultCode, data)
    googleProvider.onActivityResult(requestCode, resultCode, data)
}
```
## Ready to go
```kotlin
googleProvider.authenticate()
    .subscribe{googleCredential -> ...}
```