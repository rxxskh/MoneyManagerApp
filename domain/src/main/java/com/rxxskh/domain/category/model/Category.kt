package com.rxxskh.domain.category.model

data class Category(
    val categoryId: String? = null,
    val categoryName: String,
    val categoryIcon: Int,
    val categoryColor: Int,
    val categoryDeleted: Boolean = false,
)