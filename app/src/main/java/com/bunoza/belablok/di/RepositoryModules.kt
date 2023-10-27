package com.bunoza.belablok.di

import com.bunoza.belablok.data.database.preferences.PreferenceManager
import com.bunoza.belablok.data.repositories.DatabaseRepository
import com.bunoza.belablok.data.repositories.PreferenceRepository
import org.koin.dsl.module

val repositoryModule = module {
    single { DatabaseRepository(get(),get()) }
    single {PreferenceManager(get())}
    single { PreferenceRepository(get()) }
}
