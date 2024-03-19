package com.rxxskh.moneymanagerapp.presentation.screen.categories.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.rxxskh.domain.transaction.model.OperationType
import com.rxxskh.moneymanagerapp.R
import com.rxxskh.moneymanagerapp.presentation.component.AppTopBar
import com.rxxskh.moneymanagerapp.presentation.component.CategoryItem
import com.rxxskh.moneymanagerapp.presentation.navigation.Screen
import com.rxxskh.moneymanagerapp.presentation.ui.theme.Background

@Composable
fun CategoriesScreen(
    vm: CategoriesViewModel = hiltViewModel(),
    navController: NavController,
    categoryType: String,
    accountId: String
) {
    vm.passCategoryType(categoryType)
    vm.passAccountId(accountId)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Background)
    ) {
        AppTopBar(
            text = stringResource(id = R.string.categories_title),
            leftButtonIconId = R.drawable.ic_arrow_back,
            onLeftButtonClick = { navController.navigate(if (vm.passedCategoryType == OperationType.INCOME) Screen.IncomeScreen.route else Screen.ExpenseScreen.route) },
            rightButtonIconId = R.drawable.ic_plus,
            onRightButtonClick = {
                navController.navigate(
                    Screen.CategoryEditScreen.passIds(
                        categoryType = vm.passedCategoryType!!.name,
                        accountId = accountId,
                        categoryId = null
                    )
                )
            }
        )
        LazyVerticalGrid(
            columns = GridCells.Fixed(5),
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(items = vm.categories) { category ->
                CategoryItem(
                    category = category,
                    onCategoryClick = {
                        navController.navigate(
                            Screen.CategoryEditScreen.passIds(
                                categoryType = vm.passedCategoryType!!.name,
                                accountId = accountId,
                                categoryId = it.categoryId
                            )
                        )
                    }
                )
            }
        }
    }
}