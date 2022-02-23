package com.spravochnic.scbguide.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.spravochnic.scbguide.databinding.ViewDetailCategoryBinding
import com.spravochnic.scbguide.databinding.ViewMainBinding
import com.spravochnic.scbguide.glideOtherUrl
import com.spravochnic.scbguide.models.DetailCategory
import com.spravochnic.scbguide.models.Main
import com.spravochnic.scbguide.setCutText

class DetailsCategoryViewPagerAdapter
    : ListAdapter<DetailCategory, DetailsCategoryViewPagerAdapter.DetailsCategoryViewHolder>(DetailsCategoryDiffUtilCallback())
{

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailsCategoryViewHolder {
        return DetailsCategoryViewHolder.getViewHolder(parent)
    }

    override fun onBindViewHolder(holder: DetailsCategoryViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class DetailsCategoryViewHolder(
        private val binding: ViewDetailCategoryBinding
    ): RecyclerView.ViewHolder(binding.root) {

        fun bind(item: DetailCategory) {
            binding.apply {
                tvTitle.text = item.name
                ivDetails.glideOtherUrl(item.image)
                tvDescription.setCutText(item.description)
            }
        }

        companion object {
            fun getViewHolder(parent: ViewGroup): DetailsCategoryViewHolder {
                val binding = ViewDetailCategoryBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                return DetailsCategoryViewHolder(binding)
            }
        }
    }

    class DetailsCategoryDiffUtilCallback : DiffUtil.ItemCallback<DetailCategory>() {
        override fun areItemsTheSame(oldItem: DetailCategory, newItem: DetailCategory): Boolean { return oldItem.id == newItem.id }
        override fun areContentsTheSame(oldItem: DetailCategory, newItem: DetailCategory): Boolean { return oldItem == newItem }
    }


}