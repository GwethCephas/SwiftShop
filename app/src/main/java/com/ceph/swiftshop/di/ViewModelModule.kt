package com.ceph.swiftshop.di

import com.ceph.swiftshop.presentation.authentication.GoogleAuthClient
import com.ceph.swiftshop.presentation.screens.shop.cart.CartViewModel
import com.ceph.swiftshop.presentation.screens.home.HomeViewModel
import com.ceph.swiftshop.presentation.screens.search.SearchViewModel
import com.ceph.swiftshop.presentation.screens.explore.ShopViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {

    viewModel { HomeViewModel(get()) }

    viewModel { SearchViewModel(get()) }

    viewModel { ShopViewModel(get()) }

    viewModel { CartViewModel(get()) }

    single { GoogleAuthClient(get()) }


}