package com.rxxskh.moneymanagerapp.presentation.screen.categories.main

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rxxskh.utils.Resource
import com.rxxskh.domain.category.model.Category
import com.rxxskh.domain.category.usecase.GetCategoriesUseCase
import com.rxxskh.moneymanagerapp.common.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class CategoriesViewModel @Inject constructor(
    private val getCategoriesUseCase: GetCategoriesUseCase
) : ViewModel() {

    private var passedAccountId by mutableStateOf<String?>(null)
    private var statusPassingAccountId by mutableStateOf<Boolean>(false)

    var categories by mutableStateOf<List<Category>>(emptyList())
        private set


    fun passAccountId(input: String) {
        if (!statusPassingAccountId) {
            passedAccountId = if (input == Constants.SHARED_ACCOUNT_TAG) null else input
            statusPassingAccountId = true
            loadCategories()
        }
    }

    private fun loadCategories() {
        getCategoriesUseCase(accountId = passedAccountId).onEach { result ->
            when (result) {
                is Resource.Error -> {}

                is Resource.Success -> {
                    categories = result.data!!
                }
            }
        }.launchIn(viewModelScope)
    }
}