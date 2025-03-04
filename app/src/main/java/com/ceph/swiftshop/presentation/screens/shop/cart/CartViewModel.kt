package com.ceph.swiftshop.presentation.screens.shop.cart

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
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class CartViewModel(
    private val repository: SwiftRepository
) : ViewModel() {

    val addedProducts: StateFlow<PagingData<Product>> = repository.getAllProductsAddedToCart()
        .cachedIn(viewModelScope)
        .stateIn(
            viewModelScope,
            SharingStarted.Lazily,
            PagingData.empty()
        )

    private val _productIds = MutableStateFlow<List<Int>>(emptyList())
    val productIds = _productIds.asStateFlow()

    init {
        getAllProductIds()
    }

    private fun getAllProductIds() {
        viewModelScope.launch {
            repository.getAllProductIds().collect { ids->
                _productIds.value = ids
                _itemQuantities.value = ids.associateWith { _itemQuantities.value[it] ?: 1 }

            }
        }
    }


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


    private val _itemQuantities = MutableStateFlow<Map<Int, Int>>(emptyMap())
    val itemQuantities = _itemQuantities.asStateFlow()

    val totalItems = _itemQuantities.map { quantities -> quantities.values.sum() }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), 0)

    val totalPrice = _itemQuantities.map { quantities ->
        quantities.entries.sumOf { (id, qty)->
            val product = repository.getProductById(id)
            (product.price ?: 0.0) * qty
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), 0.0)

    fun updateQuantities(productId: Int, quantity: Int) {
        _itemQuantities.value = _itemQuantities.value.toMutableMap().apply {
            this[productId] = quantity
        }
    }

}