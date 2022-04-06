package com.oze.dev.db.model.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.oze.dev.db.model.DeveloperData
import com.oze.dev.db.model.repo.DeveloperRepoResultItem

class DeveloperRepoResultItemConverter {
    @TypeConverter
    fun toTransactionResponse(transSr: String?): DeveloperRepoResultItem {
        return Gson().fromJson(transSr, DeveloperRepoResultItem::class.java)
    }

    @TypeConverter
    fun toString(transactionResponse: DeveloperRepoResultItem?): String {
        return Gson().toJson(transactionResponse)
    }
}