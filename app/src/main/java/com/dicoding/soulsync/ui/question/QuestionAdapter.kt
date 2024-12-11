package com.dicoding.soulsync.ui.question

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.soulsync.R
import com.dicoding.soulsync.model.QuestionItem

class QuestionAdapter(
    private val onAnswerSelected: (String, Int) -> Unit // Callback dengan Int
) : RecyclerView.Adapter<QuestionAdapter.QuestionViewHolder>() {

    private val questions = mutableListOf<QuestionItem>()

    fun submitList(newQuestions: List<QuestionItem>) {
        questions.clear()
        questions.addAll(newQuestions)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuestionViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_question, parent, false)
        return QuestionViewHolder(view, onAnswerSelected)
    }

    override fun onBindViewHolder(holder: QuestionViewHolder, position: Int) {
        val question = questions[position]
        holder.bind(question)
    }

    override fun getItemCount(): Int = questions.size

    class QuestionViewHolder(
        itemView: View,
        private val onAnswerSelected: (String, Int) -> Unit
    ) : RecyclerView.ViewHolder(itemView) {
        private val questionText: TextView = itemView.findViewById(R.id.tvQuestion)
        private val optionsGroup: RadioGroup = itemView.findViewById(R.id.radioGroupOptions)
        private val inputField: EditText = itemView.findViewById(R.id.etInput)

        fun bind(question: QuestionItem) {
            questionText.text = question.question

            if (question.options == null) {
                optionsGroup.visibility = View.GONE
                inputField.visibility = View.VISIBLE

                inputField.setOnFocusChangeListener { _, hasFocus ->
                    if (!hasFocus) {
                        val answerText = inputField.text.toString()
                        val answer = answerText.toIntOrNull() ?: 0 // Default ke 0 jika gagal parsing
                        onAnswerSelected(question.keyword, answer)
                    }
                }
            } else {
                optionsGroup.visibility = View.VISIBLE
                inputField.visibility = View.GONE

                optionsGroup.removeAllViews()
                question.options.forEach { (key, value) ->
                    val radioButton = RadioButton(itemView.context).apply {
                        id = key.toInt()
                        text = value
                    }
                    optionsGroup.addView(radioButton)
                }

                optionsGroup.setOnCheckedChangeListener { _, checkedId ->
                    onAnswerSelected(question.keyword, checkedId)
                }
            }
        }
    }
}
