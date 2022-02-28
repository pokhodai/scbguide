package com.spravochnic.scbguide.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.spravochnic.scbguide.R
import com.spravochnic.scbguide.databinding.ViewMainBinding
import com.spravochnic.scbguide.databinding.ViewMainTitleBinding
import com.spravochnic.scbguide.databinding.ViewTipBinding
import com.spravochnic.scbguide.models.Main

sealed class MainItem(val itemId: Long) {
    data class MainWrap(val main: Main) : MainItem(main.id)
    object MainTip : MainItem(-1)
    object MainTitle : MainItem(-2)
}

class MainAdapter(private val onClickMain: (Int) -> Unit = {}) :
    ListAdapter<MainItem, RecyclerView.ViewHolder>(MainDiffUtilCallback()) {

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is MainItem.MainTitle -> TYPE_TITLE.hashCode()
            is MainItem.MainTip -> TYPE_TIP.hashCode()
            is MainItem.MainWrap -> TYPE_MAIN.hashCode()
            else -> {
                throw Exception("Invalid type main")
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE_TITLE.hashCode() -> TitleViewHolder.getViewHolder(parent)
            TYPE_TIP.hashCode() -> TipViewHolder.getViewHolder(parent)
            TYPE_MAIN.hashCode() -> MainViewHolder.getViewHolder(parent)
            else -> throw Exception("Not found view holder type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (val item = getItem(position)) {
            is MainItem.MainWrap -> (holder as MainViewHolder).bind(item.main, onClickMain)
            is MainItem.MainTip -> (holder as TipViewHolder)
            is MainItem.MainTitle -> (holder as TitleViewHolder)
        }
    }

    class MainViewHolder(
        private val binding: ViewMainBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Main, onClickMain: (Int) -> Unit) {
            binding.apply {
                ivIconMain.setImageResource(item.icon)
                tvNameMain.text = item.name
                if (item.topic == "14") {
                    tvTopicsMain.text = root.context.getString(
                        R.string.main_tip,
                        item.topic
                    )
                } else {
                    tvTopicsMain.text = root.context.getString(
                        R.string.main_level,
                        item.topic
                    )
                }
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
        private val binding: ViewTipBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        companion object {
            fun getViewHolder(parent: ViewGroup): TipViewHolder {
                val binding = ViewTipBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                return TipViewHolder(binding)
            }
        }
    }

    class TitleViewHolder(
        private val binding: ViewMainTitleBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        companion object {
            fun getViewHolder(parent: ViewGroup): TitleViewHolder {
                val binding = ViewMainTitleBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                return TitleViewHolder(binding)
            }
        }
    }

    class MainDiffUtilCallback : DiffUtil.ItemCallback<MainItem>() {
        override fun areItemsTheSame(oldItem: MainItem, newItem: MainItem): Boolean {
            return oldItem.itemId == newItem.itemId
        }

        override fun areContentsTheSame(oldItem: MainItem, newItem: MainItem): Boolean {
            return oldItem == newItem
        }
    }

    companion object {
        const val TYPE_TITLE = 1
        const val TYPE_TIP = 2
        const val TYPE_MAIN = 3
    }
}