package com.example.ip_test_task.domain.use_case

import com.example.ip_test_task.domain.entity.ProductItem

interface InsertItemUseCase {
    suspend operator fun invoke(product: ProductItem)
}