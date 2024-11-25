package com.dicoding.soulsync.ui.dailytherapy

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.soulsync.databinding.ItemDailyTherapyBinding
import com.dicoding.soulsync.model.DailyTherapy

class DailyTherapyAdapter(
    private val onTherapyChecked: (DailyTherapy, Boolean) -> Unit
) : ListAdapter<DailyTherapy, DailyTherapyAdapter.DailyTherapyViewHolder>(DailyTherapyDiffCallback()) {

    inner class DailyTherapyViewHolder(private val binding: ItemDailyTherapyBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(therapy: DailyTherapy) {
            binding.textViewTitle.text = therapy.title
            binding.checkBox.isChecked = therapy.isCompleted

            binding.checkBox.setOnCheckedChangeListener { _, isChecked ->
                onTherapyChecked(therapy, isChecked)
            }
        }
    }

    class DailyTherapyDiffCallback : DiffUtil.ItemCallback<DailyTherapy>() {
        override fun areItemsTheSame(oldItem: DailyTherapy, newItem: DailyTherapy): Boolean {
            return oldItem.title == newItem.title
        }

        override fun areContentsTheSame(oldItem: DailyTherapy, newItem: DailyTherapy): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DailyTherapyViewHolder {
        val binding = ItemDailyTherapyBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DailyTherapyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DailyTherapyViewHolder, position: Int) {
        val therapy = getItem(position)
        holder.bind(therapy)
    }
}
