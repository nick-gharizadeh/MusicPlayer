package com.example.musicplayer.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.musicplayer.R
import com.example.musicplayer.data.Music
import com.example.musicplayer.databinding.MusicItemBinding


//typealias ClickHandlerCategory = (Category) -> Unit

class MusicAdapter() :
    ListAdapter<Music, MusicAdapter.ItemHolder>(MusicDiffCallback) {
    class ItemHolder(val binding: MusicItemBinding) : RecyclerView.ViewHolder(binding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        val binding: MusicItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.music_item,
            parent, false
        )
        return ItemHolder(binding)

    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        val music = getItem(position)
        holder.binding.music = music

    }
}


object MusicDiffCallback : DiffUtil.ItemCallback<Music>() {

    override fun areItemsTheSame(oldItem: Music, newItem: Music): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Music, newItem: Music): Boolean {
        return oldItem.path == newItem.path
    }
}