package com.andretietz.auth.demo.injection

import android.support.v7.app.AppCompatActivity
import com.andretietz.auth.CompositeAndroidCredentialProvider
import com.andretietz.auth.CredentialProvider
import com.andretietz.auth.credentials.EmailCredential
import com.andretietz.auth.demo.injection.scopes.ActivityScope
import com.andretietz.auth.email.EmailCredentialProvider
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import dagger.multibindings.StringKey

@Module
class AuthProviderModule {

    @Provides
    @ActivityScope
    fun provideCompositeProvider(providers: Map<String, @JvmSuppressWildcards CredentialProvider>)
            : CompositeAndroidCredentialProvider = CompositeAndroidCredentialProvider(providers)

    //    @Provides
//    @IntoMap
//    @ActivityScope
//    @StringKey(FacebookCredential.TYPE)
//    fun provideFacebookProvider(activity: AppCompatActivity): CredentialProvider = FacebookCredentialProvider(activity)
//
//    @Provides
//    @IntoMap
//    @ActivityScope
//    @StringKey(TwitterCredential.TYPE)
//    fun provideTwitterProvider(activity: AppCompatActivity): CredentialProvider =
//        TwitterCredentialProvider(activity, activity.getString(R.string.twitter_app_key),
//                activity.getString(R.string.twitter_app_secret))
    @Provides
    @IntoMap
    @ActivityScope
    @StringKey(EmailCredential.TYPE)
    fun provideEmailProvider(activity: AppCompatActivity): CredentialProvider =
            EmailCredentialProvider(activity)
}