package com.andretietz.auth.demo

import com.andretietz.auth.back4app.UserFactory
import com.andretietz.auth.demo.model.User
import com.parse.ParseUser
import javax.inject.Inject


class Back4AppUserFactory @Inject constructor() : UserFactory<User> {
    override fun createUser(parseUser: ParseUser): User {
        return User(
                parseUser.objectId,
                parseUser.email,
                null,
                parseUser.username ?: parseUser.email ?: "User",
                null
        )
    }
}
