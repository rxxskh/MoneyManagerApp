package com.rxxskh.domain.category.model

import com.rxxskh.domain.transaction.model.OperationType

data class Category(
    val categoryId: String? = null,
    val categoryName: String,
    val categoryIcon: Int,
    val categoryColor: Int,
    val categoryType: OperationType,
    val categoryDeleted: Boolean = false,
)