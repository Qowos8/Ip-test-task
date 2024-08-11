package com.example.ip_test_task.utils

import com.example.ip_test_task.domain.entity.ProductItem

object FilterByName {
    fun filterItemsByName(items: List<ProductItem>, query: String): List<ProductItem> {
        return items.filter { it.name.contains(query, ignoreCase = true) }
    }
}