package com.rxxskh.domain.account.usecase

import com.rxxskh.utils.Resource
import com.rxxskh.domain.account.model.Account
import com.rxxskh.domain.account.repository.AccountRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetAccountsUseCase @Inject constructor(
    private val accountRepository: AccountRepository
) {

    operator fun invoke(): Flow<Resource<List<Account>>> = flow {
        try {
            val result = accountRepository.getAccounts()
            emit(Resource.Success(data = result))
        } catch (e: Exception) {
            emit(Resource.Error("Неизвестная ошибка"))
        }
    }.flowOn(Dispatchers.IO)
}