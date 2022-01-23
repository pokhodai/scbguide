package com.spravochnic.scbguide.views

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.spravochnic.scbguide.DefaultNetworkEventObserver
import com.spravochnic.scbguide.R
import com.spravochnic.scbguide.adapters.CategoriesAdapter
import com.spravochnic.scbguide.adapters.MainAdapter
import com.spravochnic.scbguide.databinding.FragmentCategoriesBinding
import com.spravochnic.scbguide.databinding.FragmentMainBinding
import com.spravochnic.scbguide.viewModels.MainViewModel

class CategoriesFragment : Fragment() {

    private lateinit var binding: FragmentCategoriesBinding

    private val viewModel: MainViewModel by activityViewModels()
    private lateinit var onCategoriesResult: DefaultNetworkEventObserver

    private val categoryAdapter by lazy {
        CategoriesAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View = FragmentCategoriesBinding.inflate(inflater, container, false).apply {
        binding = this
        initializeObservers()
        setObservers()
        initAdapter()
        setListeners()
    }.root

    private fun setListeners() {
        binding.run {
            mtCategories.setNavigationOnClickListener {
                findNavController().popBackStack()
            }
        }
    }

    private fun initAdapter() {
        binding.rvCategories.adapter = categoryAdapter
    }

    private fun setObservers() {
        viewModel.run {
            categoriesResult.observe(viewLifecycleOwner, onCategoriesResult)
        }
    }

    private fun initializeObservers() {
        onCategoriesResult = DefaultNetworkEventObserver(
            anchorView = binding.root,
            doOnSuccess = {
                viewModel.categories.value?.let {
                    categoryAdapter.submitList(it)

                }
            },
            doOnError = {
                findNavController().popBackStack()
            },
            doOnFailure = {
                findNavController().popBackStack()
            }
        )
    }
}