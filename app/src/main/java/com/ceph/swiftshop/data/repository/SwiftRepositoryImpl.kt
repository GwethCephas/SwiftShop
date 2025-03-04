package com.ceph.swiftshop.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.ceph.swiftshop.data.local.ProductDatabase
import com.ceph.swiftshop.data.mappers.toProduct
import com.ceph.swiftshop.data.mappers.toProductEntity
import com.ceph.swiftshop.data.pagination.CategoryPagingSource
import com.ceph.swiftshop.data.pagination.ProductsPagingSource
import com.ceph.swiftshop.data.pagination.SearchPagingSource
import com.ceph.swiftshop.data.remote.ApiService
import com.ceph.swiftshop.domain.model.Product
import com.ceph.swiftshop.domain.repository.SwiftRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class SwiftRepositoryImpl(
    private val apiService: ApiService,
    private val productDatabase: ProductDatabase
) : SwiftRepository {


    private val productDao = productDatabase.dao
    override suspend fun getProducts(): Flow<PagingData<Product>> {
        return Pager(
            config = PagingConfig(10),
            pagingSourceFactory = {
                ProductsPagingSource(apiService)
            }
        ).flow

    }

    override suspend fun getProductsByCategory(category: String): Flow<PagingData<Product>> {
        return Pager(
            config = PagingConfig(10),
            pagingSourceFactory = {
                CategoryPagingSource(apiService, category)
            }
        ).flow

    }

    override suspend fun getSearchedProducts(query: String): Flow<PagingData<Product>> {

        return Pager(
            config = PagingConfig(10),
            pagingSourceFactory = {
                SearchPagingSource(apiService, query)

            }
        ).flow
    }

    override fun getAllProductsAddedToCart(): Flow<PagingData<Product>> {
        return Pager(
            config = PagingConfig(10, enablePlaceholders = false),
            pagingSourceFactory = {
                productDao.getAllProducts()
            }
        ).flow
            .map { pagingData ->
                pagingData.map { it.toProduct() }

            }
    }

    override suspend fun getProductById(id: Int): Product {
        return try {
            productDao.getProductById(id).toProduct()
        } catch (e: Exception) {
            e.printStackTrace()
            throw e
        }
    }

    override fun getAllProductIds(): Flow<List<Int>> {
        return productDao.getAllProductIds()
    }

    override suspend fun toggleAddedProduct(product: Product) {
        val isProductAdded = productDao.isProductAdded(product.id)
        val productEntity = product.toProductEntity()


        if (isProductAdded) {
            productDao.deleteProduct(productEntity)
        } else {
            productDao.addProduct(productEntity)
        }
    }
}