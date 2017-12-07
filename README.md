[![Build Status](https://www.bitrise.io/app/a13b8d5935fe00b5.svg?token=eOZazuCAGkztMmE_7KT2yA&branch=master)](https://www.bitrise.io/app/a13b8d5935fe00b5)

# Authentication World - Android
I am sick of writing the same authentication stuff over and over again,
so I created a library that I can re-use for all different kinds of
 authentications.
This is a library written in kotlin.

## 1. A common abstract authentication layer - auth
This part of the library is basically a set of interfaces with which you can start implementing your
app, without the need to care which  providers (email, oauthX providers, ...) you will use later on
or which type of backend you are using later on.

The main interfaces are:
* ```CredentialProvider``` or ```AndroidCredentialProvider``` which represents any kind of authentication using
 a third party service such as Google, Facebook or similar. This Providers should be able to provide
 you an ```AuthCredential``` which you either use in app only or you authenticate with it against
 some backend using the ```AuthClient``` implementations.
* ```AuthClient<T>``` represents any type of authentication server you can use, while ```T``` is the
type of user object you're using in your app, which can also be of type `Void`.

So the idea was regardless of which authentication-provider you will use or to which kind of
server/service you will authenticate to, you can use these interfaces.

Go [here](auth/) for details or checkout the [demo-application](app/) or [download and
install the Demo-Application](app/demo.apk)

## 2. Ready-to-use Provider implementations
These implementations are existing right now
 * [provider-email - Provides Email credentials sign in](provider-email/)
 * [provider-google - Google Sign In](provider-google/)
 * [provider-facebook - Facebook Sign In](provider-facebook/)
 * [provider-twitter - Twitter Sign In](provider-twitter/)

## 3. Ready-to-use Client implementations
At the moment there's only one implementation
* [client-firebase](client-firebase/)

## License
    Copyright 2017 André Tietz

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.

    