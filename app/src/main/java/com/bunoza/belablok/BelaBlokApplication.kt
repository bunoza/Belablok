package com.bunoza.belablok

import android.app.Application
import com.bunoza.belablok.di.databaseModule
import com.bunoza.belablok.di.repositoryModule
import com.bunoza.belablok.di.viewModelModules
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin

class BelaBlokApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@BelaBlokApplication)
            modules(listOf(viewModelModules, repositoryModule, databaseModule))
        }
    }
}
