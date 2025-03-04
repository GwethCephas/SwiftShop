package com.ceph.swiftshop.domain.repository

import androidx.paging.PagingData
import com.ceph.swiftshop.domain.model.Product
import kotlinx.coroutines.flow.Flow

interface SwiftRepository {

    suspend fun getProducts(): Flow<PagingData<Product>>

    suspend fun getProductsByCategory(category: String): Flow<PagingData<Product>>

    suspend fun getSearchedProducts(query: String): Flow<PagingData<Product>>


    fun getAllProductsAddedToCart(): Flow<PagingData<Product>>

    fun getAllProductIds(): Flow<List<Int>>

    suspend fun getProductById(id: Int): Product

    suspend fun toggleAddedProduct(product: Product)
}