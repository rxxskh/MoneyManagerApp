package com.rxxskh.domain.category.usecase

import com.rxxskh.domain.category.model.Category
import com.rxxskh.domain.category.repository.CategoryRepository
import com.rxxskh.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class ApplyCategoryUseCase @Inject constructor(
    private val categoryRepository: CategoryRepository
) {

    operator fun invoke(category: Category, accountId: String?): Flow<Resource<Unit>> = flow {
        try {
            val result = categoryRepository.applyCategory(category = category, accountId = accountId)
            emit(Resource.Success(data = result))
        } catch (e: Exception) {
            emit(Resource.Error("Неизвестная ошибка"))
        }
    }.flowOn(Dispatchers.IO)
}