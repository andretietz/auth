package com.andretietz.auth.demo.injection

import android.app.Application
import com.andretietz.auth.demo.DemoApplication
import com.andretietz.auth.demo.injection.scopes.ApplicationScope
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule

@ApplicationScope
@Component(modules = arrayOf(AndroidSupportInjectionModule::class, ApplicationModule::class, AuthModule::class))
interface DemoComponent : AndroidInjector<DemoApplication> {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): DemoComponent
    }

}