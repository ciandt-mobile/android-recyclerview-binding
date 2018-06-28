@file:JvmName("BindableRecyclerViewAdapter")
package com.ciandt.recyclerviewbinding

import android.databinding.BindingAdapter
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.support.annotation.LayoutRes
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup

class BindableRecyclerViewAdapter<TDataBinding : ViewDataBinding, TData : Any?>(
    @LayoutRes private val itemLayoutRes: Int, private val bind: (TDataBinding, TData) -> Unit
) :
    RecyclerView.Adapter<BindableRecyclerViewAdapter.ViewHolder<TDataBinding>>() {

    private lateinit var layoutInflater: LayoutInflater
    private lateinit var collection: Collection<*>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder<TDataBinding> {
        if (!::layoutInflater.isInitialized) {
            layoutInflater = LayoutInflater.from(parent.context)
        }

        val binding = DataBindingUtil.inflate<TDataBinding>(
            layoutInflater,
            itemLayoutRes,
            parent,
            false
        )

        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return if (::collection.isInitialized) collection.size else 0
    }

    override fun onBindViewHolder(holder: ViewHolder<TDataBinding>, position: Int) {
        if (::collection.isInitialized && collection.isNotEmpty()) {

            val item = collection.elementAt(position)

            @Suppress("UNCHECKED_CAST")
            bind(holder.binding, item as TData)
        }
    }

    fun update(collection: Collection<Any?>) {
        this.collection = collection
        notifyDataSetChanged()
    }

    companion object {
        @JvmStatic
        @BindingAdapter("collection")
        fun RecyclerView.bindCollection(collection: Collection<*>) {
            val adapter = adapter as BindableRecyclerViewAdapter<*, *>
            adapter.update(collection)
        }
    }

    class ViewHolder<T : ViewDataBinding>(val binding: T) : RecyclerView.ViewHolder(binding.root)
}