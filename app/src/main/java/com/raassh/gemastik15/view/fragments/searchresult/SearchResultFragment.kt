package com.raassh.gemastik15.view.fragments.searchresult

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions
import com.raassh.gemastik15.R
import com.raassh.gemastik15.adapter.PlaceAdapter
import com.raassh.gemastik15.api.response.PlacesItem
import com.raassh.gemastik15.databinding.FragmentSearchResultBinding
import com.raassh.gemastik15.utils.*
import com.raassh.gemastik15.view.activity.dashboard.DashboardViewModel
import dev.chrisbanes.insetter.applyInsetter
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchResultFragment : Fragment() {
    private val viewModel by viewModel<SearchResultViewModel>()
    private val sharedViewModel by sharedViewModel<DashboardViewModel>()
    private var binding: FragmentSearchResultBinding? = null
    private var map: GoogleMap? = null
    private var currentLocation: LatLng? = null

    private val callback = OnMapReadyCallback { googleMap ->
        map = googleMap
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchResultBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = binding?.fragmentMap?.getFragment<SupportMapFragment?>()
        mapFragment?.getMapAsync(callback)

        val query = SearchResultFragmentArgs.fromBundle(requireArguments()).query

        binding?.apply {
            root.applyInsetter { type(statusBars = true, navigationBars = true) { padding() } }

            etSearch.setText(query)

            btnBack.setOnClickListener {
                findNavController().navigateUp()
            }

            rvResult.apply {
                adapter = PlaceAdapter().apply {
                    onItemClickListener = { place ->
                        val action =
                            SearchResultFragmentDirections.actionSearchResultFragmentToPlaceDetailFragment(
                                place
                            )
                        findNavController().navigate(action)
                    }
                }

                addItemDecoration(LinearSpaceItemDecoration(16, RecyclerView.VERTICAL))
            }

            etSearch.on(EditorInfo.IME_ACTION_DONE) {
                if (etSearch.text.toString().isNotEmpty()) {
                    val action =
                        SearchResultFragmentDirections.actionSearchResultFragmentSelf()
                    action.query = etSearch.text.toString()

                    findNavController().navigate(action)
                } else {
                    root.showSnackbar(getString(R.string.search_must_not_empty))
                }
            }
        }

        viewModel.places.observe(viewLifecycleOwner) {
            if (it != null) {
                when (it) {
                    is Resource.Loading -> {
                        showLoading(true)
                    }
                    is Resource.Success -> {
                        showLoading(false)

                        showResult(it.data)
                    }
                    is Resource.Error -> {
                        showLoading(false, error = true)

                        binding?.root?.showSnackbar(
                            requireContext().translateErrorMessage(it.message)
                        )
                    }
                }
            }
        }

        sharedViewModel.location.observe(viewLifecycleOwner) {
            currentLocation = it
            viewModel.searchPlace(query, currentLocation?.latitude, currentLocation?.longitude)
        }
    }

    private fun showLoading(loading: Boolean, error: Boolean = false) {
        binding?.apply {
            if (loading) {
                pbLoading.visibility = View.VISIBLE
                rvResult.visibility = View.GONE
                tvNoResult.visibility = View.GONE
                fragmentMap.visibility = View.GONE
            } else {
                pbLoading.visibility = View.GONE

                if (error) {
                    tvNoResult.visibility = View.VISIBLE
                    rvResult.visibility = View.GONE
                    fragmentMap.visibility = View.GONE
                } else {
                    tvNoResult.visibility = View.GONE
                    rvResult.visibility = View.VISIBLE
                    fragmentMap.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun showResult(result: List<PlacesItem>?) {
        binding?.apply {
            if (result.isNullOrEmpty()) {
                tvNoResult.visibility = View.VISIBLE
                rvResult.visibility = View.GONE
                fragmentMap.visibility = View.GONE
                return
            }

            (rvResult.adapter as PlaceAdapter).submitList(result.map {
                placeItemToEntity(it)
            })

            val latLngBounds = LatLngBounds.Builder()
            map?.clear()

            result.forEach {
                val latLng = LatLng(it.latitude, it.longitude)
                map?.addMarker(MarkerOptions().position(latLng).title(it.name))
                latLngBounds.include(latLng)
            }
            map?.setContentDescription(null)

            map?.setOnMapLoadedCallback {
                map?.moveCamera(CameraUpdateFactory.newLatLngBounds(latLngBounds.build(), 100))
            }
            map?.setOnMarkerClickListener {
                true
            }

            fragmentMap.apply {
                for (i in 0 until childCount) {
                    val child = getChildAt(i)
                    child.apply {
                        importantForAccessibility = View.IMPORTANT_FOR_ACCESSIBILITY_NO
                        isFocusable = false
                        contentDescription = null
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    override fun onDestroy() {
        super.onDestroy()
        map = null
    }
}