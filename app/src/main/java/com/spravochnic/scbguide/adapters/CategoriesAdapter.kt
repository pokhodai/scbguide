package com.spravochnic.scbguide.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.spravochnic.scbguide.*
import com.spravochnic.scbguide.databinding.ViewCategoryBinding
import com.spravochnic.scbguide.databinding.ViewMainBinding
import com.spravochnic.scbguide.models.Categories
import com.spravochnic.scbguide.models.Main

class CategoriesAdapter: ListAdapter<Categories, CategoriesAdapter.CategoryViewHolder>(CategoriesDiffUtilCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        return CategoryViewHolder.getViewHolder(parent)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class CategoryViewHolder(
        private val binding: ViewCategoryBinding
    ): RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Categories) {
            binding.apply {
                tvNameCategory.text = item.name
                tvTopicsMain.text = root.context.getString(
                    R.string.main_tip,
                    item.numbers
                )
                ivIconCategory.setImageResource(getIconCategory(item.type))
            }
        }

        private fun getIconCategory(type: String): Int {
            when (type) {
                trafficlight -> {
                    return R.drawable.ic_trafficlight
                }
                arrowtranslation -> {
                    return R.drawable.ic_arrowtranslation
                }
                box -> {
                    return R.drawable.ic_box
                }
                relay -> {
                    return R.drawable.ic_relay
                }
                railchain -> {
                    return R.drawable.ic_railchain
                }
                centralization -> {
                    return R.drawable.ic_centralization
                }
                alsn -> {
                    return R.drawable.ic_alsn
                }
                edrive -> {
                    return R.drawable.ic_edrive
                }
                gatc -> {
                    return R.drawable.ic_gatc
                }
                transformer -> {
                    return R.drawable.ic_transformer
                }
                aps -> {
                    return R.drawable.ic_aps
                }
                station -> {
                    return R.drawable.ic_station
                }
                bmrc -> {
                    return R.drawable.ic_bmrc
                }
                bridge -> {
                    return R.drawable.ic_bridge
                }
            }
            return 0
        }

        companion object {
            fun getViewHolder(parent: ViewGroup): CategoryViewHolder {
                val binding =  ViewCategoryBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                return CategoryViewHolder(binding)
            }
        }

    }

    class CategoriesDiffUtilCallback : DiffUtil.ItemCallback<Categories>() {
        override fun areItemsTheSame(oldItem: Categories, newItem: Categories): Boolean { return oldItem.id == newItem.id }
        override fun areContentsTheSame(oldItem: Categories, newItem: Categories): Boolean { return oldItem == newItem }
    }


}