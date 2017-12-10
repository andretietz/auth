package com.andretietz.auth.demo.injection

import android.app.Application
import com.andretietz.auth.AuthClient
import com.andretietz.auth.back4app.Back4AppAuthClient
import com.andretietz.auth.demo.Back4AppUserFactory
import com.andretietz.auth.demo.R
import com.andretietz.auth.demo.injection.scopes.ApplicationScope
import com.andretietz.auth.demo.model.User
import dagger.Module
import dagger.Provides

@Module
class AuthModule {
    @Provides
    @ApplicationScope
    fun provideAuthClient(application: Application,
                          factory: Back4AppUserFactory
    ): AuthClient<User> {
        return Back4AppAuthClient(
                application,
                factory,
                application.getString(R.string.back4app_application_id),
                application.getString(R.string.back4app_client_key)
        )
    }
}
