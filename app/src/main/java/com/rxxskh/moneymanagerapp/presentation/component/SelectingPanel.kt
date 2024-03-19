package com.rxxskh.moneymanagerapp.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.rxxskh.domain.account.model.Account
import com.rxxskh.domain.category.model.Category
import com.rxxskh.moneymanagerapp.R
import com.rxxskh.moneymanagerapp.common.Constants
import com.rxxskh.moneymanagerapp.common.toRussianCurrency
import com.rxxskh.moneymanagerapp.presentation.ui.theme.DarkBlue
import com.rxxskh.moneymanagerapp.presentation.ui.theme.DarkGray
import com.rxxskh.moneymanagerapp.presentation.ui.theme.LightBlue
import com.rxxskh.moneymanagerapp.presentation.ui.theme.LightGray
import com.rxxskh.moneymanagerapp.presentation.ui.theme.SecondBackground
import com.rxxskh.moneymanagerapp.presentation.ui.theme.base1
import com.rxxskh.moneymanagerapp.presentation.ui.theme.base2
import com.rxxskh.moneymanagerapp.presentation.ui.theme.title2
import com.rxxskh.moneymanagerapp.presentation.ui.theme.title3


@Composable
fun SelectingPanel(
    modifier: Modifier = Modifier,
    selectedCategory: Category?,
    categories: List<Category>,
    selectedAccount: Account?,
    onNextAccountClick: () -> Unit = { },
    onPreviousAccountClick: () -> Unit = { },
    onCategoryClick: (Category) -> Unit = { },
    onEditCategoryClick: (String?) -> Unit = {}
) {
    if (selectedAccount != null) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .background(
                    color = SecondBackground,
                    shape = RoundedCornerShape(8.dp)
                ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            SelectAccountBar(
                modifier = Modifier.padding(top = 12.dp),
                account = selectedAccount,
                onNextAccountClick = onNextAccountClick,
                onPreviousAccountClick = onPreviousAccountClick,
            )
            SelectCategoryBar(
                modifier = Modifier.padding(start = 16.dp, top = 20.dp, end = 16.dp),
                selectedCategory = selectedCategory,
                categories = categories,
                onCategoryClick = onCategoryClick
            )
            Text(
                text = stringResource(id = R.string.edit_category),
                modifier = Modifier
                    .padding(top = 16.dp, bottom = 12.dp)
                    .clickable(
                        interactionSource = MutableInteractionSource(),
                        indication = null,
                        onClick = { onEditCategoryClick(if (selectedAccount.accountMembers.isEmpty()) null else selectedAccount.accountId) }
                    ),
                color = DarkGray,
                style = MaterialTheme.typography.base2
            )
        }
    }
}

@Composable
fun SelectAccountBar(
    modifier: Modifier = Modifier,
    account: Account,
    onNextAccountClick: () -> Unit,
    onPreviousAccountClick: () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_arrow_right),
            contentDescription = null,
            modifier = Modifier
                .padding(end = 12.dp)
                .size(20.dp)
                .scale(scaleX = -1f, scaleY = 1f)
                .clickable(
                    interactionSource = MutableInteractionSource(),
                    indication = null,
                    onClick = { onNextAccountClick() }
                ),
            tint = LightGray
        )
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = account.accountName,
                modifier = Modifier
                    .width(200.dp),
                color = if (account.accountMembers.isEmpty()) LightGray else LightBlue,
                textAlign = TextAlign.Center,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
                style = MaterialTheme.typography.base1
            )
            Text(
                text = account.accountBalance.toRussianCurrency(),
                color = Color.White,
                style = MaterialTheme.typography.title2
            )
        }
        Icon(
            painter = painterResource(id = R.drawable.ic_arrow_right),
            contentDescription = null,
            modifier = Modifier
                .padding(start = 12.dp)
                .size(20.dp)
                .clickable(
                    interactionSource = MutableInteractionSource(),
                    indication = null,
                    onClick = { onPreviousAccountClick() }
                ),
            tint = LightGray
        )
    }
}

@Composable
fun SelectCategoryBar(
    modifier: Modifier = Modifier,
    selectedCategory: Category?,
    categories: List<Category>,
    onCategoryClick: (Category) -> Unit
) {
    if (categories.isEmpty()) {
        Text(
            text = stringResource(id = R.string.category_not_found),
            modifier = modifier,
            color = DarkGray,
            style = MaterialTheme.typography.title3
        )
    } else {
        LazyRow(
            modifier = modifier.fillMaxWidth()
        ) {
            items(items = categories) { category ->
                CategoryItem(
                    modifier = Modifier.padding(end = if (categories.indexOf(category) != categories.size - 1) 12.dp else 0.dp),
                    isSelected = category == selectedCategory,
                    category = category,
                    onCategoryClick = onCategoryClick
                )
            }
        }
    }
}

@Composable
fun CategoryItem(
    modifier: Modifier = Modifier,
    isSelected: Boolean = false,
    category: Category,
    onCategoryClick: (Category) -> Unit
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Icon(
            painter = painterResource(id = category.categoryIcon),
            contentDescription = null,
            modifier = Modifier
                .size(54.dp)
                .background(
                    color = if (isSelected) LightBlue else DarkBlue,
                    shape = RoundedCornerShape(8.dp)
                )
                .padding(4.dp)
                .background(
                    color = if (isSelected) DarkBlue else SecondBackground,
                    shape = RoundedCornerShape(8.dp)
                )
                .padding(8.dp)
                .clickable(
                    interactionSource = MutableInteractionSource(),
                    indication = null,
                    onClick = { onCategoryClick(category) }
                ),
            tint = Constants.COLORS[category.categoryColor]
        )
        Text(
            text = category.categoryName,
            modifier = Modifier
                .padding(top = 4.dp)
                .width(70.dp),
            color = Color.LightGray,
            textAlign = TextAlign.Center,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1,
            style = MaterialTheme.typography.base2
        )
    }
}