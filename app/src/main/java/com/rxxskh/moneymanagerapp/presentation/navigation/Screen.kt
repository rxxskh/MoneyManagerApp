package com.rxxskh.moneymanagerapp.presentation.navigation

import com.rxxskh.moneymanagerapp.R
import com.rxxskh.moneymanagerapp.common.Constants

sealed class Screen(val label: String?, val icon: Int?, val route: String) {
    object SplashScreen : Screen(label = null, icon = null, "splash")
    object LoginScreen : Screen(label = null, icon = null, "login")
    object SignupScreen : Screen(label = null, icon = null, "signup")
    object IncomeScreen : Screen(label = "Доходы", icon = R.drawable.ic_bm_income, "income")
    object ExpenseScreen : Screen(label = "Расходы", icon = R.drawable.ic_bm_expense, "expense")
    object AccountsScreen : Screen(label = "Счета", icon = R.drawable.ic_bm_accounts, "accounts")
    object FriendsScreen : Screen(label = "Друзья", icon = R.drawable.ic_person, "friends")
    object ProfileScreen : Screen(label = "Профиль", icon = R.drawable.ic_history, "profile")
    object CategoriesScreen : Screen(label = null, icon = null, "categories/{categoryType}/{accountId}") {
        fun passIds(categoryType: String, accountId: String?): String {
            return "categories/${categoryType}/${accountId ?: Constants.SHARED_ACCOUNT_TAG}"
        }
    }
    object CategoryEditScreen :
        Screen(label = null, icon = null, "categories/{categoryType}/{accountId}/{categoryId}") {
        fun passIds(accountId: String?, categoryId: String?, categoryType: String): String {
            return "categories/${categoryType}/${accountId ?: Constants.SHARED_ACCOUNT_TAG}/${categoryId ?: Constants.NEW_TAG}"
        }
    }
    object HistoryScreen : Screen(label = null, icon = null, "history/{historyType}") {
        fun passHistoryType(historyType: String): String {
            return "history/${historyType}"
        }
    }
    object AccountEditScreen : Screen(label = null, icon = null, route = "accounts/{id}") {
        fun passId(id: String): String {
            return "accounts/${id}"
        }
    }
}