package com.rxxskh.domain.category.usecase

import com.rxxskh.utils.Resource
import com.rxxskh.domain.category.repository.CategoryRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class LoadCategoryDataUseCase @Inject constructor(
    private val categoryRepository: CategoryRepository
) {

    operator fun invoke(): Flow<Resource<Unit>> = flow {
        try {
            val result = categoryRepository.loadData()
            emit(Resource.Success(data = result))
        } catch (e: Exception) {
            emit(Resource.Error("Неизвестная ошибка"))
        }
    }.flowOn(Dispatchers.IO)
}