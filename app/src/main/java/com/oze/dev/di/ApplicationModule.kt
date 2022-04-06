package com.oze.dev.di

import android.content.Context
import androidx.room.Room
import com.oze.dev.db.local.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object ApplicationModule {
    @[Provides Singleton]
    fun provideYourDatabase(
        @ApplicationContext app: Context
    ) = Room.databaseBuilder(
        app,
        AppDatabase::class.java,
        AppDatabase.DEVELOPERS_DB
    ).build()

    @[Provides Singleton]
    fun provideYourDao(db: AppDatabase) = db.getDeveloperDao()
}