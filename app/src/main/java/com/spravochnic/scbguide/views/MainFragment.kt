package com.spravochnic.scbguide.views

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.spravochnic.scbguide.R
import com.spravochnic.scbguide.adapters.MainAdapter
import com.spravochnic.scbguide.databinding.FragmentMainBinding
import com.spravochnic.scbguide.models.Main
import com.spravochnic.scbguide.models.getMainCategories
import com.spravochnic.scbguide.viewModels.MainViewModel

class MainFragment : Fragment() {

    private lateinit var binding: FragmentMainBinding

    private val viewModel: MainViewModel by activityViewModels()

    private val mainAdapter by lazy {
        MainAdapter(onClickMain = onClickMain)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View = FragmentMainBinding.inflate(inflater, container, false).apply {
        binding = this
        initAdapter()
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.loadCategoriesLectory()
    }

    private fun initAdapter() {
        binding.rvMainCategoryMain.adapter = mainAdapter
        mainAdapter.submitList(getMainCategories())
    }

    private val onClickMain: (Int) -> Unit = { type ->
        if (type == 1) {
            findNavController().navigate(MainFragmentDirections.actionMainFragmentToCategoriesFragment())
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