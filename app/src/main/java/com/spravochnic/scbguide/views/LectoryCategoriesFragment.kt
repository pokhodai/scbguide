package com.spravochnic.scbguide.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.spravochnic.scbguide.R
import com.spravochnic.scbguide.adapters.CategoriesAdapter
import com.spravochnic.scbguide.databinding.FragmentCategoriesBinding
import com.spravochnic.scbguide.models.getLectoryCategories

class LectoryCategoriesFragment : Fragment() {

    private lateinit var binding: FragmentCategoriesBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View = FragmentCategoriesBinding.inflate(inflater, container, false).apply {
        binding = this
        loadLectoryCategoriesData()
        setListeners()
    }.root

    private fun loadLectoryCategoriesData() {
        val adapter = CategoriesAdapter(onClickCategory = onClickCategory)
        binding.rvCategories.adapter = adapter
        adapter.submitList(getLectoryCategories())
    }

    private fun setListeners() {
        binding.mtCategories.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }

    private val onClickCategory: (String, String) -> Unit = { type, name ->
        findNavController().navigate(LectoryCategoriesFragmentDirections.actionCategoriesFragmentToDetailsCategoriesFragment(type = type, name = name))
    }
}