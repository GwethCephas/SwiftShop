package com.ceph.swiftshop.data.pagination

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.ceph.swiftshop.data.remote.ApiService
import com.ceph.swiftshop.domain.model.Product

class SearchPagingSource(
    private val apiService: ApiService,
    private val query: String
): PagingSource<Int, Product>() {

    override fun getRefreshKey(state: PagingState<Int, Product>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Product> {
        return try {
            val currentPage = params.key ?: 0

            val response = apiService.getSearchedProducts(
                limit = params.loadSize ,
                skip = currentPage * params.loadSize
            )

            val endOfPaginationReached = response.isEmpty()
            val prevPage = if(currentPage == 0) null else currentPage -1
            val nextPage = if(endOfPaginationReached) null else currentPage + 1

            LoadResult.Page(
                data = response.filter { it.title.contains(query, ignoreCase = true) },
                prevKey = prevPage,
                nextKey = nextPage
            )
        }catch (e: Exception) {
            Log.e("SearchedPagingSource", "Error: ${e.message}")
            LoadResult.Error(e)
        }
    }
}