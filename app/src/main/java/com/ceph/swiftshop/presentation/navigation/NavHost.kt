package com.ceph.swiftshop.presentation.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navArgument
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.ceph.swiftshop.domain.model.Product
import com.ceph.swiftshop.presentation.authentication.GoogleAuthClient
import com.ceph.swiftshop.presentation.screens.signUp.SignInScreen
import com.ceph.swiftshop.presentation.screens.shop.cart.CartScreen
import com.ceph.swiftshop.presentation.screens.shop.cart.CartViewModel
import com.ceph.swiftshop.presentation.screens.shop.checkout.CheckoutScreen
import com.ceph.swiftshop.presentation.screens.explore.ExploreScreen
import com.ceph.swiftshop.presentation.components.BottomBarItem
import com.ceph.swiftshop.presentation.components.CartTopAppBar
import com.ceph.swiftshop.presentation.components.HomeTopAppBar
import com.ceph.swiftshop.presentation.screens.home.HomeScreen
import com.ceph.swiftshop.presentation.screens.home.HomeViewModel
import com.ceph.swiftshop.presentation.screens.profile.ProfileScreen
import com.ceph.swiftshop.presentation.screens.search.SearchScreen
import com.ceph.swiftshop.presentation.screens.search.SearchViewModel
import com.ceph.swiftshop.presentation.screens.explore.ShopViewModel
import com.ceph.swiftshop.presentation.screens.profile.SettingsScreen
import com.ceph.swiftshop.presentation.screens.shop.checkout.PaymentCompleteScreen
import com.ceph.swiftshop.presentation.screens.shop.checkout.PaymentViewModel
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavHostSetUp(
    navController: NavHostController,
    products: LazyPagingItems<Product>,
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    homeViewModel: HomeViewModel,
    googleAuthClient: GoogleAuthClient
) {

    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val searchViewModel = koinViewModel<SearchViewModel>()

    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route

//   BottomBar visibility logic
    var isBottomBarVisible by remember { mutableStateOf(true) }
    val gridScrollState = rememberLazyGridState()
    var lastScrollIndex by remember { mutableIntStateOf(0) }
    val firsVisibleItemIndex by remember { derivedStateOf { gridScrollState.firstVisibleItemIndex } }

    LaunchedEffect(key1 = firsVisibleItemIndex) {
        if (firsVisibleItemIndex < lastScrollIndex) {
            isBottomBarVisible = true
        } else if (firsVisibleItemIndex > lastScrollIndex) {
            isBottomBarVisible = false
        }

        lastScrollIndex = gridScrollState.firstVisibleItemIndex
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            when (currentRoute) {
                Routes.Home.route -> {
                    HomeTopAppBar(navController)
                }

                Routes.Cart.route -> {
                    CartTopAppBar(scrollBehavior = scrollBehavior)
                }



            }
        },
        bottomBar = {
            when (currentRoute) {
                Routes.Home.route -> {
                    BottomBarItem(navController = navController)
                }

                Routes.Shop.route -> {
                    BottomBarItem(navController = navController)
                }

                Routes.Cart.route -> {
                    BottomBarItem(navController = navController)
                }
                Routes.Profile.route -> {
                    BottomBarItem(navController = navController)
                }

            }

        }
    ) { paddingValues ->

        NavHost(
            navController = navController,
            startDestination = if (googleAuthClient.getCurrentUser() != null) Routes.Home.route else Routes.SignIn.route
        ) {
            composable(Routes.Home.route) {
                val productIds by homeViewModel.getAllAddedProductsIds.collectAsStateWithLifecycle()
                val featuredProducts = homeViewModel.featuredProducts.collectAsLazyPagingItems()
                HomeScreen(
                    products = products,
                    navController = navController,
                    productsIds = productIds,
                    onAddToCart = { product ->
                        homeViewModel.toggleProductAddedToCart(product)
                    },
                    featuredProducts = featuredProducts,
                    paddingValues = paddingValues,
                    gridState = gridScrollState
                )
            }
            composable(Routes.Shop.route) {
                val searchProducts = searchViewModel.searchedProducts.collectAsLazyPagingItems()
                val shopViewModel = koinViewModel<ShopViewModel>()
                val productsIds by shopViewModel.getAllAddedProductsIds.collectAsStateWithLifecycle()


                ExploreScreen(
                    products = products,
                    searchedProducts = searchProducts,
                    searchQuery = searchQuery,
                    onSearchQueryChange = onSearchQueryChange,
                    onSearch = { query ->
                        searchViewModel.searchProducts(query)
                    },
                    navController = navController,
                    shopViewModel = shopViewModel,
                    productsIds = productsIds,
                    onAddToCart = { product ->
                        shopViewModel.toggleProductAddedToCart(product)
                    },
                    gridState = gridScrollState

                )
            }
            composable(Routes.Cart.route) {
                val cartViewModel = koinViewModel<CartViewModel>()
                val addedProducts = cartViewModel.addedProducts.collectAsLazyPagingItems()
                val productsIds by cartViewModel.productIds.collectAsStateWithLifecycle()
                val itemQuantities by cartViewModel.itemQuantities.collectAsStateWithLifecycle()
                val totalItems by cartViewModel.totalItems.collectAsStateWithLifecycle()
                val totalPrice by cartViewModel.totalPrice.collectAsStateWithLifecycle()

                CartScreen(
                    addedProducts = addedProducts,
                    productsIds = productsIds,
                    itemQuantities = itemQuantities,
                    totalItems = totalItems,
                    totalPrice = totalPrice,
                    onAddedToCart = { product -> cartViewModel.toggleProductAddedToCart(product) },
                    onQuantityChange = { productId, quantity ->
                        cartViewModel.updateQuantities(productId, quantity)
                    },
                    navController = navController,
                    paddingValues = paddingValues,
                    scrollBehavior = scrollBehavior
                )
            }

            composable(Routes.Profile.route) {


                ProfileScreen(
                    navController = navController,
                    userData = googleAuthClient.getCurrentUser(),
                    paddingValues = paddingValues
                )
            }
            composable(Routes.Search.route) {
                val searchProducts = searchViewModel.searchedProducts.collectAsLazyPagingItems()
                val productsIds by searchViewModel.getAllAddedProductsIds.collectAsStateWithLifecycle()
                SearchScreen(
                    searchedProducts = searchProducts,
                    searchQuery = searchQuery,
                    onSearchQueryChange = onSearchQueryChange,
                    onSearch = { query ->
                        searchViewModel.searchProducts(query)
                    },
                    navController = navController,
                    onAddToCart = { product ->
                        searchViewModel.toggleProductAddedToCart(product)
                    },
                    productsIds = productsIds,
                    gridState = gridScrollState
                )
            }
            composable(
                route = Routes.Checkout.route + "?totalItems={totalItems}&totalPrice={totalPrice}",
                arguments = listOf(
                    navArgument("totalItems") {
                        type = NavType.IntType
                        defaultValue = 0
                    },
                    navArgument("totalPrice") {
                        type = NavType.FloatType
                        defaultValue = 0f
                    },
                )
            ) { backStackEntry ->
                val totalItems = backStackEntry.arguments?.getInt("totalItems") ?: 0
                val totalPrice = backStackEntry.arguments?.getFloat("totalPrice")?.toDouble() ?: 0.0

                val viewModel = viewModel<PaymentViewModel>()
                CheckoutScreen(
                    paddingValues = paddingValues,
                    totalItems = totalItems,
                    totalPrice = totalPrice,
                    navController = navController,
                    viewModel = viewModel

                )
            }

            composable("PaymentSuccess") {
                PaymentCompleteScreen(navController = navController)
            }
            composable(Routes.SignIn.route) {

                SignInScreen(
                    navController = navController,
                    googleAuthClient = googleAuthClient
                )
            }
            composable(Routes.Settings.route) {
                SettingsScreen(
                    paddingValues = paddingValues,
                    navController = navController
                )
            }
        }
    }

}