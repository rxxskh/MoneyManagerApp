package com.rxxskh.data.category.local.model

import com.rxxskh.domain.category.model.Category

data class CategoryLocalData(
    val accountId: String? = null,
    val category: Category
)

fun CategoryLocalData.toCategory() = Category(
    categoryId = category.categoryId,
    categoryName = category.categoryName,
    categoryIcon = category.categoryIcon,
    categoryColor = category.categoryColor,
    categoryType = category.categoryType,
    categoryDeleted = category.categoryDeleted
)

fun Category.toCategoryLocalData(accountId: String?) = CategoryLocalData(
    accountId = accountId,
    category = this
)