package com.rxxskh.data.category.repository

import com.rxxskh.data.account.local.AccountLocalDataSource
import com.rxxskh.data.category.local.CategoryLocalDataSource
import com.rxxskh.data.category.local.model.CategoryLocalData
import com.rxxskh.data.category.local.model.toCategory
import com.rxxskh.data.category.local.model.toCategoryLocalData
import com.rxxskh.data.category.remote.CategoryRemoteDataSource
import com.rxxskh.data.category.remote.model.toCategory
import com.rxxskh.data.category.remote.model.toCategoryRemoteData
import com.rxxskh.data.user.local.UserLocalDataSource
import com.rxxskh.domain.category.model.Category
import com.rxxskh.domain.category.repository.CategoryRepository
import javax.inject.Inject

class CategoryRepositoryImpl @Inject constructor(
    private val categoryLocalDataSource: CategoryLocalDataSource,
    private val categoryRemoteDataSource: CategoryRemoteDataSource,
    private val userLocalDataSource: UserLocalDataSource,
    private val accountLocalDataSource: AccountLocalDataSource
) : CategoryRepository {

    override suspend fun loadData() {
        val categoryMemberList = categoryRemoteDataSource.getCategoryMemberList(
            accountIdList = accountLocalDataSource.get().map { it.accountId },
            userId = userLocalDataSource.getUser()!!.userId!!
        )
        val categories = categoryRemoteDataSource.getCategories(
            categoryIdList = categoryMemberList.map { it.category_id!! }
        )
        categoryLocalDataSource.save(categoryLocalDataList = categories.map {
            CategoryLocalData(
                accountId = categoryMemberList.first { categoryMember -> categoryMember.category_id == it.category_id }.account_id,
                category = it.toCategory()
            )
        })
    }

    override suspend fun getCategories(accountId: String?): List<Category> {
        return categoryLocalDataSource.get().filter { it.accountId == accountId }
            .filter { !it.category.categoryDeleted }
            .map { it.toCategory() }
    }

    override suspend fun getCategoryById(categoryId: String): Category {
        return categoryLocalDataSource.get()
            .first { it.category.categoryId == categoryId }.toCategory()
    }

    override suspend fun applyCategory(category: Category, accountId: String?) {
        val categoryId =
            if (category.categoryId == null) categoryRemoteDataSource.pushCategoryKey() else category.categoryId

        categoryLocalDataSource.push(
            categoryLocalData = category.copy(categoryId = categoryId)
                .toCategoryLocalData(accountId)
        )

        categoryRemoteDataSource.pushCategory(
            categoryRemoteData = category.toCategoryRemoteData().copy(category_id = categoryId)
        )
        if (category.categoryId == null) {
            categoryRemoteDataSource.pushCategoryMember(
                categoryId = categoryId!!,
                userId = if (accountId == null) userLocalDataSource.getUser()!!.userId!! else null,
                accountId = accountId
            )
        }
    }

    override suspend fun deleteCategory(category: Category) {
        categoryLocalDataSource.edit(category = category.copy(categoryDeleted = true))
        categoryRemoteDataSource.pushCategory(
            categoryRemoteData = category.copy(categoryDeleted = true).toCategoryRemoteData()
        )
    }
}