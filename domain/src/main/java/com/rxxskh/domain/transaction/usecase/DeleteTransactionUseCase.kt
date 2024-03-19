package com.rxxskh.domain.transaction.usecase

import com.rxxskh.domain.transaction.model.Transaction
import com.rxxskh.domain.transaction.repository.TransactionRepository
import com.rxxskh.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class DeleteTransactionUseCase @Inject constructor(
    private val transactionRepository: TransactionRepository
) {

    operator fun invoke(transaction: Transaction): Flow<Resource<Unit>> = flow {
        try {
            val result = transactionRepository.deleteTransaction(transaction = transaction)
            emit(Resource.Success(data = result))
        } catch (e: Exception) {
            emit(Resource.Error("Неизвестная ошибка"))
        }
    }.flowOn(Dispatchers.IO)
}