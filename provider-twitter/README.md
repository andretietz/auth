# AndroidCredentialProvider: Twitter
is an implementation of ```AndroidCredentialProvider```. If you're using
multiple ```AndroidCredentialProvider``` I recommend using the ```CompositeAndroidCredentialProvider```

## 1. Setup
* Add the dependency:
```gradle
implementation "com.andretietz.auth:provicer-twitter:X.Y.Z"
```
* [Create a Twitter Application](https://apps.twitter.com/)
* Make sure it's read only
* Note the API Key and the API Secret

## 2. Implementation
Create an instance
```kotlin
val twitterProvider = TwitterCredentialProvider(activity, "YOUR-API-KEY", "YOUR-API-SECRET")
```
and make sure you reach the onActivityResult call to the provider:
```kotlin
public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    super.onActivityResult(requestCode, resultCode, data)
    twitterProvider.onActivityResult(requestCode, resultCode, data)
}
```
## Ready to go
```kotlin
twitterProvider.authenticate()
    .subscribe{twitterCredential -> ...}
```