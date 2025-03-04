package com.ceph.swiftshop.presentation.components

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.FilledIconToggleButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.ceph.swiftshop.ui.theme.secondaryLight

@Composable
fun RemoveFromCart(
    isAdded: Boolean,
    onAddToCart: () -> Unit
) {

    FilledIconToggleButton(
        checked = isAdded,
        onCheckedChange = { onAddToCart() },
        colors = IconButtonDefaults.iconToggleButtonColors(
            containerColor = Color.Transparent

        )
    ) {
        Icon(
            modifier = Modifier.size(20.dp),
            imageVector = Icons.Filled.Delete,
            contentDescription = "Added to cart",
            tint = secondaryLight
        )

    }

}