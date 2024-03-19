package com.rxxskh.moneymanagerapp.presentation.screen.categories.edit

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rxxskh.domain.category.model.Category
import com.rxxskh.domain.category.usecase.ApplyCategoryUseCase
import com.rxxskh.domain.category.usecase.DeleteCategoryUseCase
import com.rxxskh.domain.category.usecase.GetCategoryByIdUseCase
import com.rxxskh.moneymanagerapp.common.Constants
import com.rxxskh.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class CategoryEditViewModel @Inject constructor(
    private val applyCategoryUseCase: ApplyCategoryUseCase,
    private val getCategoryByIdUseCase: GetCategoryByIdUseCase,
    private val deleteCategoryUseCase: DeleteCategoryUseCase
) : ViewModel() {

    private var passedCategoryId by mutableStateOf<String?>(null)
    private var categoryIdSettingStatus by mutableStateOf<Boolean>(false)
    private var passedAccountId by mutableStateOf<String?>(null)
    private var accountIdSettingStatus by mutableStateOf<Boolean>(false)

    var name by mutableStateOf<String>("")
        private set
    var icon by mutableStateOf<Int>(Constants.CATEGORY_ICONS[0])
        private set
    var colorIndex by mutableStateOf<Int>(0)
        private set

    var iconScreenStatus by mutableStateOf<Boolean>(false)
        private set
    var colorScreenStatus by mutableStateOf<Boolean>(false)
        private set

    fun passData(accountId: String, categoryId: String) {
        setAccountId(accountId)
        setCategoryId(categoryId)
    }

    private fun setCategoryId(input: String) {
        if (!categoryIdSettingStatus) {
            passedCategoryId = if (input == Constants.NEW_TAG) null else input
            if (passedCategoryId != null) {
                loadCategory()
            }
            categoryIdSettingStatus = true
        }
    }

    private fun setAccountId(input: String) {
        if (!accountIdSettingStatus) {
            passedAccountId = if (input == Constants.SHARED_ACCOUNT_TAG) null else input
            accountIdSettingStatus = true
        }
    }

    fun updateCategoryName(input: String) {
        name = input.replace("  ", " ")
    }

    fun clearCategoryName() {
        name = ""
    }

    fun updateIconScreenStatus() {
        iconScreenStatus = !iconScreenStatus
    }

    fun updateIcon(input: Int) {
        icon = input
    }

    fun updateColorScreenStatus() {
        colorScreenStatus = !colorScreenStatus
    }

    fun updateColor(input: Int) {
        colorIndex = input
    }

    private fun isCorrectInput(): Boolean = name.isNotEmpty()

    fun applyCategory(onComplete: () -> Unit) {
        if (isCorrectInput()) {
            applyCategoryUseCase(
                category = Category(
                    categoryId = passedCategoryId,
                    categoryName = name,
                    categoryIcon = icon,
                    categoryColor = colorIndex
                ),
                accountId = passedAccountId
            ).onEach { }.launchIn(viewModelScope)
                .invokeOnCompletion { onComplete() }
        }
    }

    fun deleteCategory(onComplete: () -> Unit) {
        deleteCategoryUseCase(
            category = Category(
                categoryId = passedCategoryId,
                categoryName = name,
                categoryIcon = icon,
                categoryColor = colorIndex
            )
        ).onEach { }.launchIn(viewModelScope)
            .invokeOnCompletion { onComplete() }
    }

    private fun loadCategory() {
        getCategoryByIdUseCase(categoryId = passedCategoryId!!).onEach { result ->
            when (result) {
                is Resource.Error -> {}
                is Resource.Success -> {
                    val category = result.data!!
                    name = category.categoryName
                    icon = category.categoryIcon
                    colorIndex = category.categoryColor
                }
            }
        }.launchIn(viewModelScope)
    }
}