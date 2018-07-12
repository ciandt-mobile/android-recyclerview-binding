package com.ciandt.recyclerviewbinding.presentation.simple

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.ciandt.recyclerviewbinding.repository.ItemsRepository

class SimpleViewModel : ViewModel() {

    val items: LiveData<List<String>> =
        MutableLiveData<List<String>>().apply { value = ItemsRepository().getItemsPage() }
}