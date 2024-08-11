package com.example.ip_test_task.presentation

import android.app.Application
import com.example.ip_test_task.di.AppComponentHolder
import com.example.ip_test_task.di.DaggerAppComponent

class Application: Application() {

    override fun onCreate() {
        super.onCreate()

        AppComponentHolder.appComponent = DaggerAppComponent.factory().create { this }
    }
}