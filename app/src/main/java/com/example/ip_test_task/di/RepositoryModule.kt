package com.example.ip_test_task.di

import com.example.ip_test_task.data.ProductRepositoryImpl
import com.example.ip_test_task.domain.ProductRepository
import com.example.ip_test_task.domain.use_case.DeleteItemUseCase
import com.example.ip_test_task.domain.use_case.GetAllItemsUseCase
import com.example.ip_test_task.domain.use_case.InsertItemUseCase
import com.example.ip_test_task.domain.use_case.SearchProductUseCase
import com.example.ip_test_task.domain.use_case.impl.DeleteItemUseCaseImpl
import com.example.ip_test_task.domain.use_case.impl.GetAllItemsUseCaseImpl
import com.example.ip_test_task.domain.use_case.impl.GetAllItemsUseCaseImpl_Factory
import com.example.ip_test_task.domain.use_case.impl.InsertItemUseCaseImpl
import com.example.ip_test_task.domain.use_case.impl.SearchProductUseCaseImpl
import com.example.ip_test_task.presentation.MainViewModelFactory
import dagger.Binds
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
interface RepositoryModule {
    @Binds
    fun provideRepository(repository: ProductRepositoryImpl): ProductRepository

    @Binds
    fun provideDeleteItemUseCase(deleteItemUseCase: DeleteItemUseCaseImpl): DeleteItemUseCase

    @Binds
    fun provideInsertItemUseCase(insertItemUseCase: InsertItemUseCaseImpl): InsertItemUseCase

    @Binds
    fun provideGetItemsUseCase(getItemUseCase: GetAllItemsUseCaseImpl): GetAllItemsUseCase

    @Binds
    fun provideSearchItemUseCase(searchProductUseCaseImpl: SearchProductUseCaseImpl): SearchProductUseCase
}