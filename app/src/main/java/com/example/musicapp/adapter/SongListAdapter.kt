package com.example.musicapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.musicapp.databinding.ListSongsBinding
import com.example.musicapp.model.Music


class SongListAdapter(
    val songs: List<Music>,
    private val context: Context,
    private val listener: ClickListener
) : RecyclerView.Adapter<SongListAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: ListSongsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            binding.data = songs[position]
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongListAdapter.ViewHolder {
        return ViewHolder(
            ListSongsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: SongListAdapter.ViewHolder, position: Int) {
        holder.bind(position)

        holder.itemView.setOnClickListener {
            listener.cLick(songs[position])
        }
    }

    override fun getItemCount(): Int {
        return songs.size
    }

    interface ClickListener{
        fun cLick(id : Music)
    }

}