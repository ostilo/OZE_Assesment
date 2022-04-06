package com.oze.dev.db.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.oze.dev.db.model.converters.DeveloperDataConverter
import com.oze.dev.db.model.converters.DeveloperRepoResultConverter
import com.oze.dev.db.model.repo.DeveloperRepoResult
import com.oze.dev.db.model.repo.DeveloperRepoResultItem
import kotlinx.parcelize.Parcelize

@Entity(tableName = "developers")
@Parcelize
data class DeveloperInfo (
    @SerializedName("avatar_url")
    @Expose
    var avatarUrl: String,

    var isFavorite: Boolean = false,

    @TypeConverters(DeveloperDataConverter::class)
    @Expose
    var developerData: DeveloperData? = null,


    @Expose
    @TypeConverters(DeveloperRepoResultConverter::class)
    var developerRepoResult: List<DeveloperRepoResultItem> = listOf(),

    @SerializedName("events_url")
    @Expose
    var eventsUrl: String,
    @SerializedName("followers_url")
    @Expose
    var followersUrl: String,
    @SerializedName("following_url")
    @Expose
    var followingUrl: String,
    @SerializedName("gists_url")
    @Expose
    var gistsUrl: String,
    @SerializedName("gravatar_id")
    @Expose
    var gravatarId: String,
    @SerializedName("html_url")
    @Expose
    var htmlUrl: String,
    @SerializedName("id")


    @ColumnInfo
    @Expose
    @PrimaryKey var id: Int,
    @SerializedName("login")

    @Expose
    var login: String,
    @SerializedName("node_id")
    @Expose
    var nodeId: String,
    @SerializedName("organizations_url")
    @Expose
    var organizationsUrl: String,
    @SerializedName("received_events_url")
    @Expose
    var receivedEventsUrl: String,
    @SerializedName("repos_url")
    @Expose
    var reposUrl: String,
    @SerializedName("score")
    @Expose
    var score: Double,
    @SerializedName("site_admin")
    @Expose
    var siteAdmin: Boolean,
    @SerializedName("starred_url")
    @Expose
    var starredUrl: String,
    @SerializedName("subscriptions_url")
    @Expose
    var subscriptionsUrl: String,
    @SerializedName("type")
    @Expose
    var type: String,
    @SerializedName("url")
    @Expose
    var url: String

): Parcelable