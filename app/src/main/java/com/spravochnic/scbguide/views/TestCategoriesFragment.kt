package com.spravochnic.scbguide.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.spravochnic.scbguide.R
import com.spravochnic.scbguide.adapters.MainAdapter
import com.spravochnic.scbguide.adapters.TestCategoriesAdapter
import com.spravochnic.scbguide.databinding.FragmentMainBinding
import com.spravochnic.scbguide.databinding.FragmentTestCategoriesBinding
import com.spravochnic.scbguide.models.getTestCategories

class TestCategoriesFragment : Fragment() {

    lateinit var binding: FragmentTestCategoriesBinding

    private val testCategoriesAdapter by lazy {
        TestCategoriesAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View = FragmentTestCategoriesBinding.inflate(inflater, container, false).apply {
        binding = this
        initAdapter()
        setListeners()
    }.root

    private fun setListeners() {
        binding.run {
            mtTestCategories.setNavigationOnClickListener {
                findNavController().popBackStack()
            }
        }
    }

    private fun initAdapter() {
        binding.rvTestCategories.adapter = testCategoriesAdapter
        testCategoriesAdapter.submitList(getTestCategories())
    }
}