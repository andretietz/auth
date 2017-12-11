package com.andretietz.auth

import android.content.Intent

interface AndroidCredentialProvider : CredentialProvider {
    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?)
}