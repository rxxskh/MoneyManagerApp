package com.rxxskh.data.category.remote.model

import com.rxxskh.domain.category.model.Category

data class CategoryRemoteData(
    val category_id: String? = null,
    val category_name: String? = null,
    val category_icon: Int? = null,
    val category_color: Int? = null,
    val category_deleted: Boolean? = null
)

fun Category.toCategoryRemoteData() = CategoryRemoteData(
    category_id = categoryId,
    category_name = categoryName,
    category_icon = categoryIcon,
    category_color = categoryColor,
    category_deleted = categoryDeleted
)

fun CategoryRemoteData.toCategory() = Category(
    categoryId = category_id,
    categoryName = category_name ?: "",
    categoryIcon = category_icon ?: 0,
    categoryColor = category_color ?: 0,
    categoryDeleted = category_deleted ?: true
)