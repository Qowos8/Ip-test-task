package com.example.ip_test_task.domain.use_case

import com.example.ip_test_task.domain.entity.ProductItem

interface SearchProductUseCase {
    suspend operator fun invoke(query: String, currentList: List<ProductItem>): List<ProductItem>
}