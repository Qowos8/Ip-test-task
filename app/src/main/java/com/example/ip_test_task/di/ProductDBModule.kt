package com.example.ip_test_task.di

import com.example.ip_test_task.data.db.ProductDao
import com.example.ip_test_task.data.db.ProductDataBase
import dagger.Module
import dagger.Provides

@Module
object ProductDBModule {
    @Provides
    fun provideDB(dataBase: ProductDataBase): ProductDao{
        return dataBase.productDao()
    }
}