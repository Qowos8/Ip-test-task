package com.example.ip_test_task.domain.use_case.impl

import com.example.ip_test_task.domain.ProductRepository
import com.example.ip_test_task.domain.entity.ProductItem
import com.example.ip_test_task.domain.use_case.SearchProductUseCase
import javax.inject.Inject

class SearchProductUseCaseImpl @Inject constructor(
    private val repository: ProductRepository
): SearchProductUseCase {
    override suspend fun invoke(query: String, currentList: List<ProductItem>): List<ProductItem> {
        return repository.searchProduct(query, currentList)
    }
}