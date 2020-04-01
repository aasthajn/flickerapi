package com.app.flickerimageview.utils

import androidx.recyclerview.widget.RecyclerView

import androidx.recyclerview.widget.LinearLayoutManager
import com.app.flickerimageview.utils.Constants.PER_PAGE

abstract class PaginationListener(private val layoutManager: LinearLayoutManager) : RecyclerView.OnScrollListener() {

    var isLoading: Boolean = false
    var connected: Boolean = false

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        val visibleItemCount = layoutManager.childCount
        val totalItemCount = layoutManager.itemCount
        val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
        if (!isLoading && connected) {
            if (visibleItemCount + firstVisibleItemPosition >= totalItemCount
                && firstVisibleItemPosition >= 0
                && totalItemCount >= PER_PAGE
            ) {
                loadMoreItems()
            }
        }
    }

    protected abstract fun loadMoreItems()
}