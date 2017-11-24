package com.andretietz.auth.demo

import com.andretietz.auth.demo.injection.DaggerDemoComponent
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication
import timber.log.Timber

class DemoApplication : DaggerApplication() {

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) Timber.plant(Timber.DebugTree())
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication>
            = DaggerDemoComponent.builder().application(this).build()
}