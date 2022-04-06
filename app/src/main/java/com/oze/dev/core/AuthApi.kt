package com.oze.dev.core

import com.oze.dev.core.auth.remote.ApiResponse
import com.oze.dev.core.auth.ApiRoutes
import com.oze.dev.db.model.DeveloperData
import com.oze.dev.db.model.DevelopersResult
import com.oze.dev.db.model.repo.DeveloperRepoResult
import com.oze.dev.utils.CONSTANTS
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface AuthApi {
    @GET(ApiRoutes.GET_SPECIFIC_DEVELOPER_DATA)
    suspend fun getSpecificUserData(
        @Path(CONSTANTS.DEVELOPER) user: String
    ): ApiResponse<DeveloperData>

    @GET(ApiRoutes.GET_SEARCH_DEVELOPER_DATA)
    suspend fun getSearchDeveloperData(
        @Query(CONSTANTS.SEARCH) search: String,
        @Query(CONSTANTS.PAGE) page: Int = 1
    ): ApiResponse<DevelopersResult>

    @GET(ApiRoutes.GET_SPECIFIC_DEVELOPER_REPOS)
    suspend fun getSpecificDeveloperReposData(
        @Path(CONSTANTS.DEVELOPER) user: String,
        @Query(CONSTANTS.PAGE) page: Int = 1
    ): ApiResponse<DeveloperRepoResult>
}