package com.savr.baseandroid.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.savr.baseandroid.R
import com.savr.baseandroid.common.Constant
import com.savr.baseandroid.databinding.ItemLoadingBinding
import com.savr.baseandroid.databinding.ItemMovieBinding
import com.savr.baseandroid.domain.model.MovieItemModel

class MovieAdapter : ListAdapter<MovieItemModel, RecyclerView.ViewHolder>(DiffCallback()) {

    private var onItemClickListener: ((MovieItemModel) -> Unit)? = null

    fun setOnItemClickListener(listener: (MovieItemModel) -> Unit) {
        onItemClickListener = listener
    }

    private val viewItemType = 0
    private val viewLoadingType = 1

    class ItemHolder(binding: ItemMovieBinding) : RecyclerView.ViewHolder(binding.root) {
        var title = binding.tvTitle
        var body = binding.tvOverview
        var poster = binding.ivPoster
        var root = binding.layParent
    }

    class LoadingViewHolder(binding: ItemLoadingBinding) : RecyclerView.ViewHolder(binding.root) {
        var progressBar = binding.shimmerViewPost
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            viewItemType -> {
                ItemHolder(
                    ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                )
            }
            viewLoadingType -> {
                LoadingViewHolder(
                    ItemLoadingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                )
            }
            else -> throw IllegalArgumentException("Unknown view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ItemHolder) {
            val data = getItem(position)
            holder.title.text = data.title
            holder.body.text = data.overview
            Glide.with(holder.poster.context)
                .load(Constant.BASE_POSTER_URL + data.poster_path)
                .apply(
                    RequestOptions()
                        .placeholder(R.drawable.empty_image)
                        .error(R.drawable.empty_image)
                        .skipMemoryCache(true)
                        .encodeQuality(100)
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .dontAnimate()
                )
                .into(holder.poster)

            holder.root.setOnClickListener {
                onItemClickListener?.let {
                    it(data)
                }
            }
        } else if (holder is LoadingViewHolder) {
            holder.progressBar.startShimmer()
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (getItem(position).id == 0) viewLoadingType else viewItemType
    }

    fun addLoadingItem() {
        val currentList = currentList.toMutableList()
        currentList.add(currentList.size, MovieItemModel())
        submitList(currentList)
    }

    fun removeLoadingItem() {
        val currentList = currentList.toMutableList()
        currentList.removeAt(currentList.size - 1)
        submitList(currentList)
    }

    fun clear() {
        val currentList = currentList.toMutableList()
        currentList.clear()
        submitList(currentList)
    }

    class DiffCallback : DiffUtil.ItemCallback<MovieItemModel>() {

        override fun areItemsTheSame(oldItem: MovieItemModel, newItem: MovieItemModel): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: MovieItemModel, newItem: MovieItemModel): Boolean {
            return oldItem == newItem
        }
    }
}