package com.andretietz.auth.firebase

import com.google.firebase.auth.FirebaseUser

interface UserFactory<out T> {
    fun createUser(firebaseUser: FirebaseUser): T
}