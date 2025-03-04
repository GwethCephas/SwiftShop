package com.ceph.swiftshop.presentation.components


import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.ceph.swiftshop.presentation.navigation.Routes
import com.ceph.swiftshop.ui.theme.onPrimaryLight
import com.ceph.swiftshop.ui.theme.secondaryLight

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeTopAppBar(
    navController: NavHostController
) {
    TopAppBar(
        title = {

            Text(
                text = "SwiftShop",
                fontWeight = FontWeight.SemiBold,
                fontSize = 25.sp

            )

        },
        actions = {
            IconButton(onClick = { navController.navigate(Routes.Search.route) }) {
                Icon(imageVector = Icons.Default.Search, contentDescription = "Search")
            }
        },

        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = secondaryLight,
            titleContentColor = onPrimaryLight,
            actionIconContentColor = onPrimaryLight
        )
    )
}

