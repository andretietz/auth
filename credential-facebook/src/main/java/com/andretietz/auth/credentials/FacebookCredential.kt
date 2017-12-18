package com.andretietz.auth.credentials

import com.andretietz.auth.AuthCredential

data class FacebookCredential(val appId: String, val token: String) : AuthCredential {
    companion object {
        const val TYPE = "facebook"
    }

    override fun type() = TYPE
}
