package com.oze.dev.ui.fragments.developers.usecase

import com.oze.dev.db.base.GeneralDataSource
import com.oze.dev.db.local.AppDatabase
import javax.inject.Inject

class DeveloperUseCase @Inject constructor(private  val repository: GeneralDataSource) {
    suspend operator fun invoke(
        page:Int =1,
        category:String,
    ) = repository.getDevelopers(
        page = page,
        search = category
    )

}