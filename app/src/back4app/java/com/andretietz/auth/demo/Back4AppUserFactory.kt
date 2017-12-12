package com.andretietz.auth.demo

import com.andretietz.auth.ResultMapper
import com.andretietz.auth.demo.model.User
import com.parse.ParseUser
import javax.inject.Inject


class Back4AppUserFactory @Inject constructor() : ResultMapper<ParseUser, User> {
    override fun map(clientResult: ParseUser): User {
        return User(
                clientResult.objectId,
                clientResult.email,
                null,
                clientResult.username ?: clientResult.email ?: "User",
                null
        )
    }
}
