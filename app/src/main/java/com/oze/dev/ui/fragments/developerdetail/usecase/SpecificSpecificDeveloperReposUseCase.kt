package com.oze.dev.ui.fragments.developerdetail.usecase

import com.oze.dev.db.base.GeneralDataSource
import javax.inject.Inject

class SpecificSpecificDeveloperReposUseCase @Inject constructor(private val repository: GeneralDataSource) {
    suspend operator fun invoke(
        page: Int,
        user: String
    ) = repository.getSpecificDeveloperRepos(
        page = page,
        user = user
    )
}