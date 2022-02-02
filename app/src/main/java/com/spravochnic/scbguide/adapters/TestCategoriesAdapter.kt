package com.spravochnic.scbguide.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.spravochnic.scbguide.R
import com.spravochnic.scbguide.databinding.ViewTestCategoryBinding
import com.spravochnic.scbguide.models.TestCategories

class TestCategoriesAdapter: ListAdapter<TestCategories, TestCategoriesAdapter.TestCategoriesViewHolder>(TestCategoriesDiffUtilCallback()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TestCategoriesViewHolder {
        return TestCategoriesViewHolder.getViewHolder(parent)
    }

    override fun onBindViewHolder(holder: TestCategoriesViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class TestCategoriesViewHolder(
        private val binding: ViewTestCategoryBinding
    ): RecyclerView.ViewHolder(binding.root) {

        fun bind(item: TestCategories) {
            binding.apply {
                ivTestCategories.setImageResource(item.icon)
                tvNameTestCategories.text = item.name
                tvQuestionTestCategories.text = root.context.getString(
                    R.string.test_question,
                    item.question
                )
            }
        }

        companion object {
            fun getViewHolder(parent: ViewGroup): TestCategoriesViewHolder {
                val binding =  ViewTestCategoryBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                return TestCategoriesViewHolder(binding)
            }
        }
    }

    class TestCategoriesDiffUtilCallback : DiffUtil.ItemCallback<TestCategories>() {
        override fun areItemsTheSame(oldItem: TestCategories, newItem: TestCategories): Boolean { return oldItem.id == newItem.id }
        override fun areContentsTheSame(oldItem: TestCategories, newItem: TestCategories): Boolean { return oldItem == newItem }
    }
}