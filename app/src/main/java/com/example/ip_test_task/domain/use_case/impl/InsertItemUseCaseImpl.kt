package com.example.ip_test_task.domain.use_case.impl

import com.example.ip_test_task.domain.ProductRepository
import com.example.ip_test_task.domain.entity.ProductItem
import com.example.ip_test_task.domain.use_case.InsertItemUseCase
import javax.inject.Inject

class InsertItemUseCaseImpl @Inject constructor(
    private val repository: ProductRepository
): InsertItemUseCase {
    override suspend fun invoke(product: ProductItem) {
        repository.insert(product)
    }
}