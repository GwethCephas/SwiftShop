package com.ceph.swiftshop.presentation.components

import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import com.ceph.swiftshop.domain.model.Product

@Composable
fun ProductsVerticalGrid(
    products: LazyPagingItems<Product>,
    productsIds: List<Int>,
    onAddToCart: (Product) -> Unit,
    gridState: LazyGridState
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier
            .fillMaxWidth(),
        contentPadding = PaddingValues(10.dp),
        flingBehavior = rememberSnapFlingBehavior(lazyListState = rememberLazyListState()),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalArrangement = Arrangement.SpaceEvenly,
        state = gridState
    ) {
        items(products.itemCount) { index ->
            products[index]?.let { product ->
                ProductItem(
                    product = product,
                    isAdded = productsIds.contains(product.id),
                    onAddToCart = { onAddToCart(product) },
                    productsIds = productsIds

                )
            }


        }
    }
}