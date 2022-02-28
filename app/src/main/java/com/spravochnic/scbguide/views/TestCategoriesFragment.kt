package com.spravochnic.scbguide.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.spravochnic.scbguide.adapters.CategoriesAdapter
import com.spravochnic.scbguide.databinding.FragmentTestCategoriesBinding
import com.spravochnic.scbguide.models.getTestCategories

class TestCategoriesFragment : Fragment() {

    lateinit var binding: FragmentTestCategoriesBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View = FragmentTestCategoriesBinding.inflate(inflater, container, false).apply {
        binding = this
        loadTestCategoriesData()
        setListeners()
    }.root

    private fun loadTestCategoriesData() {
        val adapter = CategoriesAdapter()
        binding.rvTestCategories.adapter = adapter
        adapter.submitList(getTestCategories())
    }

    private fun setListeners() {
        binding.mtTestCategories.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }
}