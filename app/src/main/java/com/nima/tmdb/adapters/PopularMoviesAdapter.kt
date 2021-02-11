package com.nima.tmdb.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.nima.tmdb.R
import com.nima.tmdb.models.movie.popular.PopularModel
import com.nima.tmdb.utils.Constants
import kotlinx.android.synthetic.main.item_movie_category.view.*

class PopularMoviesAdapter(private val interaction: Interaction? = null, private val glide: RequestManager) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<PopularModel>() {

        override fun areItemsTheSame(
            oldItem: PopularModel,
            newItem: PopularModel
        ): Boolean  = oldItem.id == newItem.id


        override fun areContentsTheSame(
            oldItem: PopularModel,
            newItem: PopularModel
        ): Boolean = oldItem.equals(newItem)

    }
    private val differ = AsyncListDiffer(this, DIFF_CALLBACK)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return PopularMoviesViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_movie_category,
                parent,
                false
            ),
            interaction,
            glide
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is PopularMoviesViewHolder -> {
                holder.bind(differ.currentList[position])
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun submitList(list: List<PopularModel>) {
        differ.submitList(list)
    }

    class PopularMoviesViewHolder(
        itemView: View,
        private val interaction: Interaction?,
        private val glide: RequestManager
    ) : RecyclerView.ViewHolder(itemView) {

        fun bind(item: PopularModel) = with(itemView) {
            itemView.setOnClickListener { interaction?.onItemSelected(adapterPosition, item) }
            itemView.apply {
                txt_movieCategoryI_title.text = item.title
                txt_movieCategoryI_date.text = item.voteAverage.toString()
                glide.load(Constants.IMAGE_BASE_URL + item.posterPath)
                    .into(img_mainPageF_image)
            }
        }
    }

    interface Interaction {
        fun onItemSelected(position: Int, item: PopularModel)
    }
}