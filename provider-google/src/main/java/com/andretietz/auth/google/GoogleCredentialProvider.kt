package com.andretietz.auth.google

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import com.andretietz.auth.AndroidCredentialProvider
import com.andretietz.auth.AuthCredential
import com.andretietz.auth.credentials.GoogleCredential
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import com.google.android.gms.common.Scopes
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.Scope
import io.reactivex.Maybe
import io.reactivex.MaybeEmitter

class GoogleCredentialProvider constructor(private val activity: AppCompatActivity, webClientId: String)
    : AndroidCredentialProvider {

    companion object {
        const val GOOGLE_LOGIN_REQUEST_CODE = 0xC001
        const val GOOGLE_ERROR_REQUEST_CODE = 0xC002
    }

    private val signInClient: GoogleSignInClient
    private var resultEmitter: MaybeEmitter<AuthCredential>? = null

    init {
        val options = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(webClientId)
                .requestScopes(Scope(Scopes.EMAIL))
                .build()

        signInClient = GoogleSignIn.getClient(activity, options)

        val serviceAvailability = GoogleApiAvailability.getInstance()
        val result = serviceAvailability.isGooglePlayServicesAvailable(activity)
        if (result != ConnectionResult.SUCCESS) {
            serviceAvailability.getErrorDialog(activity, result, GOOGLE_ERROR_REQUEST_CODE).show()
        }
    }

    override fun requestCredential(): Maybe<AuthCredential> {
        return Maybe.create<AuthCredential> { emitter ->
            resultEmitter = emitter
        }.doOnSubscribe {
            activity.startActivityForResult(signInClient.signInIntent, GOOGLE_LOGIN_REQUEST_CODE)
        }.doOnSuccess {
            signInClient.signOut()
            signInClient.revokeAccess()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (GOOGLE_LOGIN_REQUEST_CODE != requestCode) return

        val task = GoogleSignIn.getSignedInAccountFromIntent(data)
        resultEmitter?.let {
            if (it.isDisposed) return@let
            try {
                val account = task.getResult(ApiException::class.java)
                it.onSuccess(GoogleCredential(account.idToken, null))
            } catch (error: ApiException) {
                it.onError(error)
            }
        }
        resultEmitter = null
    }

    override fun type(): String = GoogleCredential.TYPE
}