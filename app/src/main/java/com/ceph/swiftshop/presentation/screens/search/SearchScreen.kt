package com.ceph.swiftshop.presentation.screens.search

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.paging.compose.LazyPagingItems
import com.ceph.swiftshop.domain.model.Product
import com.ceph.swiftshop.presentation.components.ProductsVerticalGrid
import com.ceph.swiftshop.ui.theme.onPrimaryLight
import com.ceph.swiftshop.ui.theme.secondaryLight

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    searchedProducts: LazyPagingItems<Product>,
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    onSearch: (String) -> Unit,
    navController: NavHostController,
    onAddToCart: (Product) -> Unit,
    productsIds: List<Int>,
    gridState: LazyGridState
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.statusBars),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        val keyBoardController = LocalSoftwareKeyboardController.current

        SearchBar(
            modifier = Modifier
                .padding(horizontal = 20.dp, vertical = 10.dp),
            query = searchQuery,
            onQueryChange = { onSearchQueryChange(it) },
            onSearch = {
                onSearch(searchQuery)
                keyBoardController?.hide()
            },
            leadingIcon = {
                IconButton(
                    onClick = {  },
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
                    onClick = { if (searchQuery.isNotEmpty()) onSearchQueryChange("") else navController.popBackStack() },
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

        ProductsVerticalGrid(
            products = searchedProducts,
            productsIds = productsIds,
            onAddToCart = onAddToCart,
            gridState = gridState
        )
    }

}