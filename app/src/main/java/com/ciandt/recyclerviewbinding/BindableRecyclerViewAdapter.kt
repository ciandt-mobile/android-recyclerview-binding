package com.ciandt.recyclerviewbinding

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.support.annotation.LayoutRes
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.databinding.ObservableArrayList
import android.databinding.BindingAdapter



class BindableRecyclerViewAdapter<TDataBinding : ViewDataBinding, TData : Any>(
    @LayoutRes val itemLayoutRes: Int, val bind: (TDataBinding, TData) -> Unit
) :
    RecyclerView.Adapter<BindableRecyclerViewAdapter.ViewHolder<TDataBinding>>() {

    private lateinit var layoutInflater: LayoutInflater
    private lateinit var list: Collection<TData>

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
        return if (::list.isInitialized) list.size else 0
    }

    override fun onBindViewHolder(holder: ViewHolder<TDataBinding>, position: Int) {
        if (::list.isInitialized && list.isNotEmpty()) {

            val item = list.elementAt(position)

            bind(holder.binding, item)
        }
    }

    fun update(list: Collection<TData>) {
        this.list = list
        notifyDataSetChanged()
    }

    class ViewHolder<T : ViewDataBinding>(val binding: T) : RecyclerView.ViewHolder(binding.root)
}