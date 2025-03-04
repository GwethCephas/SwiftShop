package com.ceph.swiftshop.presentation.screens.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.ceph.swiftshop.presentation.navigation.Routes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    paddingValues: PaddingValues,
    navController: NavHostController
) {

    CenterAlignedTopAppBar(
        title = { Text(text = "Settings") },
        navigationIcon = {
            IconButton(
                onClick = {
                    navController.navigate(Routes.Profile.route)
                }
            ) {
                Icon(imageVector = Icons.Default.KeyboardArrowLeft, contentDescription = "Back")
            }
        }
    )
    Box(modifier = Modifier.padding(paddingValues)) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 50.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CustomRows(
                icon = Icons.Default.Notifications,
                text = "Notifications",
                onClick = {

                }
            )
            CustomRows(
                icon = Icons.Default.LocationOn,
                text = "Location",
                onClick = {}
            )
            CustomRows(
                icon = Icons.Default.Call,
                text = "Help center",
                onClick = {}
            )
            CustomRows(
                icon = Icons.Default.Info,
                text = "About us",
                onClick = {

                }
            )
        }
    }
}