package com.spravochnic.scbguide.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.spravochnic.scbguide.DefaultNetworkEventObserver
import com.spravochnic.scbguide.R
import com.spravochnic.scbguide.State
import com.spravochnic.scbguide.adapters.MainAdapter
import com.spravochnic.scbguide.adapters.MainItem
import com.spravochnic.scbguide.databinding.FragmentMainBinding
import com.spravochnic.scbguide.models.Main
import com.spravochnic.scbguide.models.getMainCategories
import com.spravochnic.scbguide.viewModels.DetailsLectoryCategoriesViewModel

class MainFragment : Fragment() {

    private lateinit var binding: FragmentMainBinding

    private val detailsLectoryCategoriesViewModel: DetailsLectoryCategoriesViewModel by activityViewModels()

    private lateinit var onDetailCategory: DefaultNetworkEventObserver

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View = FragmentMainBinding.inflate(inflater, container, false).apply {
        binding = this
        loadMainData()
        initializeObservers()
        setObservers()
        checkIsDataLoaded()
    }.root

    private fun initializeObservers() {
        onDetailCategory = DefaultNetworkEventObserver(
            anchorView = binding.root,
            doOnLoading = {
                hideMainScreen()
            },
            doOnSuccess = {
                showProgress()
            }
        )
    }

    private fun showProgress() {
        binding.run {
            progress.alpha = 0f
            rvMainCategory.visibility = View.VISIBLE
        }
    }

    private fun hideMainScreen() {
        binding.run {
            progress.animate().alpha(1f)
            rvMainCategory.visibility = View.GONE
        }
    }

    private fun setObservers() {
        detailsLectoryCategoriesViewModel.detailCategoryResult.observe(viewLifecycleOwner, onDetailCategory)
    }

    private fun checkIsDataLoaded() {
        detailsLectoryCategoriesViewModel.run {
            if (detailCategoryResult.value?.peekContent() != State.SUCCESS) getDetailsLectoryCategories()
        }
    }

    private fun loadMainData() {
        val adapter = MainAdapter(onClickMain = onClickMain)
        val data = mutableListOf<MainItem>()
        data.add(MainItem.MainTitle)
        data.add(MainItem.MainTip)
        data.addAll(getMainCategories().map { main ->
            MainItem.MainWrap(main)
        })
        binding.rvMainCategory.adapter = adapter
        adapter.submitList(data)
    }

    private val onClickMain: (Int) -> Unit = { type ->
        if (type == 1) {
            findNavController().navigate(MainFragmentDirections.actionMainFragmentToLectoryCategoriesFragment())
        } else if (type == 2) {
            findNavController().navigate(MainFragmentDirections.actionMainFragmentToTestCategoriesFragment())
        }
    }

    override fun onStart() {
        super.onStart()
        getStatusBarColor()
    }

    private fun getStatusBarColor() {
        requireActivity().window.statusBarColor =
            ContextCompat.getColor(requireContext(), R.color.main_screen_color)
    }
}