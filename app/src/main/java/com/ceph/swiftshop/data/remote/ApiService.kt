package com.ceph.swiftshop.data.remote

import com.ceph.swiftshop.domain.model.Product
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("products/category/{category}")
    suspend fun getProductsByCategory(
        @Path("category") category: String,
        @Query("limit") limit: Int? = null,
        @Query("skip") skip: Int? = null
    ): List<Product>

    @GET("products")
    suspend fun getProducts(
        @Query("limit") limit: Int,
        @Query("skip") skip: Int
    ): List<Product>

    @GET("products")
    suspend fun getSearchedProducts(
        @Query("limit") limit: Int,
        @Query("skip") skip: Int
    ): List<Product>
}