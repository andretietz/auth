package com.andretietz.auth.back4app

import android.content.Context
import com.andretietz.auth.AuthClient
import com.andretietz.auth.AuthCredential
import com.andretietz.auth.ResultMapper
import com.andretietz.auth.credentials.EmailCredential
import com.parse.Parse
import com.parse.ParseInstallation
import com.parse.ParseUser
import io.reactivex.Maybe
import io.reactivex.Single

class Back4AppAuthClient<RESULT>(
        context: Context,
        private val resultMapper: ResultMapper<ParseUser, RESULT>,
        appId: String,
        clientKey: String,
        serverUrl: String = "https://parseapi.back4app.com/"
) : AuthClient<RESULT> {

    init {
        val config = Parse.Configuration.Builder(context)
                .applicationId(appId)
                .clientKey(clientKey)
                .server(serverUrl)
                .build()
        Parse.initialize(config)
        ParseInstallation.getCurrentInstallation().saveInBackground()
    }

    override fun getUser(): Maybe<RESULT> {
        return Maybe.create { emitter ->
            val user = ParseUser.getCurrentUser()
            if (user != null) {
                emitter.onSuccess(resultMapper.map(user))
            } else {
                emitter.onComplete()
            }
        }
    }

    override fun signUp(credential: AuthCredential): Single<RESULT> {
        if (credential is EmailCredential) {
            return Single.create { emitter ->
                val user = ParseUser()
                user.username = credential.email
                user.email = credential.email
                user.setPassword(credential.password)
                user.signUpInBackground { error ->
                    if (error != null)
                        emitter.onError(error)
                    ParseUser.logInInBackground(credential.email, credential.password, { user, error ->
                        if (user != null) {
                            emitter.onSuccess(resultMapper.map(user))
                        } else {
                            emitter.onError(error)
                        }
                    })
                }
                return@create
            }
        }
        return Single.error(IllegalStateException("Unsupported credential type: ${credential.type()}"))


    }

    override fun signIn(credential: AuthCredential): Single<RESULT> {
        return Single.create { emitter ->
            if (credential is EmailCredential) {
                ParseUser.logInInBackground(credential.email, credential.password, { user, error ->
                    if (user != null) {
                        emitter.onSuccess(resultMapper.map(user))
                    } else {
                        emitter.onError(error)
                    }
                })
                return@create
            } else {
                emitter.onSuccess(resultMapper.map(Back4AppCredentialMapper.map(credential).result))
            }
        }
    }

    override fun signOut(): Single<RESULT> {
        val currentUser = ParseUser.getCurrentUser()
                ?: return Single.error(IllegalStateException("User is not signed in!"))
        return Single.create { emitter ->
            ParseUser.logOutInBackground { error ->
                if (error != null) emitter.onError(error)
                emitter.onSuccess(resultMapper.map(currentUser))
            }
        }
    }
}