package com.spravochnic.scbguide.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.spravochnic.scbguide.*
import com.spravochnic.scbguide.databinding.ViewCategoryBinding
import com.spravochnic.scbguide.models.Categories

class CategoriesAdapter(
    private val onClickCategory: (String, String) -> Unit = { type: String, name: String -> },
) : ListAdapter<Categories, CategoriesAdapter.CategoryViewHolder>(CategoriesDiffUtilCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        return CategoryViewHolder.getViewHolder(parent)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.bind(getItem(position), onClickCategory)
    }

    class CategoryViewHolder(
        private val binding: ViewCategoryBinding,
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Categories, onClickCategory: (String, String) -> Unit) {
            binding.apply {
                tvNameCategory.text = item.name
                tvTopicsMain.text = root.context.getString(
                    R.string.main_tip,
                    item.topic
                )
                ivIconCategory.setImageResource(item.icon)
                root.setOnClickListener {
                    onClickCategory(item.type, item.name)
                }
            }
        }

        companion object {
            fun getViewHolder(parent: ViewGroup): CategoryViewHolder {
                val binding = ViewCategoryBinding.inflate(
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