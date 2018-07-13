# RecyclerView + Data Binding Sample

## Simple sample

![](https://raw.githubusercontent.com/ciandt-mobile/android-recyclerview-binding/master/images/simple.gif)

### Use LiveData directly with data binding

```xml
<?xml version="1.0" encoding="utf-8"?>
<layout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto">

    <data>

        <variable
            name="viewModel"
            type="com.ciandt.recyclerviewbinding.presentation.simple.SimpleViewModel" />
    </data>

    <android.support.constraint.ConstraintLayout
        android:layout_width="match_parent"
        android:layout_height="match_parent">

        <android.support.v7.widget.RecyclerView
            android:id="@+id/recyclerView"
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            app:items="@{viewModel.items}" /> <!-- Use android data binding to update adapter -->
    </android.support.constraint.ConstraintLayout>
</layout>
```

```kotlin
class SimpleViewModel : ViewModel() {

    val items: LiveData<List<String>> =
        MutableLiveData<List<String>>().apply { value = ItemsRepository().getItemsPage() }
}
```

### Simple RecyclerView Setup

```kotlin
class SimpleFragment : Fragment() {

    ...
    
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProviders.of(this)[SimpleViewModel::class.java]

        val layoutManager = LinearLayoutManager(context)

        recyclerView.layoutManager = layoutManager
        recyclerView.hasFixedSize()
        recyclerView.adapter = ItemsAdapter()
        recyclerView.addItemDecoration(DividerItemDecoration(context, layoutManager.orientation))

        binding.viewModel = viewModel
    }

}
```

## Endless Scroll sample

![](https://raw.githubusercontent.com/ciandt-mobile/android-recyclerview-binding/master/images/endless.gif)

```kotlin
class EndlessFragment : Fragment() {

    ...
    
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProviders.of(this)[EndlessViewModel::class.java]

        val layoutManager = LinearLayoutManager(context)

        recyclerView.layoutManager = layoutManager
        recyclerView.hasFixedSize()
        recyclerView.adapter = ItemsAdapter()
        recyclerView.addItemDecoration(DividerItemDecoration(context, layoutManager.orientation))

        // USE EXTENSION TO SIMPLIFY ENDLESS SETUP
        recyclerView.endless { viewModel.fetchItems() }

        // USE EVENT WRAPPER TO NOTIFY CHANGES TO ADAPTER
        //https://bit.ly/2NeeTMP [LiveData with SnackBar, Navigation and other events (the SingleLiveEvent case)]
        viewModel.updateList.subscribe(this) { 
            recyclerView.adapter.notifyDataSetChanged()
        }

        binding.viewModel = viewModel
    }
}
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
