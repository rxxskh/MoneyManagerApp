package com.rxxskh.domain.category.repository

import com.rxxskh.domain.category.model.Category

interface CategoryRepository {

    suspend fun loadData()
    suspend fun getCategories(accountId: String?): List<Category>
    suspend fun getCategoryById(categoryId: String): Category
    suspend fun applyCategory(category: Category, accountId: String?)
    suspend fun deleteCategory(category: Category)
}