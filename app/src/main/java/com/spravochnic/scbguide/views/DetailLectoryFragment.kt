package com.spravochnic.scbguide.views

import android.os.Bundle
import android.transition.Slide
import android.transition.Transition
import android.transition.TransitionManager
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.appbar.AppBarLayout
import com.spravochnic.scbguide.databinding.FragmentDetailLectoryBinding
import com.spravochnic.scbguide.glideOtherUrl
import com.spravochnic.scbguide.viewModels.DetailsLectoryCategoriesViewModel


class DetailLectoryFragment : Fragment(), AppBarLayout.OnOffsetChangedListener {

    lateinit var binding: FragmentDetailLectoryBinding

    private val detailsLectoryCategoriesViewModel: DetailsLectoryCategoriesViewModel by activityViewModels()

    private val arg: DetailLectoryFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View = FragmentDetailLectoryBinding.inflate(inflater, container, false).apply {
        binding = this
        bindData()
        setListener()
    }.root

    private fun setListener() {
        binding.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun bindData() {
        binding.run {
            detailsLectoryCategoriesViewModel.detailCategory.value?.let { list ->
                for (i in list.indices) {
                    if (list[i].name == arg.name) {
                        bottomSheetDetail.title.text = list[i].name
                        bottomSheetDetail.description.text = list[i].description
                        image.glideOtherUrl(list[i].image)
                        cltCategories.title = list[i].name
                    }
                }
                appBarLayout.addOnOffsetChangedListener(this@DetailLectoryFragment)
            }
        }
    }

    override fun onOffsetChanged(appBarLayout: AppBarLayout?, verticalOffset: Int) {
        if (verticalOffset == 0) {
            animateTransition()
            binding.bottomSheetDetail.title.visibility = View.VISIBLE
        } else {
            animateTransition()
            binding.bottomSheetDetail.title.visibility = View.GONE
        }
    }

    private fun animateTransition() {
        val transition: Transition = Slide(Gravity.TOP)
        transition.duration = 100
        transition.addTarget(binding.bottomSheetDetail.title)
        TransitionManager.beginDelayedTransition(binding.root, transition)
    }
}