package com.example.ip_test_task.data.mapper

import com.example.ip_test_task.data.db.ProductEntity
import com.example.ip_test_task.domain.entity.ProductItem
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

fun ProductItem.toDb(): ProductEntity {
    return ProductEntity(
        id = id,
        name = name,
        time = time,
        tags = fromDomainToJsonString(tags),
        amount = amount,
    )
}

fun fromDomainToJsonString(products: List<String?>?): String {
    val json = Json { ignoreUnknownKeys = true }
    return json.encodeToString(products)
}