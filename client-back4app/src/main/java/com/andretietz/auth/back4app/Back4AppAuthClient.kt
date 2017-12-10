package com.andretietz.auth.back4app

import android.content.Context
import com.andretietz.auth.AuthClient
import com.andretietz.auth.AuthCredential
import com.parse.Parse
import com.parse.ParseInstallation
import com.parse.ParseUser
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.Single

class Back4AppAuthClient<RESULT>(
        context: Context,
        private val userFactory: UserFactory<RESULT>,
        applicationId: String,
        clientKey: String,
        serverUrl: String = "https://parseapi.back4app.com/"
) : AuthClient<RESULT> {

    init {
        val config = Parse.Configuration.Builder(context)
                .applicationId(applicationId)
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
                emitter.onSuccess(userFactory.createUser(user))
            } else {
                emitter.onComplete()
            }
        }
    }

    override fun signInState(): Observable<AuthClient.State<RESULT>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun signUp(credential: AuthCredential): Single<RESULT> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun signIn(credential: AuthCredential): Single<RESULT> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun signOut(): Single<RESULT> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}