package com.nima.tmdb.framewrok.presentation.mainPage

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupWindow
import android.widget.RelativeLayout
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.nima.tmdb.R
import com.nima.tmdb.databinding.ItemMovieCategoryBinding
import com.nima.tmdb.databinding.PopupmenuBinding
import com.nima.tmdb.business.domain.model.trend.TrendModel
import com.nima.tmdb.utils.Constants

class TrendMoviesAdapter(
    private val interaction: Interaction? = null,
    private val glide: RequestManager,
    private val ctx: Context
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<TrendModel>() {

        override fun areItemsTheSame(
            oldItem: TrendModel,
            newItem: TrendModel
        ): Boolean = oldItem.id == newItem.id


        override fun areContentsTheSame(
            oldItem: TrendModel,
            newItem: TrendModel
        ): Boolean = oldItem.equals(newItem)

    }
    private val differ = AsyncListDiffer(this, DIFF_CALLBACK)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return TrendMovieViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_movie_category,
                parent,
                false
            ),
            interaction,
            glide,
            ctx
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is TrendMovieViewHolder -> {
                holder.bind(differ.currentList[position])
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun submitList(list: List<TrendModel?>) {
        differ.submitList(list)
    }

    class TrendMovieViewHolder(
        itemView: View,
        private val interaction: Interaction?,
        private val glide: RequestManager,
        private val ctx: Context
    ) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemMovieCategoryBinding.bind(itemView)
        private lateinit var myPopupWindow: PopupWindow
        fun bind(item: TrendModel) = with(itemView) {
            val view = setPopUpWindow()
            val popBinding = PopupmenuBinding.bind(view)
            binding.btnMainPageFMenu.setOnClickListener {
                myPopupWindow.showAsDropDown(it, -80, 0)
                popBinding.apply {
                    addToListPopupMenu.setOnClickListener {
                        interaction?.addToList(bindingAdapterPosition, item)
                    }
                    favoritePopupMenu.setOnClickListener {
                        interaction?.addToFavorite(bindingAdapterPosition, item)
                    }
                    watchlistPopupMenu.setOnClickListener {
                        interaction?.addToWatchList(bindingAdapterPosition, item)
                    }
                }
            }
            itemView.setOnClickListener { interaction?.onTrendItemSelected(adapterPosition, item) }
            binding.apply {
                imgMainPageFImage.setOnClickListener {
                    interaction?.onTrendItemSelected(bindingAdapterPosition, item)
                }
                txtMovieCategoryITitle.text = item.title
                txtMovieCategoryIDate.text = item.releaseDate
                glide.load(Constants.IMAGE_BASE_URL + item.posterPath)
                    .into(imgMainPageFImage)
            }
        }
        private fun setPopUpWindow(): View {
            val inflater: LayoutInflater =
                ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val view = inflater.inflate(R.layout.popupmenu, null)
            myPopupWindow = PopupWindow(
                view,
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                true
            )
            return view
        }
    }

    interface Interaction {
        fun onTrendItemSelected(position: Int, item: TrendModel)
        fun addToList(position: Int, item: TrendModel)
        fun addToFavorite(position: Int, item: TrendModel)
        fun addToWatchList(position: Int, item: TrendModel)
    }

}