package com.tikhonov.shopsfinder.ui.favourites

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tikhonov.shopsfinder.databinding.FavouritesItemListBinding
import com.tikhonov.shopsfinder.data.room.FavoritePoi

class FavouritesListAdapter(private val listener: OnClickListener): RecyclerView.Adapter<FavouritesListAdapter.FavouriteViewHolder>() {

    interface OnClickListener{
        fun onDetail(favouritePoi: FavoritePoi)
        fun onRemove(favouritePoi: FavoritePoi)
    }

    private var items = mutableListOf<FavoritePoi>()

    fun setItems(favouritesList: List<FavoritePoi>) {
        items = favouritesList.toMutableList()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavouriteViewHolder {
        val binding = FavouritesItemListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavouriteViewHolder(
            binding
        )
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: FavouriteViewHolder, position: Int) {
        holder.binding.apply {
            textViewShopName.text = items[position].name
            textViewAddress.text = items[position].address
            buttonDetails.setOnClickListener {
                listener.onDetail(items[position])
            }
            buttonRemove.setOnClickListener {
                listener.onRemove(items[position])
            }
        }

    }

    class FavouriteViewHolder(val binding: FavouritesItemListBinding) : RecyclerView.ViewHolder(binding.root)
}