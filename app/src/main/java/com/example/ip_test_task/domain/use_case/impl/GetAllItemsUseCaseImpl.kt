package com.example.ip_test_task.domain.use_case.impl

import com.example.ip_test_task.domain.ProductRepository
import com.example.ip_test_task.domain.entity.ProductItem
import com.example.ip_test_task.domain.use_case.GetAllItemsUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllItemsUseCaseImpl @Inject constructor(
    private val repository: ProductRepository
): GetAllItemsUseCase {

    override suspend fun invoke(): Flow<List<ProductItem>> {
        return repository.getItems()
    }
}