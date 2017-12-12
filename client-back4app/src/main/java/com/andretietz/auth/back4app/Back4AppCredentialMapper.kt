package com.andretietz.auth.back4app

import bolts.Task
import com.andretietz.auth.AuthCredential
import com.andretietz.auth.credentials.TwitterCredential
import com.parse.ParseUser

internal class Back4AppCredentialMapper {
    companion object {
        @JvmStatic
        fun map(credential: AuthCredential): Task<ParseUser> {
            when (credential.type()) {
                TwitterCredential.TYPE -> {
                    credential as TwitterCredential
                    ParseUser.logInWithInBackground("twitter",
                            mutableMapOf<String, String>(
//                                    "consumer_key" to credential.consumerKey,
//                                    "consumer_secret" to credential.consumerSecret,
//                                    "id" to credential.userId,
//                                    "screen_name" to credential.userName,
                                    "auth_token" to credential.token,
                                    "auth_token_secret" to credential.secret
                            ))
                }
            }
            throw IllegalStateException("Unsupported credential type: ${credential.type()}")
        }
    }
}