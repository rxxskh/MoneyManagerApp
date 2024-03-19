package com.rxxskh.moneymanagerapp.di

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.rxxskh.data.account.local.AccountLocalDataSource
import com.rxxskh.data.account.remote.AccountRemoteDataSource
import com.rxxskh.data.account.repository.AccountRepositoryImpl
import com.rxxskh.data.category.local.CategoryLocalDataSource
import com.rxxskh.data.category.remote.CategoryRemoteDataSource
import com.rxxskh.data.category.repository.CategoryRepositoryImpl
import com.rxxskh.data.friend.local.FriendLocalDataSource
import com.rxxskh.data.friend.remote.FriendRemoteDataSource
import com.rxxskh.data.friend.repository.FriendRepositoryImpl
import com.rxxskh.data.transaction.local.TransactionLocalDataSource
import com.rxxskh.data.transaction.remote.TransactionRemoteDataSource
import com.rxxskh.data.transaction.repository.TransactionRepositoryImpl
import com.rxxskh.data.user.local.UserLocalDataSource
import com.rxxskh.data.user.remote.UserRemoteDataSource
import com.rxxskh.data.user.repository.UserRepositoryImpl
import com.rxxskh.domain.account.repository.AccountRepository
import com.rxxskh.domain.category.repository.CategoryRepository
import com.rxxskh.domain.friend.repository.FriendRepository
import com.rxxskh.domain.transaction.repository.TransactionRepository
import com.rxxskh.domain.user.repository.UserRepository
import com.rxxskh.utils.SharedPreferenceProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideGson(): Gson = Gson()

    @Provides
    @Singleton
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences =
        context.getSharedPreferences(SharedPreferenceProvider.PREFS_KEY, Context.MODE_PRIVATE)

    @Provides
    @Singleton
    fun provideUserRepository(
        userLocalDataSource: UserLocalDataSource,
        userRemoteDataSource: UserRemoteDataSource
    ): UserRepository =
        UserRepositoryImpl(userLocalDataSource, userRemoteDataSource)

    @Provides
    @Singleton
    fun provideFriendRepository(
        friendRemoteDataSource: FriendRemoteDataSource,
        friendLocalDataSource: FriendLocalDataSource,
        userLocalDataSource: UserLocalDataSource
    ): FriendRepository =
        FriendRepositoryImpl(friendRemoteDataSource, friendLocalDataSource, userLocalDataSource)

    @Provides
    @Singleton
    fun provideAccountRepository(
        accountRemoteDataSource: AccountRemoteDataSource,
        accountLocalDataSource: AccountLocalDataSource,
        userLocalDataSource: UserLocalDataSource
    ): AccountRepository =
        AccountRepositoryImpl(accountRemoteDataSource, accountLocalDataSource, userLocalDataSource)

    @Provides
    @Singleton
    fun provideCategoryRepository(
        categoryLocalDataSource: CategoryLocalDataSource,
        categoryRemoteDataSource: CategoryRemoteDataSource,
        userLocalDataSource: UserLocalDataSource,
        accountLocalDataSource: AccountLocalDataSource
    ): CategoryRepository =
        CategoryRepositoryImpl(
            categoryLocalDataSource,
            categoryRemoteDataSource,
            userLocalDataSource,
            accountLocalDataSource
        )

    @Singleton
    @Provides
    fun provideTransactionRepository(
        transactionLocalDataSource: TransactionLocalDataSource,
        transactionRemoteDataSource: TransactionRemoteDataSource,
        userLocalDataSource: UserLocalDataSource,
        accountLocalDataSource: AccountLocalDataSource,
        categoryLocalDataSource: CategoryLocalDataSource
    ): TransactionRepository =
        TransactionRepositoryImpl(
            transactionLocalDataSource,
            transactionRemoteDataSource,
            userLocalDataSource,
            accountLocalDataSource,
            categoryLocalDataSource
        )
}