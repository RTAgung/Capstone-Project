package com.example.moviecatalogue

import android.app.Application
import com.example.moviecatalogue.core.di.databaseModule
import com.example.moviecatalogue.core.di.networkModule
import com.example.moviecatalogue.core.di.repositoryModule
import com.example.moviecatalogue.core.utils.ReleaseTree
import com.example.moviecatalogue.di.useCaseModule
import com.example.moviecatalogue.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import timber.log.Timber

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
                    useCaseModule,
                    viewModelModule
                )
            )
        }
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        } else {
            Timber.plant(ReleaseTree())
        }
    }
}