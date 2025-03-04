package com.ceph.swiftshop.presentation.components

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.ceph.swiftshop.ui.theme.onPrimaryLight
import com.ceph.swiftshop.ui.theme.secondaryLight


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartTopAppBar(
    scrollBehavior: TopAppBarScrollBehavior
) {
    TopAppBar(
        scrollBehavior = scrollBehavior,
        title = {
            Text(
                text = "Cart",
                fontWeight = FontWeight.SemiBold,
                fontSize = 20.sp,
                color = onPrimaryLight
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = secondaryLight
        )
    )
}
