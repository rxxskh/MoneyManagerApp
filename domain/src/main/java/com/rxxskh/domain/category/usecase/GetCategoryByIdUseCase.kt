package com.rxxskh.domain.category.usecase

import com.rxxskh.utils.Resource
import com.rxxskh.domain.category.model.Category
import com.rxxskh.domain.category.repository.CategoryRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetCategoryByIdUseCase @Inject constructor(
    private val categoryRepository: CategoryRepository
) {

    operator fun invoke(categoryId: String): Flow<Resource<Category>> = flow {
        try {
            val result = categoryRepository.getCategoryById(categoryId = categoryId)
            emit(Resource.Success(data = result))
        } catch (e: Exception) {
            emit(Resource.Error("Неизвестная ошибка"))
        }
    }.flowOn(Dispatchers.IO)
}