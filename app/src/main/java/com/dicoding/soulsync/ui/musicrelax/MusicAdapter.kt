package com.dicoding.soulsync.ui.musicrelax

import android.media.MediaPlayer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.soulsync.R
import com.dicoding.soulsync.model.Music

class MusicAdapter(private val musicList: List<Music>) :
    RecyclerView.Adapter<MusicAdapter.MusicViewHolder>() {

    private var currentMediaPlayer: MediaPlayer? = null
    private var currentPlayingHolder: MusicViewHolder? = null

    inner class MusicViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val songNameTextView: TextView = itemView.findViewById(R.id.songNameTextView)
        val artistTextView: TextView = itemView.findViewById(R.id.artistTextView) // Tambahkan ini
        val playButton: ImageButton = itemView.findViewById(R.id.playButton)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MusicViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_music, parent, false)
        return MusicViewHolder(view)
    }

    override fun onBindViewHolder(holder: MusicViewHolder, position: Int) {
        val music = musicList[position]

        holder.songNameTextView.text = music.name
        holder.artistTextView.text = music.artist // Set artist name

        holder.playButton.setOnClickListener {
            val isPlaying = currentPlayingHolder == holder
            if (isPlaying) {
                currentMediaPlayer?.pause()
                holder.playButton.setImageResource(R.drawable.play)
                currentPlayingHolder = null
                currentMediaPlayer = null
            } else {
                currentMediaPlayer?.release()
                currentPlayingHolder?.playButton?.setImageResource(R.drawable.pause)

                currentMediaPlayer = MediaPlayer.create(holder.itemView.context, music.audioResId)
                currentMediaPlayer?.start()
                holder.playButton.setImageResource(R.drawable.pause)
                currentPlayingHolder = holder
            }
        }
    }


    override fun getItemCount(): Int = musicList.size

    fun releaseMediaPlayer() {
        currentMediaPlayer?.release()
        currentMediaPlayer = null
    }
}
