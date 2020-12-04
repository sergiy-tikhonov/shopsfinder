package com.tikhonov.shopsfinder.ui.details

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.tikhonov.shopsfinder.R
import com.tikhonov.shopsfinder.data.model.PoiDetails
import com.tikhonov.shopsfinder.databinding.FragmentDetailsBinding
import com.tikhonov.shopsfinder.data.room.FavoritePoi
import com.tikhonov.shopsfinder.util.setNavigationUp
import com.tikhonov.shopsfinder.util.setToolbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailsFragment : Fragment() {

    private lateinit var placeId: String
    private val viewModel by viewModels<DetailsViewModel>()
    // viewBinding
    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            placeId = it.getString(ARG_PLACE_ID)!!
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return  binding.root
    }


    class WebClientBrowser: WebViewClient() {
        override fun shouldOverrideUrlLoading(view: WebView, url:String): Boolean {
            return false
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setToolbar(toolbar = binding.toolbar, showMenu = true)
        setNavigationUp(binding.toolbar)
        viewModel.getPlaceDetails(placeId)

        viewModel.placeDetails.observe(viewLifecycleOwner, { poiDetails ->
            updateUi(poiDetails)
        })

        binding.webView.apply {
            webViewClient = WebClientBrowser()
            settings.javaScriptEnabled = true
        }

        binding.fabAddToFavourites.setOnClickListener {
            lifecycleScope.launch {
                viewModel.placeDetails.value?.let {
                    val favouritePoi = FavoritePoi(
                        placeId = it.placeId,
                        address = it.address ?: "",
                        name = it.name ?: ""
                    )
                    viewModel.saveFavouritePoi(favouritePoi)
                }
            }
            Toast.makeText(requireContext(), getString(R.string.shopWasSaved), Toast.LENGTH_SHORT)
                .show()
        }
    }

    private fun updateUi(poiDetails: PoiDetails) {
        binding.textViewTitle.text = poiDetails.name ?: ""
        binding.textViewAddress.text = poiDetails.address ?: ""
        binding.textViewPhone.text = poiDetails.phoneNumber ?: ""
        binding.textViewWebSite.text = poiDetails.website ?: ""
        binding.textViewRating.text = poiDetails.rating?.toString() ?: ""
        poiDetails.url?.let {
            binding.webView.loadUrl(it)
        }
    }

    companion object {

        private const val ARG_PLACE_ID = "placeID"

        fun newInstance(placeId: String) =
            DetailsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PLACE_ID, placeId)
                }
            }
    }
}