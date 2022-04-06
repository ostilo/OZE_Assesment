package com.oze.dev.core.auth.modules

import com.oze.dev.core.AuthApi
import com.oze.dev.db.base.GeneralDataSource
import com.oze.dev.db.base.GeneralRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
    @Module
    @InstallIn(SingletonComponent::class)
    class GeneralRepositoryModule {

        @Provides
        fun provideGeneralDataSource(authApi: AuthApi): GeneralDataSource {
            return GeneralRepository(authApi)
        }



    }
