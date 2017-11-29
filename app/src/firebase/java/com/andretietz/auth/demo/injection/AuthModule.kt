package com.andretietz.auth.demo.injection

import com.andretietz.auth.AuthClient
import com.andretietz.auth.demo.FirebaseUserFactory
import com.andretietz.auth.demo.injection.scopes.ApplicationScope
import com.andretietz.auth.demo.model.User
import com.andretietz.auth.firebase.FirebaseAuthClient
import dagger.Module
import dagger.Provides

@Module
class AuthModule {
    @Provides
    @ApplicationScope
    fun provideAuthClient(factory: FirebaseUserFactory): AuthClient<User> = FirebaseAuthClient(factory)
}
