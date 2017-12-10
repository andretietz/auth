package com.andretietz.auth

import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.Single

interface AuthClient<RESULT> {
    fun getUser(): Maybe<RESULT>
    fun signInState(): Observable<State<RESULT>>
    fun signUp(credential: AuthCredential): Single<RESULT>
    fun signIn(credential: AuthCredential): Single<RESULT>
    fun signOut(): Single<RESULT>

    data class State<out RESULT>(val user: RESULT?)
}