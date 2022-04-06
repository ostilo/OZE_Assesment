package com.oze.dev.ui.fragments.developerdetail.usecase

import com.oze.dev.db.base.GeneralDataSource
import javax.inject.Inject

class SpecificDeveloperUseCase  @Inject constructor(private val repository: GeneralDataSource) {
    suspend operator fun invoke(
        user: String
    ) = repository.getSpecificDeveloper(
        user = user
    )
}