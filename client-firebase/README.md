# The Firebase Client implementation
```FirebaseAuthClient implements AuthClient``` which enables you easily
to change the implementation. So if you're not sure what to use yet and
want to get started with firebase, this is a good thing todo.
At the moment it supports:
* Email/Password Login
* Google Login
* Facebook Login
* Twitter Login

## 1. Setup
* Add the dependency
```gradle
implementation 'com.andretietz.auth:client-firebase:X.Y.Z'
```
* Create a firebase project using the [firebase console](https://console.firebase.google.com).
* Add an Android application to the project
* Enable the auth-providers you need in the console.
* Download the google-services.json put it into your app directory [(see documentation)](https://developers.google.com/android/guides/google-services-plugin)
* Add the google services plugin in your root gradle:
```gradle
classpath 'com.google.gms:google-services:3.1.2'
```
* Apply the plugin in the apps build.gradle on the bottom of the file
```gradle
apply plugin: 'com.google.gms.google-services'
```

## 2. Create a UserFactory
In order to use this client implementation you need to implement the
```ResultMapper``` interface. This interface provides in one method
the FirebaseUser object as soon as available and will return any type
of user you need in your app.
i.e.:
class YourUserMapperImplementation : ResultMapper<FirebaseUser, User> {
    override fun map(firebaseUser: FirebaseUser): User {
        return User(
                firebaseUser.uid,
                firebaseUser.email,
                firebaseUser.phoneNumber,
                firebaseUser.displayName ?: firebaseUser.email ?: "User",
                firebaseUser.photoUrl.toString()
        )
    }
}
```

## 3. Create an instance

```kotlin
val userMapper = YourUserMapperImplementation()
val authClient = FirebaseAuthClient(userMapper)
```

## Ready to go

```
authClient.signUp(facebookCredentials)
    .subscribe{ userOfMyUserType -> updateUI(userOfMyUserType)}
```
