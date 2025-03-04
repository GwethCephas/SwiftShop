package com.ceph.swiftshop.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.ceph.swiftshop.R
import com.ceph.swiftshop.domain.model.Product
import com.ceph.swiftshop.ui.theme.secondaryLight


@Composable
fun CartItem(
    product: Product,
    isAdded: Boolean,
    onAddedToCart: () -> Unit,
    quantity: Int,
    onQuantityChange: (Int) -> Unit
) {


    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .padding(10.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        )
    ) {
        Row(
            modifier = Modifier.fillMaxSize().clickable {  },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier.size(100.dp),
                contentAlignment = Alignment.Center
            ) {
                AsyncImage(
                    model = product.image,
                    contentDescription = "Added image",
                    contentScale = ContentScale.Crop
                )
            }

            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 10.dp),
            ) {
                Text(
                    text = product.title,
                    fontWeight = FontWeight.Normal,
                    fontSize = 14.sp,
                    maxLines = 2
                )
                Text(
                    text = "KES ${product.price}",
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 15.sp
                )
            }

            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                RemoveFromCart(isAdded = isAdded, onAddToCart = onAddedToCart)
                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconButton(onClick = { if (quantity > 1) onQuantityChange(quantity - 1) }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_minus),
                            contentDescription = "Remove items",
                            tint = secondaryLight
                        )
                    }
                    Text(text = quantity.toString())
                    IconButton(onClick = { onQuantityChange(quantity + 1) }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_add),
                            contentDescription = "Add items",
                            tint = secondaryLight
                        )
                    }
                }

            }
        }
    }
}
