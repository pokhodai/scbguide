package com.spravochnic.scbguide.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
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
        DetailsCategoryViewPagerAdapter(onClickSeeAll = onClickSeeAll)
    }

    private val arg: DetailLectoryCategoryFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View = FragmentDetailCategoryBinding.inflate(inflater, container, false).apply {
        binding = this
        setToolbarTitle()
        initializeObservers()
        setObservers()
        setAdapter()
        checkIsDataLoaded()
        setListeners()
    }.root

    private fun setListeners() {
        binding.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun setToolbarTitle() {
        binding.toolbar.title = arg.name
    }

    private fun setAdapter() {
        binding.vpDetail.adapter = detailsCategoryAdapter
        binding.vpDetail.offscreenPageLimit = 3
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
                if (details[i].type == arg.type) {
                    detailList.add(details[i])
                }
            }
            detailsCategoryAdapter.submitList(detailList)
        }
    }

    private val onClickSeeAll:(String) -> Unit = {name ->
        findNavController().navigate(DetailLectoryCategoryFragmentDirections.actionDetailsCategoriesFragmentToDetailLectoryFragment(name = name))
    }
}