# The Back4App Client implementation
```Back4AppAuthClient implements AuthClient``` which enables you easily
to change the implementation. So if you're not sure what to use yet and
want to get started with back4app, this is a good thing todo.
At the moment it supports:
* Email/Password Login
* Facebook Login
* Twitter Login

## 1. Setup
* Add the dependency
```gradle
implementation 'com.andretietz.auth:client-back4app:X.Y.Z'
```
* Create a back4app project using the [back4app dashboard](https://dashboard.back4app.com/).
* Go to the Server Settings page of your app, on the dashboard
* Go to the Core Settings and note down the App Id and the Client secret

## 2. Create a UserFactory
In order to use this client implementation you need to implement the
```ResultMapper``` interface. This interface provides in one method
the ParseUser object as soon as available and will return any type
of user you need in your app.
i.e.:
```Kotlin
class YourUserMapperImplementation @Inject constructor() : ResultMapper<ParseUser, User> {
    override fun map(clientResult: ParseUser): User {
        return User(
                clientResult.objectId,
                clientResult.email,
                null,
                clientResult.username ?: clientResult.email ?: "User",
                null
        )
    }
}
```

## 3. Create an instance

```kotlin
val userMapper = YourUserMapperImplementation()
val authClient = Back4AppAuthClient(context, userMapper, appId, clientSecret [, serverUrl])
```

## Ready to go

```
authClient.signUp(facebookCredentials)
    .subscribe{ userOfMyUserType -> updateUI(userOfMyUserType)}
```
