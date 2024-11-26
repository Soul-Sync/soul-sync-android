package com.dicoding.soulsync.ui.musicrelax

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.soulsync.R

class MusicRelaxAdapter(private var musicList: List<String>, private var musicImages: List<Int>) :
    RecyclerView.Adapter<MusicRelaxAdapter.MusicViewHolder>() {

    class MusicViewHolder(val container: LinearLayout) : RecyclerView.ViewHolder(container)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MusicViewHolder {
        val container = LinearLayout(parent.context).apply {
            orientation = LinearLayout.HORIZONTAL
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            setPadding(16, 16, 16, 16)
        }

        // Gambar untuk lagu
        val imageView = ImageView(parent.context).apply {
            id = View.generateViewId()
            layoutParams = LinearLayout.LayoutParams(
                80,  // Ukuran gambar (misalnya 80dp)
                80   // Sama dengan tinggi
            ).apply {
                setMargins(0, 0, 16, 0)  // Memberikan jarak antara gambar dan teks
            }
            scaleType = ImageView.ScaleType.CENTER_CROP  // Agar gambar terpotong dengan benar
        }
        container.addView(imageView)

        // Title TextView
        val title = TextView(parent.context).apply {
            id = View.generateViewId()
            layoutParams = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f).apply {
                setMargins(16, 0, 0, 0)  // Memberikan margin ke kiri
            }
            textSize = 16f
            setPadding(16, 0, 0, 0)  // Memberikan padding kiri agar teks tidak terlalu mepet
        }
        container.addView(title)

        // Play Button
        val playButton = ImageButton(parent.context).apply {
            id = View.generateViewId()
            layoutParams = LinearLayout.LayoutParams(
                100,  // Ukuran tetap, misalnya 100dp
                100   // Sama dengan tinggi
            ).apply {
                setMargins(16, 0, 0, 0)  // Jika ingin memberi jarak antar elemen
            }
            setImageResource(R.drawable.play)
            background = ContextCompat.getDrawable(context, R.drawable.circle_background)  // Pastikan ini adalah lingkaran
            scaleType = ImageView.ScaleType.CENTER_INSIDE  // Agar gambar tidak terdistorsi
        }
        container.addView(playButton)

        return MusicViewHolder(container)
    }

    override fun onBindViewHolder(holder: MusicViewHolder, position: Int) {
        val imageView = holder.container.getChildAt(0) as ImageView
        imageView.setImageResource(musicImages[position])  // Mengatur gambar dari list `musicImages`

        val title = holder.container.getChildAt(1) as TextView
        title.text = musicList[position]

        val playButton = holder.container.getChildAt(2) as ImageButton
        playButton.setOnClickListener {
            Toast.makeText(holder.container.context, "Playing ${musicList[position]}", Toast.LENGTH_SHORT).show()
        }
    }

    override fun getItemCount(): Int = musicList.size

    // Fungsi untuk memperbarui data
    @SuppressLint("NotifyDataSetChanged")
    fun updateData(newMusicList: List<String>, newMusicImages: List<Int>) {
        musicList = newMusicList
        musicImages = newMusicImages
        notifyDataSetChanged()
    }
}


