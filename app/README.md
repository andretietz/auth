# The Demo Application

For any public available client implementations there should be a
flavor.

## Flavor Firebase
In order to make the demo application run for you, you have to:
* Setup [provider-google](../provider-google) or [remove it from the module](src/main/java/com/andretietz/auth/demo/injection/AuthProviderModule.kt)
* Setup [provider-facebook](../provider-facebook) or [remove it from the module](src/main/java/com/andretietz/auth/demo/injection/AuthProviderModule.kt)
* Setup [provider-twitter](../provider-twitter) or [remove it from the module](src/main/java/com/andretietz/auth/demo/injection/AuthProviderModule.kt)
* Prepare a [firebase project](https://console.firebase.google.com) where you enable email authentication and
any other provider you set up