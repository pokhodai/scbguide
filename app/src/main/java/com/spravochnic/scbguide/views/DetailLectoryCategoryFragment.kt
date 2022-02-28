package com.spravochnic.scbguide.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.spravochnic.scbguide.DefaultNetworkEventObserver
import com.spravochnic.scbguide.State
import com.spravochnic.scbguide.adapters.DetailsCategoryViewPagerAdapter
import com.spravochnic.scbguide.databinding.FragmentDetailCategoryBinding
import com.spravochnic.scbguide.models.DetailCategory
import com.spravochnic.scbguide.viewModels.DetailsLectoryCategoriesViewModel

class DetailLectoryCategoryFragment : Fragment() {

    lateinit var binding: FragmentDetailCategoryBinding

    private lateinit var onDetailCategory: DefaultNetworkEventObserver

    private val detailsLectoryCategoriesViewModel: DetailsLectoryCategoriesViewModel by activityViewModels()

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
        setObservers()
        setAdapter()
    }.root

    private fun setAdapter() {
        binding.vpDetail.adapter = detailsCategoryAdapter
    }

    private fun setObservers() {
        detailsLectoryCategoriesViewModel.detailCategoryResult.observe(viewLifecycleOwner, onDetailCategory)
    }

    override fun onStart() {
        super.onStart()
        checkIsDataLoaded()
    }

    private fun checkIsDataLoaded() {
        if (detailsLectoryCategoriesViewModel.detailCategoryResult.value?.peekContent() == State.SUCCESS) loadAdapter()
        else detailsLectoryCategoriesViewModel.getDetailsLectoryCategories()
    }

    private fun initializeObservers() {
        onDetailCategory = DefaultNetworkEventObserver(
            anchorView = binding.root,
            doOnLoading = {
                binding.progressContainer.visibility = View.VISIBLE
                binding.vpDetail.animate().alpha(0f)
            },
            doOnSuccess = {
                loadAdapter()
                binding.progressContainer.visibility = View.GONE
                binding.vpDetail.animate().alpha(1f)
            }
        )
    }

    private fun loadAdapter() {
        detailsLectoryCategoriesViewModel.detailCategory.value?.let { details ->
            val detailList = mutableListOf<DetailCategory>()
            for (i in details.indices) {
                if (details[i].type == arguments?.getString(TYPE)) {
                    detailList.add(details[i])
                }
            }
            detailsCategoryAdapter.submitList(detailList)
        }
    }

    companion object {
        const val TYPE = "type"
    }
}