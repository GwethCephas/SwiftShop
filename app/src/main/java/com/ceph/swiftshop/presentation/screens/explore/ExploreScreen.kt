package com.ceph.swiftshop.presentation.screens.explore

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.SuggestionChipDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.ceph.swiftshop.domain.model.Product
import com.ceph.swiftshop.presentation.components.ProductsVerticalGrid
import com.ceph.swiftshop.ui.theme.onPrimaryLight
import com.ceph.swiftshop.ui.theme.outlineLight
import com.ceph.swiftshop.ui.theme.secondaryLight
import com.ceph.swiftshop.data.utils.Categories

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExploreScreen(
    products: LazyPagingItems<Product>,
    searchedProducts: LazyPagingItems<Product>,
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    onSearch: (String) -> Unit,
    navController: NavHostController,
    shopViewModel: ShopViewModel,
    productsIds: List<Int>,
    onAddToCart: (Product) -> Unit,
    gridState: LazyGridState
) {

    val categoryList = listOf(
        Categories("electronics"),
        Categories("jewelery"),
        Categories("men's clothing"),
        Categories("women's clothing"),
    )
    val keyBoardController = LocalSoftwareKeyboardController.current
    val categories = remember { shopViewModel.categoryProducts }.collectAsLazyPagingItems()


    Column(
        modifier = Modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.statusBars),
    ) {


        SearchBar(
            modifier = Modifier.padding(10.dp),
            query = searchQuery,
            onQueryChange = { onSearchQueryChange(it) },
            onSearch = {
                onSearch(searchQuery)
                keyBoardController?.hide()
            },
            leadingIcon = {
                IconButton(
                    onClick = { },
                    modifier = Modifier
                        .padding(top = 4.dp)
                        .size(25.dp),
                    colors = IconButtonDefaults.iconButtonColors(
                        containerColor = secondaryLight
                    )
                ) {
                    Icon(
                        modifier = Modifier.size(16.dp),
                        imageVector = Icons.Default.Search,
                        contentDescription = "Add to cart",
                        tint = onPrimaryLight
                    )
                }
            },
            trailingIcon = {
                IconButton(
                    onClick = {
                        if (searchQuery.isNotEmpty()) onSearchQueryChange("")
                        else navController.popBackStack()
                    },
                    modifier = Modifier
                        .padding(top = 4.dp)
                        .size(25.dp),
                    colors = IconButtonDefaults.iconButtonColors(
                        containerColor = secondaryLight
                    )
                ) {
                    Icon(
                        modifier = Modifier.size(16.dp),
                        imageVector = Icons.Default.Close,
                        contentDescription = "Add to cart",
                        tint = onPrimaryLight
                    )
                }
            },
            active = false,
            onActiveChange = {},
            content = {},
            placeholder = { Text(text = "Search...") }
        )

        LazyRow(
            modifier = Modifier.padding(10.dp),
            horizontalArrangement = Arrangement.spacedBy(5.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            items(categoryList.size) {
                SuggestionChip(
                    onClick = {
                        shopViewModel.getProductsByCategory(categoryList[it].category)
                    },
                    label = { Text(text = categoryList[it].category) },
                    colors = SuggestionChipDefaults.suggestionChipColors(
                        containerColor = if (
                            categoryList[it].category == categories.itemSnapshotList.items.firstOrNull()?.category) secondaryLight
                        else outlineLight,
                        labelColor = Color.White,

                        ),
                    modifier = Modifier.clip(RoundedCornerShape(20.dp))

                )
            }
        }



        if (searchQuery.isNotEmpty()) {
            ProductsVerticalGrid(
                products = searchedProducts,
                onAddToCart = onAddToCart,
                productsIds = productsIds,
                gridState = gridState
            )
        } else if (
            categories.itemCount != 0
        ) {
            ProductsVerticalGrid(
                products = categories,
                onAddToCart = onAddToCart,
                productsIds = productsIds,
                gridState = gridState

            )
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
