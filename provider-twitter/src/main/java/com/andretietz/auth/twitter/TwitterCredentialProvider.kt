package com.andretietz.auth.twitter

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import com.andretietz.auth.AndroidCredentialProvider
import com.andretietz.auth.AuthCredential
import com.andretietz.auth.credentials.TwitterCredential
import com.twitter.sdk.android.core.Callback
import com.twitter.sdk.android.core.Result
import com.twitter.sdk.android.core.Twitter
import com.twitter.sdk.android.core.TwitterAuthConfig
import com.twitter.sdk.android.core.TwitterAuthException
import com.twitter.sdk.android.core.TwitterConfig
import com.twitter.sdk.android.core.TwitterCore
import com.twitter.sdk.android.core.TwitterException
import com.twitter.sdk.android.core.TwitterSession
import com.twitter.sdk.android.core.identity.TwitterAuthClient
import io.reactivex.Maybe

class TwitterCredentialProvider(
        private val activity: AppCompatActivity,
        private val consumerKey: String,
        private val consumerSecret: String
) : AndroidCredentialProvider {

    private val client: TwitterAuthClient

    init {
        val config = TwitterConfig.Builder(activity)
                .twitterAuthConfig(TwitterAuthConfig(consumerKey, consumerSecret))
                .build()
        Twitter.initialize(config)
        client = TwitterAuthClient()
    }

    override fun requestCredential(): Maybe<AuthCredential> {
        return Maybe.create<AuthCredential> { emitter ->
            client.authorize(activity, object : Callback<TwitterSession>() {
                override fun success(result: Result<TwitterSession>) {
                    emitter.onSuccess(TwitterCredential(
                            consumerKey,
                            consumerSecret,
                            result.data.userName,
                            result.data.userId.toString(),
                            result.data.authToken.token,
                            result.data.authToken.secret
                    ))
                }

                override fun failure(exception: TwitterException?) {
                    if (exception != null) {
                        if (exception is TwitterAuthException
                                && exception.message != null
                                && ("Authorization failed, request was canceled." == exception.message
                                || "Failed to get authorization, bundle incomplete" == exception.message)) {
                            emitter.onComplete()
                            return
                        } else {
                            emitter.onError(exception)
                        }
                    } else {
                        emitter.onError(IllegalStateException("Unknown twitter error!"))
                    }
                }
            })
        }.doOnSuccess {
            TwitterCore
                    .getInstance()
                    .sessionManager
                    .clearActiveSession()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode != client.requestCode) return
        client.onActivityResult(requestCode, resultCode, data)
    }

    override fun type(): String = TwitterCredential.TYPE
}
