package com.oze.dev.db.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.oze.dev.db.model.DeveloperData
import com.oze.dev.db.model.DeveloperInfo
import com.oze.dev.db.model.converters.DeveloperDataConverter
import com.oze.dev.db.model.converters.DeveloperRepoResultConverter
import com.oze.dev.db.model.converters.RoomConverters
import com.oze.dev.db.model.repo.DeveloperRepoResult
import com.oze.dev.db.model.repo.DeveloperRepoResultItem

@Database(version = 1, entities = [DeveloperInfo::class,DeveloperData::class,DeveloperRepoResultItem::class], exportSchema = false)
@TypeConverters(DeveloperRepoResultConverter::class,DeveloperDataConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getDeveloperDao(): DevelopersDao
    companion object {
        val DEVELOPERS_DB = "developers.db"
    }
}