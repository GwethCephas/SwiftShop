package com.ceph.swiftshop.presentation.components

import androidx.compose.foundation.layout.height
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.ceph.swiftshop.presentation.navigation.Routes
import com.ceph.swiftshop.ui.theme.onPrimaryLight
import com.ceph.swiftshop.ui.theme.secondaryLight

@Composable
fun BottomBarItem(
    navController: NavHostController
) {

    val bottomBarItems = listOf(
        Routes.Home,
        Routes.Shop,
        Routes.Cart,
        Routes.Profile
    )

    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route



    NavigationBar(
        modifier = Modifier.height(105.dp)
    ) {
        bottomBarItems.forEachIndexed { _, routes ->
            NavigationBarItem(
                selected = currentRoute == routes.route,
                onClick = {
                    if (currentRoute != routes.route) {
                        navController.navigate(routes.route) {
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                },
                icon = {
                    (if (currentRoute == routes.route) routes.selectedIcon
                    else routes.unselectedIcon)?.let {
                        Icon(
                            imageVector = it,
                            contentDescription = "Icons",
                            tint = if (currentRoute == routes.route) onPrimaryLight else Color.Black
                        )
                    }

                },
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = secondaryLight
                )
            )
        }
    }
}