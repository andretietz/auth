package com.andretietz.auth

interface ResultMapper<in CLIENT_RESULT, out RESULT> {
    fun map(clientResult: CLIENT_RESULT): RESULT
}