package com.dicoding.soulsync.ui.question

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.soulsync.R
import com.dicoding.soulsync.model.Question

class QuestionAdapter(private var questions: List<Question>) :
    RecyclerView.Adapter<QuestionAdapter.QuestionViewHolder>() {

    // Metode untuk memperbarui data adapter
    fun updateData(newQuestions: List<Question>) {
        this.questions = newQuestions
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuestionViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_question, parent, false)
        return QuestionViewHolder(view)
    }

    override fun onBindViewHolder(holder: QuestionViewHolder, position: Int) {
        holder.bind(questions[position])
    }

    override fun getItemCount(): Int = questions.size

    inner class QuestionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val questionTextView: TextView = itemView.findViewById(R.id.questionTextView)
        private val optionsRadioGroup: RadioGroup = itemView.findViewById(R.id.optionsRadioGroup)

        fun bind(question: Question) {
            questionTextView.text = question.question

            optionsRadioGroup.removeAllViews()

            question.options?.forEach { (key, value) ->
                val radioButton = RadioButton(itemView.context).apply {
                    text = value
                    id = key.toInt()
                }
                optionsRadioGroup.addView(radioButton)
            } ?: run {
                val radioButton = RadioButton(itemView.context).apply {
                    text = "Tidak ada opsi"
                    isEnabled = false
                }
                optionsRadioGroup.addView(radioButton)
            }
        }
    }
}
