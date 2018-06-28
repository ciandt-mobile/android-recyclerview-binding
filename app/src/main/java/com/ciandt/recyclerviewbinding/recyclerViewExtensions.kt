package com.ciandt.recyclerviewbinding

import android.databinding.BindingAdapter
import android.support.v7.widget.RecyclerView

@BindingAdapter("bind:items")
fun RecyclerView.bindItems(list: List<String>) {
    val adapter = adapter as BindableRecyclerViewAdapter<*, String>
    adapter.update(list)
}
