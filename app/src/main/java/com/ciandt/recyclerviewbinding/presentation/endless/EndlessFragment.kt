package com.ciandt.recyclerviewbinding.presentation.endless


import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.ciandt.recyclerviewbinding.R
import com.ciandt.recyclerviewbinding.databinding.FragmentEndlessBinding
import com.ciandt.recyclerviewbinding.presentation.items.ItemsAdapter
import com.ciandt.recyclerviewbinding.presentation.subscribe
import kotlinx.android.synthetic.main.fragment_endless.*

class EndlessFragment : Fragment() {

    private lateinit var binding: FragmentEndlessBinding
    private lateinit var viewModel: EndlessViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_endless, container, false)

        binding.setLifecycleOwner(this)

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProviders.of(this)[EndlessViewModel::class.java]

        val layoutManager = LinearLayoutManager(context)

        recyclerView.layoutManager = layoutManager
        recyclerView.hasFixedSize()
        recyclerView.adapter = ItemsAdapter()
        recyclerView.addItemDecoration(DividerItemDecoration(context, layoutManager.orientation))

        recyclerView.endless { viewModel.fetchItems() }

        viewModel.updateList.subscribe(this) {
            recyclerView.adapter.notifyDataSetChanged()
        }

        binding.viewModel = viewModel
    }
}