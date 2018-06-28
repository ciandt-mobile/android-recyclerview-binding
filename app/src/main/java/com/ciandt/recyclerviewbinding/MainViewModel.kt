package com.ciandt.recyclerviewbinding

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel

class MainViewModel: ViewModel() {

    val items : LiveData<List<String>> = MutableLiveData<List<String>>()

    init {

        val items = mutableListOf<String>()

        for (i in 1..30) {
            items.add("Item $i")
        }

        (this.items as MutableLiveData<List<String>>).value = items
    }
}