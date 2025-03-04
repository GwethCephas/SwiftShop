package com.ceph.swiftshop.presentation.screens.shop.cart

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.paging.compose.LazyPagingItems
import com.ceph.swiftshop.domain.model.Product
import com.ceph.swiftshop.presentation.components.CartItem
import com.ceph.swiftshop.presentation.navigation.Routes
import com.ceph.swiftshop.ui.theme.onPrimaryLight
import com.ceph.swiftshop.ui.theme.secondaryLight


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartScreen(
    addedProducts: LazyPagingItems<Product>,
    productsIds: List<Int>,
    itemQuantities: Map<Int, Int>,
    totalItems: Int,
    totalPrice: Double,
    onAddedToCart: (Product) -> Unit,
    onQuantityChange: (Int, Int) -> Unit,
    navController: NavHostController,
    paddingValues: PaddingValues,
    scrollBehavior: TopAppBarScrollBehavior
) {


    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
    ) {

        if (addedProducts.itemCount == 0) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(
                    text = "No items added in the cart \n Added items will appear here",
                    fontWeight = FontWeight.Normal,
                    fontSize = 20.sp
                )
            }
        } else {

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(10.dp)
                    .nestedScroll(scrollBehavior.nestedScrollConnection),
            ) {
                items(addedProducts.itemCount) { index ->
                    addedProducts[index]?.let { product ->
                        val quantity= itemQuantities[product.id] ?: 1
                        CartItem(
                            product = product,
                            isAdded = productsIds.contains(product.id),
                            onAddedToCart = { onAddedToCart(product) },
                            quantity = quantity,
                            onQuantityChange = { newQuantity ->
                                onQuantityChange(product.id, newQuantity)

                            }
                        )
                    }
                }
            }
        }

        if (addedProducts.itemCount > 0) {
            Button(
                onClick = {
                    navController.navigate(
                        Routes.Checkout.route + "?totalItems=${totalItems}&totalPrice=${
                            "%.2f".format(
                                totalPrice
                            )
                        }"
                    ) {
                        popUpTo(Routes.Cart.route) {
                            inclusive = true
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .padding(10.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = secondaryLight,
                    contentColor = onPrimaryLight
                )
            ) {
                Text(text = "Check  out")
            }
        }

    }
}


