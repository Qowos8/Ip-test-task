package com.example.ip_test_task.presentation

import com.example.ip_test_task.domain.entity.ProductItem

sealed class ProductState {
    class Error(val error: String): ProductState()
    class Success(val query: List<ProductItem>): ProductState()
    object Empty: ProductState()
    object Init: ProductState()
}