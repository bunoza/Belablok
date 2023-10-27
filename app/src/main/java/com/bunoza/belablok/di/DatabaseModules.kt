package com.bunoza.belablok.di

import androidx.room.Room
import com.bunoza.belablok.data.database.AppDatabase
import org.koin.dsl.module

val databaseModule = module {
    single { get<AppDatabase>().scoreDao() }
    single { get<AppDatabase>().gameDao() }
    single {
        Room.databaseBuilder(get(), AppDatabase::class.java, "score_database")
            .fallbackToDestructiveMigration().build()
    }
}
