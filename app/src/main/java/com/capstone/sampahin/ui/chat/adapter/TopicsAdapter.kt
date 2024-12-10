package com.capstone.sampahin.ui.chat.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.capstone.sampahin.data.chat.ChatRequest
import com.capstone.sampahin.databinding.ItemTopicBinding

class TopicsAdapter(
    private var topicsTitle: List<ChatRequest>,
    private val onItemSelectedCallback: OnItemSelected
) : RecyclerView.Adapter<TopicsAdapter.ViewHolder>() {

    interface OnItemSelected {
        fun onItemClicked(itemID: Int, itemTitle: String)
    }

    inner class ViewHolder(val binding: ItemTopicBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemTopicBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.binding.tvTopicTitle.text = topicsTitle[position].toString()
        holder.itemView.setOnClickListener {
            onItemSelectedCallback.onItemClicked(position, topicsTitle[position].toString())
        }

    }

    override fun getItemCount(): Int {
        return topicsTitle.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setTopics(newTopics: List<ChatRequest>) {
        topicsTitle = newTopics
        notifyDataSetChanged()
    }


}