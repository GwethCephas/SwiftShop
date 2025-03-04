package com.ceph.swiftshop.presentation.screens.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.ceph.swiftshop.domain.model.Product
import com.ceph.swiftshop.domain.repository.SwiftRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class HomeViewModel(
    private val repository: SwiftRepository
) : ViewModel() {

    private val _products = MutableStateFlow<PagingData<Product>>(PagingData.empty())
    val products = _products.asStateFlow()

    private val _featuredProducts = MutableStateFlow<PagingData<Product>>(PagingData.empty())
    val featuredProducts = _featuredProducts.asStateFlow()

    init {
        getProducts()
    }

    private fun getProducts() {
        viewModelScope.launch {
            repository.getProducts().collect { products ->
                _products.value = products
            }
        }
    }
    init {
        getFeaturedProducts("jewelery")
    }

    private fun getFeaturedProducts(category: String) {
        viewModelScope.launch {
            try {
                repository.getProductsByCategory(category)
                    .cachedIn(viewModelScope)
                    .collect {
                        _featuredProducts.value = it

                    }
            } catch (e:Exception) {
                Log.e("HomeViewModel", "getFeaturedProducts: $e")
                e.printStackTrace()
            }

        }
    }

    val getAllAddedProductsIds: StateFlow<List<Int>> = repository.getAllProductIds()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )


    fun toggleProductAddedToCart(product: Product) {
        viewModelScope.launch {
            try {
                repository.toggleAddedProduct(product)
            } catch (e: Exception) {
                Log.e("ShopViewModel", "toggleProductAddedToCart: $e")
                e.printStackTrace()
            }

        }
    }

}