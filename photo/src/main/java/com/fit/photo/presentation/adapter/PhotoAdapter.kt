package com.fit.photo.presentation.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.fit.photo.databinding.ItemPhotoBinding

class PhotoAdapter(private val context: Context, private val itemPhotoModelList: List<ItemPhotoModel>) : RecyclerView.Adapter<PhotoAdapter.PhotoViewHolder>() {

    inner class PhotoViewHolder(val binding: ItemPhotoBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        val view = ItemPhotoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PhotoViewHolder(view)
    }

    override fun onBindViewHolder(holder: PhotoAdapter.PhotoViewHolder, position: Int) {
        Glide.with(context)
            .load(itemPhotoModelList[position].imageUrl)
            .into(holder.binding.imagePhoto)
    }

    override fun getItemCount(): Int {
        return itemPhotoModelList.size
    }
}