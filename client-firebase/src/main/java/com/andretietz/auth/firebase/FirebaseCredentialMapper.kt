package com.andretietz.auth.firebase

import com.andretietz.auth.credentials.EmailCredential
import com.andretietz.auth.credentials.FacebookCredential
import com.andretietz.auth.credentials.GoogleCredential
import com.andretietz.auth.credentials.TwitterCredential
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.TwitterAuthProvider

internal class FirebaseCredentialMapper {
    fun map(credential: com.andretietz.auth.AuthCredential): AuthCredential =
        when (credential.type()) {
            EmailCredential.TYPE -> {
                credential as EmailCredential
                EmailAuthProvider.getCredential(credential.email, credential.password)
            }
            GoogleCredential.TYPE -> {
                credential as GoogleCredential
                GoogleAuthProvider.getCredential(credential.idToken, credential.token)
            }
            FacebookCredential.TYPE -> {
                credential as FacebookCredential
                FacebookAuthProvider.getCredential(credential.token)
            }
            TwitterCredential.TYPE -> {
                credential as TwitterCredential
                TwitterAuthProvider.getCredential(credential.token, credential.secret)
            }
            else -> throw IllegalStateException("Unsupported credential type: ${credential.type()}")
        }
}
