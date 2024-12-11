package com.capstone.sampahin.ui.chat.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.capstone.sampahin.databinding.ItemTopicBinding

class TopicsAdapter(
    private val onItemSelected: (String) -> Unit
) : ListAdapter<String, TopicsAdapter.ViewHolder>(DIFF_CALLBACK) {

    inner class ViewHolder(val binding: ItemTopicBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(topic: String) {
            binding.tvTopicTitle.text = topic
            binding.root.setOnClickListener { onItemSelected(topic) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemTopicBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val topics = getItem(position)
        holder.bind(topics)
    }

    companion object {

        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<String>() {
            override fun areItemsTheSame(
                oldItem: String,
                newItem: String
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: String,
                newItem: String
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}
