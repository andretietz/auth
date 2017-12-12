package com.andretietz.auth.demo

import com.andretietz.auth.ResultMapper
import com.andretietz.auth.demo.model.User
import com.google.firebase.auth.FirebaseUser
import javax.inject.Inject


class FirebaseUserMapper @Inject constructor() : ResultMapper<FirebaseUser, User> {
    override fun map(firebaseUser: FirebaseUser): User {
        return User(
                firebaseUser.uid,
                firebaseUser.email,
                firebaseUser.phoneNumber,
                firebaseUser.displayName ?: firebaseUser.email ?: "User",
                firebaseUser.photoUrl.toString()
        )
    }
}
