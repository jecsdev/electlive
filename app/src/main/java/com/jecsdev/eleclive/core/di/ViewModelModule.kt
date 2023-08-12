package com.jecsdev.eleclive.core.di

import com.jecsdev.eleclive.core.network.ApiService
import com.jecsdev.eleclive.data.domain.UseCases.GetElectionsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class ViewModelModule {
    @Provides
    fun provideGetElectionsUseCase(apiService: ApiService): GetElectionsUseCase {
        return GetElectionsUseCase(apiService)
    }
}