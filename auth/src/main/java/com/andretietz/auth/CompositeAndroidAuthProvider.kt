package com.andretietz.auth

import android.content.Intent
import io.reactivex.Maybe
import io.reactivex.annotations.CheckReturnValue

class CompositeAndroidAuthProvider(private val providers: Map<String, AuthProvider>) {

    @CheckReturnValue
    fun authenticate(type: String): Maybe<AuthCredential> {
        providers[type]
                ?.let { return it.authenticate() }
        throw IllegalStateException("Provider $type not found!")
    }

    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        providers.forEach {
            if (it.value !is AndroidAuthProvider) return@forEach
            val provider = it.value as AndroidAuthProvider
            provider.onActivityResult(requestCode, resultCode, data)
        }
    }
}
