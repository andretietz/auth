# AndroidCredentialProvider: Email
is an implementation of ```AndroidCredentialProvider```. If you're using
multiple ```AndroidCredentialProvider``` I recommend using the ```CompositeAndroidCredentialProvider```

## 1. Setup
* Add dependency
```gradle
implementation 'com.andretietz.auth:provider-email:X.Y.Z'
```

## 2. Implementation
Create an instance
```kotlin
val emailProvider = EmailCredentialProvider(activity)
```
and make sure you reach the onActivityResult call to the provider:
```kotlin
public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    super.onActivityResult(requestCode, resultCode, data)
    emailProvider.onActivityResult(requestCode, resultCode, data)
}
```
## Ready to go
```kotlin
emailProvider.requestCredential()
    .subscribe{emailCredential -> ...}
```