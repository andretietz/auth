package com.andretietz.auth

import io.reactivex.Maybe
import io.reactivex.annotations.CheckReturnValue

interface CredentialProvider {
    /**
     * @return a name for this auth provider. i.e. google or facebook
     */
    fun type(): String

    /**
     * @return a stream that starts authenticating when someone subscribes to it. when successful
     * it emits an {@link AuthCredential}, when it was canceled it will complete
     */
    @CheckReturnValue
    fun authenticate(): Maybe<AuthCredential>
}
