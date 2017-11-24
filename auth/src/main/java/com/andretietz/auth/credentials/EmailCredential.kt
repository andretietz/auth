package com.andretietz.auth.credentials

import com.andretietz.auth.AuthCredential

data class EmailCredential(val email: String, val password: String) : AuthCredential {
    companion object {
        val TYPE = "email"
    }

    override fun type(): String = TYPE
}