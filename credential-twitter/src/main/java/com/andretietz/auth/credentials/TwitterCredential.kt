package com.andretietz.auth.credentials

import com.andretietz.auth.AuthCredential

class TwitterCredential constructor(
        val consumerKey: String,
        val consumerSecret: String,
        val userId: String,
        val userName: String,
        val token: String,
        val secret: String) : AuthCredential {
    companion object {
        const val TYPE = "twitter"
    }

    override fun type() = TYPE
}
