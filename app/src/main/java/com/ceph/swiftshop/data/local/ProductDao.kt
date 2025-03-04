package com.ceph.swiftshop.data.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDao {

    @Upsert
    suspend fun addProduct(productEntity: ProductEntity)
    @Delete
    suspend fun deleteProduct(productEntity: ProductEntity)

    @Query("SELECT * FROM products_table")
    fun getAllProducts():PagingSource<Int, ProductEntity>
    @Query("SELECT * FROM products_table WHERE id = :id")
    suspend fun getProductById(id: Int): ProductEntity

    @Query("SELECT id FROM products_table")
    fun getAllProductIds(): Flow<List<Int>>

    @Query("SELECT EXISTS(SELECT * FROM products_table WHERE id = :id)")
    suspend fun isProductAdded(id: Int): Boolean

}