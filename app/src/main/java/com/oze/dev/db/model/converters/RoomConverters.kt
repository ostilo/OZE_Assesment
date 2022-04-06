package com.oze.dev.db.model.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.oze.dev.db.model.repo.DeveloperRepoResultItem

object RoomConverters {

    @TypeConverter
    @JvmStatic
    fun restoreModelList(listOfString: String): List<DeveloperRepoResultItem> {
        return Gson().fromJson(listOfString, object : TypeToken<List<DeveloperRepoResultItem>>() {
        }.type)
    }

    @TypeConverter
    @JvmStatic
    fun saveModelListAsString(listOfString: List<DeveloperRepoResultItem>): String {
        return Gson().toJson(listOfString)
    }

}