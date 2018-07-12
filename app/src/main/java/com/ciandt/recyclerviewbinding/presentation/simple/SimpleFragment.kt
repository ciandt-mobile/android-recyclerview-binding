package com.ciandt.recyclerviewbinding.presentation.simple


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
import com.ciandt.recyclerviewbinding.databinding.FragmentSimpleBinding
import kotlinx.android.synthetic.main.fragment_simple.*

class SimpleFragment : Fragment() {

    private lateinit var binding: FragmentSimpleBinding
    private lateinit var viewModel: SimpleViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_simple, container, false)

        binding.setLifecycleOwner(this)

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(SimpleViewModel::class.java)

        val layoutManager = LinearLayoutManager(context)

        recyclerView.layoutManager = layoutManager
        recyclerView.hasFixedSize()
        recyclerView.adapter = SimpleAdapter()
        recyclerView.addItemDecoration(DividerItemDecoration(context, layoutManager.orientation))

        binding.viewModel = viewModel
    }

}
