package com.spravochnic.scbguide.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.spravochnic.scbguide.R
import com.spravochnic.scbguide.databinding.ViewMainBinding
import com.spravochnic.scbguide.databinding.ViewTipBinding
import com.spravochnic.scbguide.models.Main

class MainAdapter(private val onClickMain: (Int) -> Unit = {}): ListAdapter<Main, RecyclerView.ViewHolder>(MainDiffUtilCallback()) {

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position).viewType) {
            TYPE_TIP -> TYPE_TIP.hashCode()
            TYPE_MAIN -> TYPE_MAIN.hashCode()
            else -> {
                throw Exception("Invalid type main")
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE_TIP.hashCode() ->  TipViewHolder.getViewHolder(parent)
            TYPE_MAIN.hashCode() -> MainViewHolder.getViewHolder(parent)
            else -> throw Exception("Not found view holder type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItem(position).viewType) {
            TYPE_MAIN -> (holder as MainViewHolder).bind(getItem(position), onClickMain)
        }
    }

    class MainViewHolder(
        private val binding: ViewMainBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Main, onClickMain: (Int) -> Unit) {
            binding.apply {
                ivIconMain.setImageResource(item.icon)
                tvNameMain.text = item.name
                tvTopicsMain.text = root.context.getString(
                    R.string.main_tip,
                    item.topic
                )
                root.setOnClickListener {
                    onClickMain(item.type)
                }
            }
        }

        companion object {
            fun getViewHolder(parent: ViewGroup): MainViewHolder {
                val binding = ViewMainBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                return MainViewHolder(binding)
            }
        }
    }

    class TipViewHolder(
        private val binding:ViewTipBinding
    ): RecyclerView.ViewHolder(binding.root) {

        companion object {
            fun getViewHolder(parent: ViewGroup): TipViewHolder {
                val binding = ViewTipBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                return TipViewHolder (binding)
            }
        }
    }

    class MainDiffUtilCallback : DiffUtil.ItemCallback<Main>() {
        override fun areItemsTheSame(oldItem: Main, newItem: Main): Boolean { return oldItem.id == newItem.id }
        override fun areContentsTheSame(oldItem: Main, newItem: Main): Boolean { return oldItem == newItem }
    }

    companion object {
        const val TYPE_TIP = 1
        const val TYPE_MAIN = 2
    }
}