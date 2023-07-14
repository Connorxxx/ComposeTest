package com.zckj.compose.ui

import androidx.annotation.StringRes
import com.zckj.compose.R

sealed class Screen(val route: String, @StringRes val resourceId: Int) {
    object Home : Screen("home", R.string.home)
    object Profile : Screen("profile", R.string.profile)
    object Dashboard : Screen("dashboard", R.string.dashboard)
    object Author : Screen("author", R.string.author)
    object LazyList : Screen("lazyList", R.string.lazy_list)
}
