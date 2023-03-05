package com.ivantsov.giphysearcher.screens.searchGifs

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.ivantsov.giphysearcher.data.model.Gif
import com.ivantsov.giphysearcher.databinding.GifListItemBinding

class SearchGifsAdapter(
    private val gifList: MutableList<Gif> = mutableListOf(),
    val onClick: (String) -> Unit
) : RecyclerView.Adapter<SearchGifViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchGifViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = GifListItemBinding.inflate(inflater, parent, false)
        return SearchGifViewHolder(binding)
    }

    override fun getItemCount(): Int = gifList.size

    override fun onBindViewHolder(holder: SearchGifViewHolder, position: Int) {
        holder.binding.gifListItemUrl.setOnClickListener {
            onClick(gifList[position].id ?: "")
        }
        gifList[position].images?.original?.url.let {
            Glide.with(holder.itemView.context).asGif().load(it)
                .placeholder(ColorDrawable(Color.BLACK))
                .diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.binding.gifListItemUrl)
        }
    }
}


