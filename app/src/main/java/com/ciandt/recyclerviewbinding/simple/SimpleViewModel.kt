package com.ciandt.recyclerviewbinding.simple

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel

class SimpleViewModel : ViewModel() {

    val items: LiveData<Array<String>> = MutableLiveData<Array<String>>()

    init {

        val items = mutableListOf<String>()

        for (i in 1..30) {
            items.add("Item $i")
        }

        (this.items as MutableLiveData<Array<String>>).value = items.toTypedArray()
    }
}