package com.ciandt.recyclerviewbinding.repository

class ItemsRepository {

    private var nextItem = 1

    fun getItemsPage(pageSize: Int = 30): List<String> {

        val items = mutableListOf<String>()
        val lastItem = nextItem + pageSize - 1

        for (i in nextItem..lastItem) {
            items.add("Item $i")
        }

        nextItem = lastItem + 1

        return items
    }
}