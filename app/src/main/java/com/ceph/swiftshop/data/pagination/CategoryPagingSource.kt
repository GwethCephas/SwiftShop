package com.ceph.swiftshop.data.pagination


import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.ceph.swiftshop.data.remote.ApiService
import com.ceph.swiftshop.domain.model.Product

class CategoryPagingSource(
    private val apiService: ApiService,
    private val category: String
): PagingSource<Int, Product>() {

    override fun getRefreshKey(state: PagingState<Int, Product>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Product> {
        return try {
            val currentPage = params.key ?: 0

            val response = apiService.getProductsByCategory(
                limit = params.loadSize ,
                skip = currentPage * params.loadSize,
                category = category
            )

            val endOfPaginationReached = response.isEmpty()
            val prevPage = if(currentPage == 0) null else currentPage -1
            val nextPage = if(endOfPaginationReached) null else currentPage + 1

            LoadResult.Page(
                data = response,
                prevKey = prevPage,
                nextKey = nextPage
            )
        }catch (e: Exception) {
            Log.e("CategoryPagingSource", "Error: ${e.message}")
            LoadResult.Error(e)
        }
    }
}