package com.example.ip_test_task.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.ip_test_task.domain.use_case.DeleteItemUseCase
import com.example.ip_test_task.domain.use_case.GetAllItemsUseCase
import com.example.ip_test_task.domain.use_case.InsertItemUseCase
import com.example.ip_test_task.domain.use_case.SearchProductUseCase
import javax.inject.Inject

class MainViewModelFactory @Inject constructor(
    private val insertItemUseCase: InsertItemUseCase,
    private val deleteItemUseCase: DeleteItemUseCase,
    private val getAllItemsUseCase:GetAllItemsUseCase,
    private val searchProductUseCase: SearchProductUseCase
): ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(
            insertItemUseCase = insertItemUseCase,
            deleteItemUseCase = deleteItemUseCase,
            getAllItemsUseCase = getAllItemsUseCase,
            searchProductUseCase = searchProductUseCase
        ) as T
    }
}