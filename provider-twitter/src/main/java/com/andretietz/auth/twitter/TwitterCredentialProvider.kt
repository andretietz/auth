package com.andretietz.auth.twitter

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import com.andretietz.auth.AndroidCredentialProvider
import com.andretietz.auth.AuthCredential
import com.andretietz.auth.credentials.TwitterCredential
import com.twitter.sdk.android.core.*
import com.twitter.sdk.android.core.identity.TwitterAuthClient
import io.reactivex.Maybe
import io.reactivex.MaybeEmitter


class TwitterCredentialProvider(private val activity: AppCompatActivity, consumerKey: String, consumerSecret: String)
    : AndroidCredentialProvider {

    private val client: TwitterAuthClient

    private var resultEmitter: MaybeEmitter<AuthCredential>? = null

    init {
        val config = TwitterConfig.Builder(activity)
                .twitterAuthConfig(TwitterAuthConfig(consumerKey, consumerSecret))
                .build()
        Twitter.initialize(config)
        client = TwitterAuthClient()
    }

    override fun authenticate(): Maybe<AuthCredential> {
        return Maybe.create<AuthCredential> { emitter -> resultEmitter = emitter }
                .doOnSubscribe {
                    client.authorize(activity, object : Callback<TwitterSession>() {
                        override fun success(result: Result<TwitterSession>) {
                            resultEmitter?.let {
                                if (it.isDisposed) return
                                it.onSuccess(TwitterCredential(result.data.authToken.token, result.data.authToken.secret))
                            }
                        }

                        override fun failure(exception: TwitterException?) {
                            resultEmitter?.let {
                                if (it.isDisposed) return
                                if (exception != null) {
                                    if (exception is TwitterAuthException
                                            && exception.message != null
                                            && ("Authorization failed, request was canceled." == exception.message
                                            || "Failed to get authorization, bundle incomplete" == exception.message)) {
                                        it.onComplete()
                                        return
                                    }
                                    it.onError(exception)
                                } else {
                                    it.onError(IllegalStateException("Unknown twitter error!"))
                                }
                            }
                        }
                    })
                }
                .doOnSuccess {
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