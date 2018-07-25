package com.ciandt.recyclerviewbinding.presentation.endless

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.os.Handler
import com.ciandt.recyclerviewbinding.presentation.Event
import com.ciandt.recyclerviewbinding.repository.ItemsRepository

data class Page(val positionStart: Int, val count: Int)

class EndlessViewModel : ViewModel() {

    private val repository = ItemsRepository()
    private val list = mutableListOf<String>()

    private val _items = MutableLiveData<List<String>>().apply { value = list }
    private val _addItems = MutableLiveData<Event<Page>>()
    private val _removeItem = MutableLiveData<Event<Int>>()

    private val handler = Handler()

    private var loading = false

    private var delay = 0L

    val items: LiveData<List<String>>
        get() = _items

    val addItems: LiveData<Event<Page>>
        get() = _addItems

    val removeItem: LiveData<Event<Int>>
        get() = _removeItem

    init {
        fetchItems()
    }

    fun fetchItems() {
        if (!loading) {
            loading = true

            showLoading()

            // Simulate delay

            handler.postDelayed({

                val position = list.size
                val newItems = repository.getItemsPage()
                list.addAll(newItems)

                addItems(position, newItems.size)
                removeLoading(position-1)

                delay = if (delay == 0L) 3000 else 0
                loading = false
            }, delay)
        }
    }

    private fun showLoading() {
        list.add("loading")
        addItems(list.size - 1, 1)
    }

    private fun removeLoading(position: Int) {
        list.removeAt(position)
        removeItems(position)
    }

    private fun addItems(positionStart: Int, count: Int) {
        _addItems.value = Event(Page(positionStart, count))
    }

    private fun removeItems(positionStart: Int) {
        _removeItem.value = Event(positionStart)
    }
}