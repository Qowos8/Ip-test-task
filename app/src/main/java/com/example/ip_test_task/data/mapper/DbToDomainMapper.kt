package com.example.ip_test_task.data.mapper

import com.example.ip_test_task.data.db.ProductEntity
import com.example.ip_test_task.domain.entity.ProductItem
import kotlinx.serialization.json.Json

fun ProductEntity.toDomain(): ProductItem {
    return ProductItem(
        id = id,
        name = name,
        time = time,
        tags = fromJsonString(tags),
        amount = amount,
    )
}

fun fromJsonString(jsonString: String): List<String> {
    val json = Json { ignoreUnknownKeys = true }
    return json.decodeFromString<List<String>>(jsonString)
}