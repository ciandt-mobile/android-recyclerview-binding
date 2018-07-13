package com.ciandt.recyclerviewbinding.presentation.endless

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.os.Handler
import com.ciandt.recyclerviewbinding.presentation.SimpleEvent
import com.ciandt.recyclerviewbinding.repository.ItemsRepository

class EndlessViewModel : ViewModel() {

    private val repository = ItemsRepository()
    private val list = mutableListOf<String>()

    private val _items = MutableLiveData<List<String>>().apply { value = list }
    private val _updateList = MutableLiveData<SimpleEvent>()

    private val handler = Handler()

    private var loading = false

    private var delay = 0L

    val items: LiveData<List<String>>
        get() = _items

    val updateList: LiveData<SimpleEvent>
        get() = _updateList

    init {
        fetchItems()
    }

    fun fetchItems() {
        if (!loading) {
            loading = true

            showLoading()

            // Simulate delay

            handler.postDelayed({
                removeLoading()

                list.addAll(repository.getItemsPage())

                updateList()

                delay = if (delay == 0L) 3000 else 0
                loading = false
            }, delay)
        }
    }

    private fun showLoading() {
        list.add("loading")
        updateList()
    }

    private fun removeLoading() {
        if (list.last() == "loading") {
            list.removeAt(list.lastIndex)
        }
    }

    private fun updateList() {
        _updateList.value = SimpleEvent()
    }
}