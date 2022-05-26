package com.example.musicapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.musicapp.databinding.ListSongsBinding
import com.example.musicapp.model.Music

class SongListAdapter(
    val songs: List<Music>
) : RecyclerView.Adapter<SongListAdapter.ViewHolder>() {
    inner class ViewHolder(private val binding: ListSongsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            binding.songName.text = songs[position].title
            binding.artistName.text = songs[position].artist
            binding.time.text = songs[position].duration
            binding.image.setImageURI(songs[position].uri)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongListAdapter.ViewHolder {
        return ViewHolder(
            ListSongsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: SongListAdapter.ViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int {
        return songs.size
    }

}