package com.andretietz.auth

import android.content.Intent

interface AndroidAuthProvider : AuthProvider {
    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?)
}