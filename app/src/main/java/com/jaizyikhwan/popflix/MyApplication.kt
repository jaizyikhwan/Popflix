package com.jaizyikhwan.popflix

import android.app.Application
import com.jaizyikhwan.core.di.databaseModule
import com.jaizyikhwan.core.di.networkModule
import com.jaizyikhwan.core.di.repositoryModule
import com.jaizyikhwan.popflix.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger(Level.NONE)
            androidContext(this@MyApplication)
            modules(
                listOf(
                    databaseModule,
                    networkModule,
                    repositoryModule,
                    appModule
                )
            )
        }
    }
}
