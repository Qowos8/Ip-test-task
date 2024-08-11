package com.example.ip_test_task.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("Products")
data class ProductEntity (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val time: Long,
    val tags: String,
    val amount: Int,
)
