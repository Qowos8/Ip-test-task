package com.example.ip_test_task.domain

import com.example.ip_test_task.domain.entity.ProductItem
import kotlinx.coroutines.flow.Flow

interface ProductRepository {

    suspend fun insert(product: ProductItem)

    suspend fun delete(product: ProductItem)

    fun getItems(): Flow<List<ProductItem>>

    suspend fun searchProduct(query: String, currentList: List<ProductItem>): List<ProductItem>
}
