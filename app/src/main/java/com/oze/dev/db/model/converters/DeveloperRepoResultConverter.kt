package com.oze.dev.db.model.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.oze.dev.db.model.repo.DeveloperRepoResultItem
import java.lang.Exception
import java.util.*

object DeveloperRepoResultConverter {
    @TypeConverter
    fun toString(paymentRecords: List<DeveloperRepoResultItem?>?): String? {
        return try {
            Gson().toJson(paymentRecords)
        } catch (e: Exception) {
            null
        }
    }

    @TypeConverter
    fun toPaymentList(str: String?): List<DeveloperRepoResultItem?>? {
        if(str == "null"){
            return  Collections.emptyList()
        }
        return try {
            val type = object : TypeToken<List<DeveloperRepoResultItem?>?>() {}.type
            Gson().fromJson<List<DeveloperRepoResultItem?>>(str, type)
        } catch (e: Exception) {
            null
        }
    }
}