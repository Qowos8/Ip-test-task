package com.example.ip_test_task.di

import android.content.Context
import androidx.room.Room
import com.example.ip_test_task.data.db.ProductDataBase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object DataBaseModule{
    private const val BASE_NAME = "product_database"

    @Provides
    @Singleton
    fun provideDataBase(context: Context): ProductDataBase {
        return Room.databaseBuilder(
                context,
                ProductDataBase::class.java,
                BASE_NAME
            )
                .fallbackToDestructiveMigration()
                .build()
        }
    }