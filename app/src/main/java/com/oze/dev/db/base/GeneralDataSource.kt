package com.oze.dev.db.base

import com.oze.dev.db.model.DeveloperData
import com.oze.dev.db.model.DevelopersResult
import com.oze.dev.db.model.repo.DeveloperRepoResult
import kotlinx.coroutines.flow.Flow

interface GeneralDataSource {
    /*
   * Get specific developer data from server
   * */
    suspend fun getSpecificDeveloper(
        user: String
    ): ResultCallBack<DeveloperData>

    /*
   * Get Developers
   * */
    suspend fun getDevelopers(
        page: Int,
        search: String
    ): ResultCallBack<Flow<DevelopersResult>>
    /*
    * Get Developers  End
    * */

    //
    /*
  * Get Developers
  * */

    suspend fun getSpecificDeveloperRepos(
        page: Int,
        user: String
    ): ResultCallBack<Flow<DeveloperRepoResult>>
}