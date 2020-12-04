package com.tikhonov.shopsfinder.ui.infoWindow

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.tikhonov.shopsfinder.data.model.Poi
import com.tikhonov.shopsfinder.databinding.InfoWindowMapBinding
import com.tikhonov.shopsfinder.ui.details.DetailsFragment
import com.tikhonov.shopsfinder.util.navigateTo


class InfoWindowFragment : Fragment() {

    private lateinit var poi: Poi
    // viewBinding
    private var _binding: InfoWindowMapBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        poi = arguments?.getSerializable(POI_ARGUMENT) as Poi
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = InfoWindowMapBinding.inflate(inflater, container, false)
        return  binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.button.setOnClickListener {
            navigateTo(fragment = DetailsFragment.newInstance(poi.placeId), addToBackStack = true)
        }
        updateUi(poi)

    }

    private fun updateUi(poi: Poi) {
        binding.textView.text = poi.name
        binding.ratingBar.rating = poi.rating.toFloat()
        binding.textViewRating.text = poi.rating.toString()
        binding.textViewNumberOfRates.text = "(${poi.numberOfRates})"
    }

    companion object {

        const val POI_ARGUMENT = "poi"

        fun newInstance(poi: Poi): InfoWindowFragment {
            val newFragment = InfoWindowFragment()
            val bundle = Bundle()
                .apply {
                    putSerializable(POI_ARGUMENT, poi)
                }
            newFragment.arguments = bundle
            return newFragment
        }

    }

}