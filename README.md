# RecyclerView + Data Binding Sample

## ViewModel

```kotlin
class MainViewModel : ViewModel() {

    val items: LiveData<Array<String>> = MutableLiveData<Array<String>>()
}
```
## RecyclerView Layout

```xml
<?xml version="1.0" encoding="utf-8"?>
<layout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    xmlns:bind="http://schemas.android.com/apk/res-auto">

    <data>

        <variable
            name="viewModel"
            type="com.ciandt.recyclerviewbinding.MainViewModel" />
    </data>

    <android.support.constraint.ConstraintLayout>

        <android.support.v7.widget.RecyclerView
            ...
            bind:items="@{viewModel.items}"/> <!--Binding LiveData property-->
    </android.support.constraint.ConstraintLayout>
</layout>
```

## Adapter

```kotlin
class MainAdapter : RecyclerView.Adapter<MainAdapter.ViewHolder>() {

    private var items: Array<String> = emptyArray()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder { ... }

    override fun getItemCount() { ... }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (items.size > position) {
            holder.bind(items[position])
        }
    }

    fun update(items: Array<String>) { ... }

    companion object {
        @JvmStatic
        @BindingAdapter("items") // app:items attribute in RecyclerView
        fun RecyclerView.bindItems(items: Array<String>) {
            val adapter = adapter as MainAdapter
            adapter.update(items)
        }
    }

    class ViewHolder(private val binding: ItemViewBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: String) {
            binding.item = item // Bindind RecyclerView row
        }
    }
}
```

## RecyclerView item layout
```xml
<?xml version="1.0" encoding="utf-8"?>
<layout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools">

    <data>

        <variable
            name="item"
            type="String" />
    </data>

    <android.support.constraint.ConstraintLayout>

        <TextView
            ...
            android:text="@{item}"/>

    </android.support.constraint.ConstraintLayout>
</layout>
```

```
MIT License

Copyright (c) 2018 CI&T Mobile Team

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```
