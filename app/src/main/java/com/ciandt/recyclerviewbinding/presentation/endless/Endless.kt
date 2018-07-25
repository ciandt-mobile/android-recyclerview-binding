package com.ciandt.recyclerviewbinding.presentation.endless

import android.support.v4.view.accessibility.AccessibilityEventCompat.TYPE_VIEW_ACCESSIBILITY_FOCUSED
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.RecyclerViewAccessibilityDelegate
import android.view.View
import android.view.ViewGroup
import android.view.accessibility.AccessibilityEvent

class EndlessScroll(
    private val recyclerView: RecyclerView,
    val visibleThreshold: Int = 10,
    private val loadMore: () -> Unit
) :
    RecyclerView.OnScrollListener() {

    private var previousTotal = 1
    private var loading = true

    class AccessibilityDelegate(recyclerView: RecyclerView, var changeRow: () -> Unit) :
        RecyclerViewAccessibilityDelegate(recyclerView) {

        override fun onRequestSendAccessibilityEvent(
            host: ViewGroup?,
            child: View?,
            event: AccessibilityEvent?
        ): Boolean {
            event?.let {
                if (it.eventType == TYPE_VIEW_ACCESSIBILITY_FOCUSED) {
                    changeRow()
                }
            }

            return super.onRequestSendAccessibilityEvent(host, child, event)
        }
    }

    init {
        recyclerView.setAccessibilityDelegateCompat(
            AccessibilityDelegate(
                recyclerView,
                ::changeRowByAccessibility
            )
        )
    }

    private fun changeRowByAccessibility() {
        if (this.recyclerView.scrollState != RecyclerView.SCROLL_STATE_IDLE) {
            return
        }

        loading()
    }

    override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        if (this.recyclerView.scrollState == RecyclerView.SCROLL_STATE_IDLE) {
            return
        }

        loading()
    }

    private fun loading() {
        val visibleItemCount = recyclerView.childCount
        val totalItemCount = recyclerView.layoutManager.itemCount
        val firstVisibleItem =
            (recyclerView.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()

        if (loading) {
            if (totalItemCount > previousTotal) {
                loading = false
                previousTotal = totalItemCount
            }
        }

        if (!loading && (totalItemCount - visibleItemCount) <= (firstVisibleItem + visibleThreshold)) {
            loadMore()
            loading = true
        }
    }
}

fun RecyclerView.endless(visibleThreshold: Int = 10, loadMore: () -> Unit) {
    this.addOnScrollListener(EndlessScroll(this, visibleThreshold, loadMore))
}