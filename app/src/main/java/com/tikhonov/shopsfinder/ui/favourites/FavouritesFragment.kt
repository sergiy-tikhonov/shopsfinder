package com.tikhonov.shopsfinder.ui.favourites

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.tikhonov.shopsfinder.ui.details.DetailsFragment
import com.tikhonov.shopsfinder.databinding.FragmentFavouritesBinding
import com.tikhonov.shopsfinder.data.room.FavoritePoi
import com.tikhonov.shopsfinder.util.navigateTo
import com.tikhonov.shopsfinder.util.setNavigationUp
import com.tikhonov.shopsfinder.util.setToolbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavouritesFragment : Fragment(), FavouritesListAdapter.OnClickListener {

    private val viewModel by viewModels<FavouritesViewModel>()
    // viewBinding
    private var _binding: FragmentFavouritesBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFavouritesBinding.inflate(inflater, container, false)
        return  binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setToolbar(toolbar = binding.toolbar, showMenu = true)
        setNavigationUp(binding.toolbar)
        val favouritesListAdapter =
            FavouritesListAdapter(this)
        binding.recViewFavourites.apply {
            adapter = favouritesListAdapter
            layoutManager = LinearLayoutManager(view.context)
            setHasFixedSize(true)
        }

        viewModel.favouriteList.observe(viewLifecycleOwner, {
            favouritesListAdapter.setItems(it)
        })

    }

    override fun onDetail(favourirePoi: FavoritePoi) {
        navigateTo(
                fragment = DetailsFragment.newInstance(placeId = favourirePoi.placeId), addToBackStack = true)
    }

    override fun onRemove(favourirePoi: FavoritePoi) {
        viewModel.removeFavourite(favourirePoi)
    }
}