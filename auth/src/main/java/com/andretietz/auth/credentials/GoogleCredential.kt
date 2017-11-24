package com.andretietz.auth.credentials

import com.andretietz.auth.AuthCredential

data class GoogleCredential(val idToken: String?, val token: String?) : AuthCredential {
    companion object {
        const val TYPE = "google"
    }

    override fun type(): String = TYPE
}