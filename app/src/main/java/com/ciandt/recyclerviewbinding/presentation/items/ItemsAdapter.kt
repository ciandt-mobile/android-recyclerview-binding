@file:JvmName("ItemsAdapter")

package com.ciandt.recyclerviewbinding.presentation.items

import android.databinding.BindingAdapter
import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.ciandt.recyclerviewbinding.R
import com.ciandt.recyclerviewbinding.databinding.ItemViewBinding

class ItemsAdapter : RecyclerView.Adapter<ItemsAdapter.ViewHolder>() {

    private lateinit var layoutInflater: LayoutInflater
    private var items: List<String> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        if (!::layoutInflater.isInitialized) {
            layoutInflater = LayoutInflater.from(parent.context)
        }

        val binding = DataBindingUtil.inflate<ItemViewBinding>(
            layoutInflater,
            R.layout.item_view,
            parent,
            false
        )

        return ViewHolder(binding)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (items.size > position) {
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

    class ViewHolder(private val binding: ItemViewBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: String) {
            binding.item = item
        }
    }
}