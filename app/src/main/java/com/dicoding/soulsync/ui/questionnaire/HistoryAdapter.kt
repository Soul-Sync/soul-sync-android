package com.dicoding.soulsync.ui.questionnaire

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.soulsync.R
import com.dicoding.soulsync.model.QuestionnaireHistory
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

class HistoryAdapter : RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>() {

    private val historyList = mutableListOf<QuestionnaireHistory>()

    fun submitList(list: List<QuestionnaireHistory>) {
        historyList.clear()
        historyList.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_history, parent, false)
        return HistoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        holder.bind(historyList[position])
    }

    override fun getItemCount(): Int = historyList.size

    class HistoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvDate: TextView = itemView.findViewById(R.id.tvDate)
        private val tvStatus: TextView = itemView.findViewById(R.id.tvStatus)

        fun bind(history: QuestionnaireHistory) {
            // Format tanggal tanpa waktu
            val formattedDate = formatDateWithoutTime(history.date)
            tvDate.text = formattedDate
            tvStatus.text = history.status
        }

        private fun formatDateWithoutTime(inputDate: String): String {
            return try {
                val zonedDateTime = ZonedDateTime.parse(inputDate)
                val formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy", Locale("id", "ID"))
                zonedDateTime.format(formatter)
            } catch (e: Exception) {
                "Invalid date"
            }
        }
    }
}
