package com.jecsdev.eleclive.core.di

import android.content.Context
import com.jecsdev.eleclive.domain.repository.Repository
import com.jecsdev.eleclive.domain.useCases.GetElectionsUseCase
import com.jecsdev.eleclive.utils.providers.GetResourceProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext

@Module
@InstallIn(ViewModelComponent::class)
class ViewModelModule {
    @Provides
    fun provideGetElectionsUseCase(repository: Repository): GetElectionsUseCase {
        return GetElectionsUseCase(repository)
    }

    @Provides
    fun provideResourceProvider(@ApplicationContext context: Context): GetResourceProvider {
        return GetResourceProvider(context)
    }

}