package com.andretietz.auth.demo.injection

import android.support.v7.app.AppCompatActivity
import com.andretietz.auth.AuthProvider
import com.andretietz.auth.CompositeAndroidAuthProvider
import com.andretietz.auth.credentials.FacebookCredential
import com.andretietz.auth.credentials.GoogleCredential
import com.andretietz.auth.credentials.TwitterCredential
import com.andretietz.auth.demo.R
import com.andretietz.auth.demo.injection.scopes.ActivityScope
import com.andretietz.auth.facebook.FacebookAuthProvider
import com.andretietz.auth.google.GoogleAuthProvider
import com.andretietz.auth.twitter.TwitterAuthProvider
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import dagger.multibindings.StringKey

@Module
class AuthProviderModule {

    @Provides
    @ActivityScope
    fun provideCompositeProvider(providers: Map<String, @JvmSuppressWildcards AuthProvider>)
            : CompositeAndroidAuthProvider = CompositeAndroidAuthProvider(providers)

    @Provides
    @IntoMap
    @ActivityScope
    @StringKey(GoogleCredential.TYPE)
    fun provideGoogleProvider(activity: AppCompatActivity): AuthProvider =
            GoogleAuthProvider(activity, activity.getString(R.string.default_web_client_id))

    @Provides
    @IntoMap
    @ActivityScope
    @StringKey(FacebookCredential.TYPE)
    fun provideFacebookProvider(activity: AppCompatActivity): AuthProvider = FacebookAuthProvider(activity)

    @Provides
    @IntoMap
    @ActivityScope
    @StringKey(TwitterCredential.TYPE)
    fun provideTwitterProvider(activity: AppCompatActivity): AuthProvider {
        return TwitterAuthProvider(activity, activity.getString(R.string.twitter_app_key),
                activity.getString(R.string.twitter_app_secret))
    }
}