package com.rxxskh.moneymanagerapp.common

import com.rxxskh.domain.account.model.Account
import com.rxxskh.domain.category.model.Category
import com.rxxskh.domain.user.model.User
import com.rxxskh.moneymanagerapp.R

object TestValues {
    val categories = listOf(
        Category(
            categoryName = "Уборка",
            categoryIcon = R.drawable.cat_cleaning,
            categoryColor = 0
        ),
        Category(
            categoryName = "Продукты",
            categoryIcon = R.drawable.cat_hamburger,
            categoryColor = 1
        ),
        Category(
            categoryName = "Отдых",
            categoryIcon = R.drawable.cat_bed,
            categoryColor = 2
        ),
        Category(
            categoryName = "Напитки",
            categoryIcon = R.drawable.cat_water,
            categoryColor = 3
        ),
        Category(
            categoryName = "Кино",
            categoryIcon = R.drawable.cat_computer,
            categoryColor = 4
        ),
    )

    val accounts = listOf(
        Account(
            accountName = "Основной1",
            accountBalance = 150000,
        ),
        Account(
            accountName = "Основной2",
            accountBalance = 150000,
        ),
        Account(accountName = "Бизнес1", accountBalance = 200000),
        Account(accountName = "Бизнес2", accountBalance = 20000),
        Account(accountName = "Бизнес3", accountBalance = 220000),
        Account(accountName = "Запасной1", accountBalance = 50500),
        Account(accountName = "Запасной2", accountBalance = 55000),
        Account(accountName = "Запасной3", accountBalance = 50600),
        Account(accountName = "Запасной4", accountBalance = 500000),
    )

    val friends = listOf(
        User(
            userId = "deiii31332i31",
            userLogin = "zonik",
            userPassword = "123"
        ),
        User(
            userId = "kfdfskflew",
            userLogin = "kliktak",
            userPassword = "123"
        ),
        User(
            userId = "fdsfk32lkl2f",
            userLogin = "quinn",
            userPassword = "123"
        ),
        User(
            userId = "dadkladlalk",
            userLogin = "zonik1",
            userPassword = "123"
        ),
        User(
            userId = "kfdfskdakldaflew",
            userLogin = "kliktak1",
            userPassword = "123"
        ),
        User(
            userId = "fddaklsdsfk32lkl2f",
            userLogin = "quinn1",
            userPassword = "123"
        ),
        User(
            userId = "fdaffafafa",
            userLogin = "zonik2",
            userPassword = "123"
        ),
        User(
            userId = "fdafadfadfadf",
            userLogin = "kliktak2",
            userPassword = "123"
        ),
        User(
            userId = "fdafaf3141fd",
            userLogin = "quinn2",
            userPassword = "123"
        )
    )

    val authUser = User(
        userId = "-NsB--OtzG69fUwbEqMX",
        userLogin = "kliktak",
        userPassword = "123"
    )

    val passedAccount = Account(
        accountId = "-NsP1MhaV3JJtEajbtJq",
        accountName = "hgh",
        accountBalance = 20000,
    )
}