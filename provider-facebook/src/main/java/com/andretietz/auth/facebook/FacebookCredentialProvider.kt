package com.andretietz.auth.facebook

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import com.andretietz.auth.AndroidCredentialProvider
import com.andretietz.auth.AuthCredential
import com.andretietz.auth.credentials.FacebookCredential
import com.facebook.CallbackManager
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import io.reactivex.Maybe


class FacebookCredentialProvider(
        private val activity: AppCompatActivity,
        private vararg val scopes: String = arrayOf("public_profile", "email")
) : AndroidCredentialProvider {
    private val callbackManager = CallbackManager.Factory.create()
    private val loginManager = LoginManager.getInstance()

    override fun requestCredential(): Maybe<AuthCredential> {
        return Maybe.create<AuthCredential> { emitter ->
            loginManager.registerCallback(callbackManager, object : com.facebook.FacebookCallback<LoginResult> {
                override fun onError(error: FacebookException) {
                    if (emitter.isDisposed) return
                    emitter.onError(error)
                }

                override fun onCancel() {
                    if (emitter.isDisposed) return
                    emitter.onComplete()
                }

                override fun onSuccess(loginResult: LoginResult) {
                    if (emitter.isDisposed) return
                    emitter.onSuccess(FacebookCredential(loginResult.accessToken.token))
                }
            })
        }.doOnSubscribe {
            loginManager.logInWithReadPermissions(activity, scopes.toMutableList())
        }.doOnSuccess { loginManager.logOut() }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode != 0xface) return
        callbackManager.onActivityResult(requestCode, resultCode, data)
    }

    override fun type(): String = FacebookCredential.TYPE
}