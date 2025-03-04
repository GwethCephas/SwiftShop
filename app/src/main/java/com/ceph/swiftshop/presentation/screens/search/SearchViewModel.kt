package com.ceph.swiftshop.presentation.screens.search

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.ceph.swiftshop.domain.model.Product
import com.ceph.swiftshop.domain.repository.SwiftRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class SearchViewModel(
    private val repository: SwiftRepository
): ViewModel() {

    private val _searchedProducts = MutableStateFlow<PagingData<Product>>(PagingData.empty())
    val searchedProducts = _searchedProducts.asStateFlow()


    fun searchProducts(query:String) {
        viewModelScope.launch {
            repository.getSearchedProducts(query).collect{
                _searchedProducts.value = it
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
            } catch (e:Exception){
                Log.e("ShopViewModel", "toggleProductAddedToCart: $e")
                e.printStackTrace()
            }

        }
    }

}