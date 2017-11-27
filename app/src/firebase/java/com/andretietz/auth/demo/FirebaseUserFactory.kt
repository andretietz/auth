package com.andretietz.auth.demo

import com.andretietz.auth.demo.model.User
import com.andretietz.auth.firebase.UserFactory
import com.google.firebase.auth.FirebaseUser
import javax.inject.Inject


class FirebaseUserFactory @Inject constructor() : UserFactory<User> {
    override fun createUser(firebaseUser: FirebaseUser): User {
        return User(
                firebaseUser.uid,
                firebaseUser.email,
                firebaseUser.phoneNumber,
                firebaseUser.displayName ?: firebaseUser.email ?: "User",
                firebaseUser.photoUrl.toString()
        )
    }
}
