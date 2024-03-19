package com.rxxskh.moneymanagerapp.presentation.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navArgument
import com.rxxskh.moneymanagerapp.presentation.screen.accounts.edit.AccountEditScreen
import com.rxxskh.moneymanagerapp.presentation.screen.accounts.main.AccountsScreen
import com.rxxskh.moneymanagerapp.presentation.screen.auth.login.LoginScreen
import com.rxxskh.moneymanagerapp.presentation.screen.auth.signup.SignupScreen
import com.rxxskh.moneymanagerapp.presentation.screen.categories.edit.CategoryEditScreen
import com.rxxskh.moneymanagerapp.presentation.screen.categories.main.CategoriesScreen
import com.rxxskh.moneymanagerapp.presentation.screen.expense.ExpenseScreen
import com.rxxskh.moneymanagerapp.presentation.screen.friends.FriendsScreen
import com.rxxskh.moneymanagerapp.presentation.screen.history.HistoryScreen
import com.rxxskh.moneymanagerapp.presentation.screen.income.IncomeScreen
import com.rxxskh.moneymanagerapp.presentation.screen.profile.ProfileScreen
import com.rxxskh.moneymanagerapp.presentation.screen.splash.SplashScreen
import com.rxxskh.moneymanagerapp.presentation.ui.theme.Background
import com.rxxskh.moneymanagerapp.presentation.ui.theme.DarkBlue
import com.rxxskh.moneymanagerapp.presentation.ui.theme.LightBlue
import com.rxxskh.moneymanagerapp.presentation.ui.theme.LightGreen
import com.rxxskh.moneymanagerapp.presentation.ui.theme.LightRed
import com.rxxskh.moneymanagerapp.presentation.ui.theme.SecondBackground
import com.rxxskh.moneymanagerapp.presentation.ui.theme.base2

@Composable
fun NavigationGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.LoginScreen.route
    ) {
        composable(
            route = Screen.SplashScreen.route
        ) {
            SplashScreen(navController = navController)
        }
        composable(
            route = Screen.LoginScreen.route
        ) {
            LoginScreen(navController = navController)
        }
        composable(
            route = Screen.SignupScreen.route
        ) {
            SignupScreen(navController = navController)
        }
        composable(
            route = Screen.IncomeScreen.route
        ) {
            IncomeScreen(navController = navController)
        }
        composable(
            route = Screen.ExpenseScreen.route
        ) {
            ExpenseScreen(navController = navController)
        }
        composable(
            route = Screen.AccountsScreen.route
        ) {
            AccountsScreen(navController = navController)
        }
        composable(
            route = Screen.FriendsScreen.route
        ) {
            FriendsScreen()
        }
        composable(
            route = Screen.ProfileScreen.route
        ) {
            ProfileScreen(navController = navController)
        }
        composable(
            route = Screen.CategoriesScreen.route,
            arguments = listOf(
                navArgument("categoryType") { type = NavType.StringType },
                navArgument("accountId") { type = NavType.StringType }
            )
        ) {
            CategoriesScreen(
                navController = navController,
                categoryType = it.arguments?.getString("categoryType")!!,
                accountId = it.arguments?.getString("accountId")!!
            )
        }
        composable(
            route = Screen.CategoryEditScreen.route,
            arguments = listOf(
                navArgument("categoryType") { type = NavType.StringType },
                navArgument("accountId") { type = NavType.StringType },
                navArgument("categoryId") { type = NavType.StringType },
            )
        ) {
            CategoryEditScreen(
                navController = navController,
                categoryType = it.arguments?.getString("categoryType")!!,
                accountId = it.arguments?.getString("accountId")!!,
                categoryId = it.arguments?.getString("categoryId")!!
            )
        }
        composable(
            route = Screen.HistoryScreen.route,
            arguments = listOf(
                navArgument("historyType") { type = NavType.StringType }
            )
        ) {
            HistoryScreen(
                navController = navController,
                historyType = it.arguments?.getString("historyType")!!
            )
        }
        composable(
            route = Screen.AccountEditScreen.route,
            arguments = listOf(navArgument("id") {
                type = NavType.StringType
            })
        ) {
            AccountEditScreen(
                navController = navController,
                accountId = it.arguments?.getString("id")!!
            )
        }
    }
}

@Composable
fun BottomNavigationBar(
    navController: NavController
) {
    val screens = listOf(
        Screen.IncomeScreen,
        Screen.ExpenseScreen,
        Screen.AccountsScreen,
        Screen.FriendsScreen,
        Screen.ProfileScreen
    )
    val backStackEntry = navController.currentBackStackEntryAsState()
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(70.dp)
            .background(SecondBackground)
    ) {
        NavigationBar(
            modifier = Modifier
                .padding(top = 1.dp)
                .fillMaxSize(),
            containerColor = Background,
            contentColor = DarkBlue,
        ) {
            Row(
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                screens.forEach { screen ->
                    val selected = screen.route == backStackEntry.value?.destination?.route
                    Box(
                        modifier = Modifier
                            .height(50.dp)
                            .weight(1f)
                            .clickable(
                                interactionSource = MutableInteractionSource(),
                                indication = null,
                                onClick = {
                                    navController.navigate(screen.route)
                                }
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.SpaceBetween
                        ) {
                            Icon(
                                painter = painterResource(id = screen.icon!!),
                                contentDescription = screen.route,
                                modifier = Modifier.size(24.dp),
                                tint = if (selected) {
                                    when (screen) {
                                        Screen.IncomeScreen -> LightGreen
                                        Screen.ExpenseScreen -> LightRed
                                        else -> LightBlue
                                    }
                                } else DarkBlue,
                            )
                            Text(
                                text = screen.label!!,
                                color = if (selected) {
                                    when (screen) {
                                        Screen.IncomeScreen -> LightGreen
                                        Screen.ExpenseScreen -> LightRed
                                        else -> LightBlue
                                    }
                                } else DarkBlue,
                                style = MaterialTheme.typography.base2
                            )
                        }
                    }
                }
            }
        }
    }
}