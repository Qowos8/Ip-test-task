package com.example.ip_test_task.data

import com.example.ip_test_task.data.db.ProductDao
import com.example.ip_test_task.data.db.ProductEntity
import com.example.ip_test_task.data.mapper.toDb
import com.example.ip_test_task.data.mapper.toDomain
import com.example.ip_test_task.domain.ProductRepository
import com.example.ip_test_task.domain.entity.ProductItem
import com.example.ip_test_task.utils.FilterByName
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.forEach
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val dao: ProductDao
): ProductRepository {

    override suspend fun insert(product: ProductItem) {
        dao.insert(product.toDb())
    }

    override suspend fun delete(product: ProductItem) {
        dao.delete(product.toDb())
    }

    override fun getItems(): Flow<List<ProductItem>> {
        return dao.getAllProducts().map { list -> list.map { it.toDomain() } }
    }

    override suspend fun searchProduct(query: String, currentList: List<ProductItem>): List<ProductItem> {
        return FilterByName.filterItemsByName(currentList, query)
    }
}