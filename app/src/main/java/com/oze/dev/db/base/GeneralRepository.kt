package com.oze.dev.db.base

import com.oze.dev.core.AuthApi
import com.oze.dev.core.auth.remote.ApiResponse
import com.oze.dev.core.auth.message
import com.oze.dev.db.model.DeveloperData
import com.oze.dev.db.model.DevelopersResult
import com.oze.dev.db.model.repo.DeveloperRepoResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GeneralRepository @Inject constructor(private val apiService: AuthApi) : GeneralDataSource {

    override suspend fun getSpecificDeveloper(
        user: String
    ): ResultCallBack<DeveloperData> {
        var result: ResultCallBack<DeveloperData>
        apiService.getSpecificUserData(user = user).apply {
            result = when (this) {
                is ApiResponse.ApiSuccessResponse -> {
                    data?.let {
                        ResultCallBack.Success(it)
                    } ?: ResultCallBack.Error("Null record")
                }
                is ApiResponse.ApiFailureResponse.Error -> {
                    ResultCallBack.Error(message())
                }
                is ApiResponse.ApiFailureResponse.Exception -> {
                    ResultCallBack.CallException(exception = this.exception as Exception)
                }
            }
        }
        return result
    }

    override suspend fun getSpecificDeveloperRepos(
        page: Int,
        user: String
    ): ResultCallBack<Flow<DeveloperRepoResult>> {
        var result: ResultCallBack<Flow<DeveloperRepoResult>>
        apiService.getSpecificDeveloperReposData(
            page = page,
            user = user
        ).apply {
            result = when (this) {
                is ApiResponse.ApiSuccessResponse -> {
                    data?.let {
                        ResultCallBack.Success(
                            flow { emit(it) }
                        )
                    } ?: ResultCallBack.Error("Null record")
                }
                is ApiResponse.ApiFailureResponse.Error -> {
                    ResultCallBack.Error(message())
                }
                is ApiResponse.ApiFailureResponse.Exception -> {
                    ResultCallBack.CallException(exception = this.exception as Exception)
                }
            }
        }
        return result
    }

    override suspend fun getDevelopers(
        page: Int,
        search: String
    ): ResultCallBack<Flow<DevelopersResult>> {
        var result: ResultCallBack<Flow<DevelopersResult>>
        apiService.getSearchDeveloperData(
            page = page,
            search = search
        ).apply {
            result = when (this) {
                is ApiResponse.ApiSuccessResponse -> {
                    data?.let {
                        ResultCallBack.Success(
                            flow { emit(it) }
                        )
                    } ?: ResultCallBack.Error("Null record")
                }
                is ApiResponse.ApiFailureResponse.Error -> {
                    ResultCallBack.Error(message())
                }
                is ApiResponse.ApiFailureResponse.Exception -> {
                    ResultCallBack.CallException(exception = this.exception as Exception)
                }
            }
        }
        return result
    }
}