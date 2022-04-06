package com.oze.dev.db.model

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class DevelopersResult(
    @SerializedName("incomplete_results")
    @Expose
    var incompleteResults: Boolean,
    @SerializedName("items")
    @Expose
    var usersInfo: List<DeveloperInfo>,
    @SerializedName("total_count")
    @Expose
    var totalCount: Int
) : Parcelable
