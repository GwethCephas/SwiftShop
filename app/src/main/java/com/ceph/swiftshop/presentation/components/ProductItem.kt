package com.ceph.swiftshop.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledIconToggleButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.ceph.swiftshop.domain.model.Product
import com.ceph.swiftshop.ui.theme.onPrimaryLight
import com.ceph.swiftshop.ui.theme.secondaryLight

@Composable
fun ProductItem(
    product: Product,
    productsIds: List<Int>,
    isAdded: Boolean,
    onAddToCart: () -> Unit
) {

    val isBottomSheetOpen = remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth(0.5f)
            .width(170.dp)
            .height(210.dp)
            .padding(5.dp)
            .clickable { isBottomSheetOpen.value = true },
        elevation = CardDefaults.cardElevation(
            defaultElevation = 10.dp
        ),
        colors = CardDefaults.cardColors(
            containerColor = onPrimaryLight
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AsyncImage(

                model = product.image,
                contentDescription = "Product Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .height(120.dp)
            )
            Text(
                text = product.title,
                maxLines = 1,
                modifier = Modifier.padding(5.dp)
            )


            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Text(
                    text = "KES ${product.price}",
                    modifier = Modifier.padding(5.dp)
                )

                AddToCartButton(
                    isAdded = isAdded,
                    onAddToCart = onAddToCart
                )

            }


        }


    }
    if (isBottomSheetOpen.value) {
        DrawerSheet(
            onDismissRequest = { isBottomSheetOpen.value = false },
            product = product,
            productsIds = productsIds,
            onAddToCart = onAddToCart
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DrawerSheet(
    onDismissRequest: () -> Unit,
    product: Product,
    productsIds: List<Int>,
    onAddToCart: () -> Unit

) {

    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    ModalBottomSheet(
        onDismissRequest = onDismissRequest,
        sheetState = sheetState,
        containerColor = onPrimaryLight
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            ) {
                AsyncImage(
                    model = product.image,
                    contentDescription = "Product Image",
                    contentScale = ContentScale.Crop,
                )

            }

            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                Text(
                    text = product.title,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 18.sp
                )
                Text(
                    text = product.category,
                    fontSize = 12.sp

                )
                Text(
                    text = "KES ${product.price}",
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 18.sp
                )
                Text(
                    text = "Description",
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 18.sp,

                    )
                Text(text = product.description)
            }

            Button(
                onClick = onAddToCart,
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (productsIds.contains(product.id)) Color.LightGray
                    else secondaryLight,
                    contentColor = if (productsIds.contains(product.id)) Color.White
                    else onPrimaryLight
                ),
                modifier = Modifier.fillMaxWidth().padding(20.dp)
            ) {
                if (productsIds.contains(product.id)) {
                    Text(text = "Remove from cart")
                } else {
                    Text(text = "Add to cart")
                }
            }


        }

    }
}

@Composable
fun AddToCartButton(
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
        if (isAdded) {
            Icon(
                modifier = Modifier.size(20.dp),
                imageVector = Icons.Filled.ShoppingCart,
                contentDescription = "Added to cart",
                tint = secondaryLight
            )
        } else {
            Icon(
                modifier = Modifier.size(20.dp),
                imageVector = Icons.Outlined.ShoppingCart,
                contentDescription = "Remove from cart",
                tint = Color.Black
            )
        }

    }

}