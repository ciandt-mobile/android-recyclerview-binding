@file:JvmName("ItemsAdapter")

package com.ciandt.recyclerviewbinding.presentation.items

import android.databinding.BindingAdapter
import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ciandt.recyclerviewbinding.R
import com.ciandt.recyclerviewbinding.databinding.ItemViewBinding
import com.ciandt.recyclerviewbinding.presentation.startAnimation
import kotlinx.android.synthetic.main.loading_view.view.*

class ItemsAdapter : RecyclerView.Adapter<ItemsAdapter.ViewHolder>() {

    private var items: List<String> = emptyList()

    private val loading = 0
    private val item = 1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return if (viewType == loading) {
            LoadingViewHolder(parent)
        } else {
            ItemViewHolder(parent)
        }
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (holder is ItemViewHolder && items.size > position) {
            holder.bind(items[position])
        }
    }

    fun update(items: List<String>) {
        this.items = items
        notifyDataSetChanged()
    }

    companion object {
        @JvmStatic
        @BindingAdapter("items")
        fun RecyclerView.bindItems(items: List<String>) {
            val adapter = adapter as ItemsAdapter
            adapter.update(items)
        }
    }

    override fun getItemViewType(position: Int) =
        if (items[position] == "loading") loading else item

    abstract class ViewHolder(view: View) : RecyclerView.ViewHolder(view)

    class ItemViewHolder(
        private val parent: ViewGroup,
        private val binding: ItemViewBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_view,
            parent,
            false
        )
    ) : ViewHolder(binding.root) {

        fun bind(item: String) {
            binding.item = item
        }
    }

    class LoadingViewHolder(
        private val parent: ViewGroup,
        view: View = LayoutInflater.from(parent.context).inflate(
            R.layout.loading_view,
            parent,
            false
        )
    ) :
        ViewHolder(view) {

        init {
            view.imgLoading.post { view.imgLoading.drawable.startAnimation() }
        }
    }
}