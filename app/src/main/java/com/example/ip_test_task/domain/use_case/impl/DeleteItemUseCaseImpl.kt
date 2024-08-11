package com.example.ip_test_task.domain.use_case.impl

import com.example.ip_test_task.domain.ProductRepository
import com.example.ip_test_task.domain.entity.ProductItem
import com.example.ip_test_task.domain.use_case.DeleteItemUseCase
import javax.inject.Inject

class DeleteItemUseCaseImpl @Inject constructor(
    private val repository: ProductRepository
): DeleteItemUseCase {
    override suspend fun invoke(product: ProductItem) {
        repository.delete(product)
    }
}