package com.ciandt.recyclerviewbinding.presentation.endless

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.ciandt.recyclerviewbinding.presentation.SimpleEvent
import com.ciandt.recyclerviewbinding.repository.ItemsRepository

class EndlessViewModel : ViewModel() {

    private val repository = ItemsRepository()
    private val list = mutableListOf<String>()

    private val _items = MutableLiveData<List<String>>().apply { value = list }
    private val _updateList = MutableLiveData<SimpleEvent>()

    val items: LiveData<List<String>>
        get() = _items

    val updateList: LiveData<SimpleEvent>
        get() = _updateList

    fun fetchItems() {
        list.addAll(repository.getItemsPage())
        _updateList.value = SimpleEvent()
    }

    init {
        fetchItems()
    }
}