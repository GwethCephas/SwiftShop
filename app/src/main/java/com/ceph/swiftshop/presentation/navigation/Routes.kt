package com.ceph.swiftshop.presentation.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.outlined.AddCircle
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Routes(
    val title: String,
    val selectedIcon: ImageVector?,
    val unselectedIcon:ImageVector?,
    val route: String
) {

    data object Home:Routes(
        title = "Home",
        selectedIcon = Icons.Default.Home,
        unselectedIcon = Icons.Default.Home,
        route = "home"
    )
    data object Search:Routes(
        title = "Search",
        selectedIcon = Icons.Default.Search,
        unselectedIcon = Icons.Default.Search,
        route = "search"
    )
    data object Checkout:Routes(
        title = "Checkout",
        selectedIcon = null,
        unselectedIcon = null,
        route = "checkout"
    )
    data object Shop:Routes(
        title = "Shop",
        selectedIcon = Icons.Filled.AddCircle,
        unselectedIcon = Icons.Outlined.AddCircle,
        route = "shop"
    )
    data object Profile:Routes(
        title = "Person",
        selectedIcon = Icons.Filled.Person,
        unselectedIcon = Icons.Filled.Person,
        route = "person"
    )
    data object Cart:Routes(
        title = "Cart",
        selectedIcon = Icons.Filled.ShoppingCart,
        unselectedIcon = Icons.Outlined.ShoppingCart,
        route = "Cart"
    )
    data object Address: Routes (
        title = "Address",
        selectedIcon = null,
        unselectedIcon = null,
        route = "address"
    )
    data object SignIn: Routes (
        title = "SignIn",
        selectedIcon = null,
        unselectedIcon = null,
        route = "signIn"
    )
    data object Settings: Routes (
        title = "Settings",
        selectedIcon = null,
        unselectedIcon = null,
        route = "settings"
    )
}