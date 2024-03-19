package com.rxxskh.data.category.local

import android.content.SharedPreferences
import com.google.gson.Gson
import com.rxxskh.data.category.local.model.CategoryLocalData
import com.rxxskh.data.category.local.model.CategoryLocalDataList
import com.rxxskh.data.category.local.model.toCategory
import com.rxxskh.domain.category.model.Category
import com.rxxskh.utils.SharedPreferenceProvider
import javax.inject.Inject

class CategoryLocalDataSource @Inject constructor(
    private val gson: Gson,
    private val sharedPreferences: SharedPreferences
) {

    suspend fun save(categoryLocalDataList: List<CategoryLocalData>) {
        with(sharedPreferences.edit()) {
            val json: String =
                gson.toJson(CategoryLocalDataList(categoryLocalDataList = categoryLocalDataList))
            putString(SharedPreferenceProvider.CATEGORIES, json)
            apply()
        }
    }

    suspend fun get(): List<CategoryLocalData> {
        val json: String? = sharedPreferences.getString(SharedPreferenceProvider.CATEGORIES, null)
        return if (json != null) {
            gson.fromJson(json, CategoryLocalDataList::class.java).categoryLocalDataList
        } else {
            emptyList()
        }
    }

    suspend fun getById(categoryId: String): Category {
        return get().first { it.category.categoryId == categoryId }.toCategory()
    }

    suspend fun push(categoryLocalData: CategoryLocalData) {
        val list = get().toMutableList()
        val found =
            list.firstOrNull { it.category.categoryId == categoryLocalData.category.categoryId }
        if (found == null) {
            list.add(categoryLocalData)
            save(categoryLocalDataList = list)
        } else {
            list[list.indexOf(found)] = categoryLocalData
        }
        save(categoryLocalDataList = list)
    }

    suspend fun edit(category: Category) {
        val list = get().toMutableList()
        val found = list.first { it.category.categoryId == category.categoryId }
        val foundAccountId = found.accountId
        list[list.indexOf(found)] = found.copy(accountId = foundAccountId, category = category)
        save(categoryLocalDataList = list)
    }
}