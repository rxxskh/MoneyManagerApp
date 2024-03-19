package com.rxxskh.utils

import com.google.firebase.database.FirebaseDatabase

object FirebaseReferencesProvider {

    val USERS_REF = FirebaseDatabase.getInstance().getReference("users")
    val USER_FRIENDS_REF = FirebaseDatabase.getInstance().getReference("user_friends")

    val ACCOUNTS_REF = FirebaseDatabase.getInstance().getReference("accounts")
    val ACCOUNT_MEMBERS_REF = FirebaseDatabase.getInstance().getReference("account_members")

    val CATEGORIES_REF = FirebaseDatabase.getInstance().getReference("categories")
    val CATEGORY_MEMBERS_REF = FirebaseDatabase.getInstance().getReference("category_members")

    val TRANSACTION_REF = FirebaseDatabase.getInstance().getReference("transactions")
}