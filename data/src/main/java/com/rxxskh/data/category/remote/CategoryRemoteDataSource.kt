package com.rxxskh.data.category.remote

import com.rxxskh.data.category.remote.model.CategoryMemberRemoteData
import com.rxxskh.data.category.remote.model.CategoryRemoteData
import com.rxxskh.utils.FirebaseReferencesProvider
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class CategoryRemoteDataSource @Inject constructor() {

    suspend fun pushCategoryKey(): String = FirebaseReferencesProvider.CATEGORIES_REF.push().key!!

    suspend fun pushCategory(categoryRemoteData: CategoryRemoteData) {
        FirebaseReferencesProvider.CATEGORIES_REF
            .child(categoryRemoteData.category_id!!)
            .setValue(categoryRemoteData)
    }

    suspend fun pushCategoryMember(categoryId: String, userId: String?, accountId: String?) {
        val key = FirebaseReferencesProvider.CATEGORY_MEMBERS_REF.push().key!!
        FirebaseReferencesProvider.CATEGORY_MEMBERS_REF
            .child(key)
            .setValue(
                CategoryMemberRemoteData(
                    category_id = categoryId,
                    user_id = userId,
                    account_id = accountId
                )
            )
    }

    suspend fun getCategories(
        categoryIdList: List<String>,
    ): List<CategoryRemoteData> {
        return suspendCoroutine { continuation ->
            val result = mutableListOf<CategoryRemoteData>()
            FirebaseReferencesProvider.CATEGORIES_REF.get()
                .addOnSuccessListener { snapshot ->
                    if (snapshot.exists()) {
                        for (children in snapshot.children) {
                            val data = children.getValue(CategoryRemoteData::class.java)
                            if (data != null) {
                                if (categoryIdList.contains(data.category_id)) {
                                    result.add(data)
                                }
                            }
                        }
                    }
                    continuation.resume(result)
                }.addOnFailureListener { exception ->
                    continuation.resumeWithException(exception)
                }
        }
    }

    suspend fun getCategoryMemberList(
        accountIdList: List<String>,
        userId: String
    ): List<CategoryMemberRemoteData> {
        return suspendCoroutine { continuation ->
            val result = mutableListOf<CategoryMemberRemoteData>()
            FirebaseReferencesProvider.CATEGORY_MEMBERS_REF.get()
                .addOnSuccessListener { snapshot ->
                    if (snapshot.exists()) {
                        for (children in snapshot.children) {
                            val data = children.getValue(CategoryMemberRemoteData::class.java)
                            if (data != null) {
                                if (data.account_id != null) {
                                    if (accountIdList.contains(data.account_id)) {
                                        result.add(data)
                                    }
                                } else if (data.user_id != null) {
                                    if (data.user_id == userId) {
                                        result.add(data)
                                    }
                                }
                            }
                        }
                    }
                    continuation.resume(result)
                }.addOnFailureListener { exception ->
                    continuation.resumeWithException(exception)
                }
        }
    }
}