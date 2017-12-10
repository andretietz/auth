package com.andretietz.auth

import android.content.Intent
import io.reactivex.Maybe
import io.reactivex.annotations.CheckReturnValue

class CompositeAndroidCredentialProvider(val providers: Map<String, CredentialProvider>) {

    @CheckReturnValue
    fun authenticate(type: String): Maybe<AuthCredential> {
        providers[type]
                ?.let { return it.requestCredential() }
        throw IllegalStateException("Provider $type not found!")
    }

    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        providers.values
            .filterIsInstance<AndroidCredentialProvider>()
            .forEach { it.onActivityResult(requestCode, resultCode, data) }
    }
}
