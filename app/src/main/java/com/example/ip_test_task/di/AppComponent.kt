package com.example.ip_test_task.di

import android.content.Context
import com.example.ip_test_task.data.db.ProductDataBase
import com.example.ip_test_task.presentation.MainActivity
import dagger.Component
import javax.inject.Singleton

@Component(
    dependencies = [
        AppDependencies::class
    ],
    modules = [
        AppModule::class,
        RepositoryModule::class,
        DataBaseModule::class,
        ProductDBModule::class
    ]
)
@Singleton
interface AppComponent {

    fun context(): Context
    fun dataBase(): ProductDataBase
    fun injectMainActivity(main: MainActivity)

    @Component.Factory
    interface Factory{
        fun create(appDependencies: AppDependencies): AppComponent
    }
}

object AppComponentHolder {
    lateinit var appComponent: AppComponent
}