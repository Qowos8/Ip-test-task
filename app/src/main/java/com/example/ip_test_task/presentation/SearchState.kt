package com.example.ip_test_task.presentation

sealed class SearchState {
    class Error(val error: String): SearchState()
    class Success(val query: String): SearchState()
    object Init: SearchState()
}