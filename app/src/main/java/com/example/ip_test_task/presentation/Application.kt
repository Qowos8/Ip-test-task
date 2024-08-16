package com.example.ip_test_task.presentation

import android.app.Application
import android.content.Context
import com.example.ip_test_task.di.AppComponentHolder
import com.example.ip_test_task.di.DaggerAppComponent
import java.io.File
import java.io.FileOutputStream

class Application: Application() {

    override fun onCreate() {
        super.onCreate()

        AppComponentHolder.appComponent = DaggerAppComponent.factory().create { this }
        initDatabase(this)
    }

    private fun initDatabase(context: Context) {
        val dbPath = context.getDatabasePath(DB_NAME).absolutePath

        if (!File(dbPath).exists()) {
            context.assets.open(DB_NAME).use { inputStream ->
                FileOutputStream(dbPath).use { outputStream ->
                    val buffer = ByteArray(1024)
                    var length: Int
                    while (inputStream.read(buffer).also { length = it } > 0) {
                        outputStream.write(buffer, 0, length)
                    }
                }
            }
        }
    }

    private companion object{
        private const val DB_NAME = "data.db"
    }
}