package com.andretietz.auth

import io.reactivex.Maybe
import io.reactivex.Single

interface AuthClient<RESULT> {
    fun isSignedIn(): Maybe<RESULT>
    fun signUp(credential: AuthCredential): Single<RESULT>
    fun signIn(credential: AuthCredential): Single<RESULT>
    fun signOut(): Single<RESULT>
}