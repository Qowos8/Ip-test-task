package com.example.ip_test_task.domain.use_case

import com.example.ip_test_task.domain.entity.ProductItem
import kotlinx.coroutines.flow.Flow

interface GetAllItemsUseCase {
    suspend operator fun invoke(): Flow<List<ProductItem>>
}