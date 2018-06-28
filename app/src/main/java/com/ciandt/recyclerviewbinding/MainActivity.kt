package com.ciandt.recyclerviewbinding

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.ciandt.recyclerviewbinding.databinding.ActivityMainBinding
import com.ciandt.recyclerviewbinding.databinding.ItemViewBinding
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel

    private val adapter =
        BindableRecyclerViewAdapter<ItemViewBinding, String>(R.layout.item_view) { b, v ->
            b.item = v
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)

        val binding: ActivityMainBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_main)

        binding.setLifecycleOwner(this)

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.hasFixedSize()
        recyclerView.adapter = adapter

        binding.viewModel = viewModel

        viewModel.items.observe(this, Observer { adapter.update(it ?: emptyList()) })
    }
}