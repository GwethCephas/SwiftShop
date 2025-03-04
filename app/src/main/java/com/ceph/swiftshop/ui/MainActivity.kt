package com.ceph.swiftshop.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.rememberNavController
import androidx.paging.compose.collectAsLazyPagingItems
import com.ceph.swiftshop.presentation.authentication.GoogleAuthClient
import com.ceph.swiftshop.presentation.screens.home.HomeViewModel
import com.ceph.swiftshop.presentation.navigation.NavHostSetUp
import com.ceph.swiftshop.ui.theme.SwiftShopTheme
import org.koin.androidx.compose.koinViewModel

class MainActivity : ComponentActivity() {


    private val googleAuthClient by lazy {
        GoogleAuthClient(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SwiftShopTheme {

                val homeViewModel = koinViewModel<HomeViewModel>()
                val products = homeViewModel.products.collectAsLazyPagingItems()

                var searchQuery by rememberSaveable { mutableStateOf("") }

                val navController = rememberNavController()

                NavHostSetUp(
                    navController = navController,
                    products = products,
                    searchQuery = searchQuery,
                    onSearchQueryChange = {
                        searchQuery = it
                    },
                    homeViewModel = homeViewModel,
                    googleAuthClient = googleAuthClient
                )

            }
        }
    }
}
