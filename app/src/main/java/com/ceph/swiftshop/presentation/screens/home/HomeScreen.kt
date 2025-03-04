package com.ceph.swiftshop.presentation.screens.home

import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.paging.compose.LazyPagingItems
import com.ceph.swiftshop.domain.model.Product
import com.ceph.swiftshop.presentation.components.DrawerSheet
import com.ceph.swiftshop.presentation.components.ProductItem
import com.ceph.swiftshop.presentation.components.ProductsVerticalGrid
import com.ceph.swiftshop.presentation.navigation.Routes
import com.ceph.swiftshop.ui.theme.onPrimaryLight
import com.ceph.swiftshop.ui.theme.secondaryLight


@Composable
fun HomeScreen(
    products: LazyPagingItems<Product>,
    featuredProducts: LazyPagingItems<Product>,
    navController: NavHostController,
    productsIds: List<Int>,
    onAddToCart: (Product) -> Unit,
    paddingValues: PaddingValues,
    gridState: LazyGridState

) {


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
    ) {

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            text = "Featured",
            fontWeight = FontWeight.SemiBold,
            fontSize = 22.sp
        )



        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp),
            contentPadding = PaddingValues(5.dp),
            flingBehavior = rememberSnapFlingBehavior(lazyListState = rememberLazyListState()),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly

        ) {
            items(
                featuredProducts.itemCount
            ) { index ->

                featuredProducts[index]?.let { product ->
                    var isBottomSheetOpen by remember { mutableStateOf(false) }

                    ProductItem(
                        product = product,
                        productsIds = productsIds,
                        isAdded = productsIds.contains(product.id),
                        onAddToCart = { onAddToCart(product) }
                    )
                    if (isBottomSheetOpen) {
                        DrawerSheet(
                            onDismissRequest = { isBottomSheetOpen = false },
                            product = product,
                            productsIds = productsIds,
                            onAddToCart = { onAddToCart(product) }
                        )
                    }

                }
            }


        }





        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Popular Products",
                fontWeight = FontWeight.SemiBold,
                fontSize = 22.sp,
            )

            TextButton(
                onClick = {
                    navController.navigate(Routes.Shop.route) {
                        popUpTo(Routes.Home.route) { inclusive = true }
                    }
                }
            ) {
                Text(
                    text = "See All",
                    fontWeight = FontWeight.Normal,
                    fontSize = 15.sp

                )
            }


        }

        if (products.itemCount == 0) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(
                    color = secondaryLight
                )
            }
        } else {

            ProductsVerticalGrid(
                products = products,
                onAddToCart = onAddToCart,
                productsIds = productsIds,
                gridState = gridState
            )
        }


    }


}


