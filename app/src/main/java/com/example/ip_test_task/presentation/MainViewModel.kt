package com.example.ip_test_task.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ip_test_task.domain.entity.ProductItem
import com.example.ip_test_task.domain.use_case.DeleteItemUseCase
import com.example.ip_test_task.domain.use_case.GetAllItemsUseCase
import com.example.ip_test_task.domain.use_case.InsertItemUseCase
import com.example.ip_test_task.domain.use_case.SearchProductUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.launch

class MainViewModel(
    private val insertItemUseCase: InsertItemUseCase,
    private val deleteItemUseCase: DeleteItemUseCase,
    private val getAllItemsUseCase: GetAllItemsUseCase,
    private val searchProductUseCase: SearchProductUseCase
) : ViewModel() {

    private val _productState: MutableStateFlow<ProductState> = MutableStateFlow(ProductState.Init)
    val productState: StateFlow<ProductState> get() = _productState.asStateFlow()

    val currentSearch: MutableSharedFlow<String> = MutableSharedFlow(extraBufferCapacity = 1)

    private val _queryState: MutableStateFlow<SearchState> = MutableStateFlow(SearchState.Init)
    val queryState: StateFlow<SearchState> get() = _queryState.asStateFlow()

    private var currentList: List<ProductItem> = emptyList()

    private val _searchFieldText: MutableStateFlow<String> = MutableStateFlow("")
    val searchFieldText: StateFlow<String> get() = _searchFieldText.asStateFlow()

    private val _filteredProducts: MutableStateFlow<List<ProductItem>> = MutableStateFlow(emptyList())
    val filteredProducts: StateFlow<List<ProductItem>> get() = _filteredProducts.asStateFlow()

    init {
        searchFlow()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun searchFlow() {
        currentSearch
            .map { it.trim() }
            .mapLatest { query -> _queryState.emit(SearchState.Success(query)) }
            .launchIn(viewModelScope)
    }

    fun insert(product: ProductItem) {
        viewModelScope.launch {
            insertItemUseCase(product)
        }
    }

    fun delete(product: ProductItem) {
        viewModelScope.launch {
            deleteItemUseCase(product)
        }
    }

    fun searchProduct(query: String) {
        viewModelScope.launch {
            val result = searchProductUseCase(query, currentList)
            _filteredProducts.emit(result)
            _productState.emit(ProductState.Success(result))
        }
    }

    fun updateProductAmount(product: ProductItem, newAmount: Int) {
        val updatedProduct = product.copy(amount = newAmount)
        viewModelScope.launch {
            deleteItemUseCase(product)
            insertItemUseCase(updatedProduct)
        }
    }

    fun getAll() {
        viewModelScope.launch {
            getAllItemsUseCase().collect { products ->
                currentList = products

                if (products.isEmpty()) {
                    _productState.emit(ProductState.Empty)
                } else {
                    _productState.emit(ProductState.Success(products))
                    searchProduct(searchFieldText.value)
                }
            }
        }
    }

    fun updateSearchFieldText(newText: String) {
        viewModelScope.launch {
            _searchFieldText.emit(newText)
        }
    }
}