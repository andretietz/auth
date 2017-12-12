package com.andretietz.auth.demo.injection

import android.support.v7.app.AppCompatActivity
import com.andretietz.auth.demo.MainActivity
import com.andretietz.auth.demo.injection.scopes.ActivityScope
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ApplicationModule {

    @ActivityScope
    @ContributesAndroidInjector(modules = arrayOf(AuthProviderModule::class, MainActivityProvider::class))
    abstract fun provideActivity(): MainActivity

    @Module
    abstract class MainActivityProvider {
        @Binds
        abstract fun provideActivity(mainActivity: MainActivity): AppCompatActivity
    }
}