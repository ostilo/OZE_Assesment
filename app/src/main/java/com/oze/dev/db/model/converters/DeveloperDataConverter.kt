package com.oze.dev.db.model.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.oze.dev.db.model.DeveloperData
import java.util.*

class DeveloperDataConverter {
    @TypeConverter
    fun toTransactionResponse(transSr: String): DeveloperData {
        return if (transSr == "null") {
            DeveloperData("","","","","","","",
                0,"",0,"","","","","",
                0,"","","","","",0,0,"","" +
                        "",false,"","","","","","")

        }else {
            Gson().fromJson(transSr, DeveloperData::class.java)
        }
    }

    @TypeConverter
    fun toString(transactionResponse: DeveloperData?): String {
        return Gson().toJson(transactionResponse)
    }
}