package com.andretietz.auth.back4app

import com.parse.ParseUser

interface UserFactory<out T> {
    fun createUser(parseUser: ParseUser): T
}