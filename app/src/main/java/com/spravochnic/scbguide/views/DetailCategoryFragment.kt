package com.spravochnic.scbguide.views

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.spravochnic.scbguide.DefaultNetworkEventObserver
import com.spravochnic.scbguide.R
import com.spravochnic.scbguide.Success
import com.spravochnic.scbguide.adapters.CategoriesAdapter
import com.spravochnic.scbguide.adapters.DetailsCategoryViewPagerAdapter
import com.spravochnic.scbguide.databinding.FragmentCategoriesBinding
import com.spravochnic.scbguide.databinding.FragmentDetailCategoryBinding
import com.spravochnic.scbguide.models.DetailCategory
import com.spravochnic.scbguide.viewModels.DetailCategoryViewModel

class DetailCategoryFragment : Fragment() {

    lateinit var binding: FragmentDetailCategoryBinding

    private val detailViewModel: DetailCategoryViewModel by activityViewModels()
    private lateinit var onDetailCategory: DefaultNetworkEventObserver

    private val detailsCategoryAdapter by lazy {
        DetailsCategoryViewPagerAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View = FragmentDetailCategoryBinding.inflate(inflater, container, false).apply {
        binding = this
        initializeObservers()
        setObservable()
    }.root

    private fun initializeObservers() {
        onDetailCategory = DefaultNetworkEventObserver(
            anchorView = binding.root,
            doOnSuccess = {
                loadAdapter()
            }
        )
    }

    private fun loadAdapter() {
        detailViewModel.detail.value?.let {
            val data = mutableListOf<DetailCategory>()
            val type = arguments?.getString(TYPE)
            for (i in it.indices) {
                if (it[i].type == type) {
                    data.add(it[i])
                }
            }
            detailsCategoryAdapter.submitList(data)
            binding.vpDetail.adapter = detailsCategoryAdapter
        }
    }

    private fun setObservable() {
        detailViewModel.detailResult.observe(viewLifecycleOwner, onDetailCategory)
    }

    companion object {
        const val TYPE = "type"
    }
}