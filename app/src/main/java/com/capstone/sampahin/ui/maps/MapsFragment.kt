package com.capstone.sampahin.ui.maps

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.LatLngBounds
import com.capstone.sampahin.R
import com.capstone.sampahin.databinding.FragmentMapsBinding
import com.capstone.sampahin.data.Result
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory

class MapsFragment : Fragment(R.layout.fragment_maps), OnMapReadyCallback {

    private lateinit var map: GoogleMap
    private val boundsBuilder = LatLngBounds.Builder()
    private lateinit var binding: FragmentMapsBinding
    private val mapsViewModel: MapsViewModel by viewModels {
        MapsViewModelFactory.getInstance()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentMapsBinding.bind(view)

        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        binding.addAddressButton.setOnClickListener {
            val address = binding.etAddress.text.toString()
            val radius = binding.etRadius.text.toString().toIntOrNull()

            if (address.isNotEmpty() && radius != null) {
                hideKeyboard()
                mapsViewModel.fetchMapsData(address, radius)
            } else {
                Toast.makeText(
                    requireContext(),
                    "Please enter valid address and radius",
                    Toast.LENGTH_SHORT
                ).show()
            }

        }

        mapsViewModel.mapsResult.observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Loading -> {
                    showLoading(true)
                }

                is Result.Success -> {
                    showLoading(false)
                    val places = result.data.places
                    places?.forEach { place ->
                        place?.location?.let {
                            val latLng = LatLng(it.lat as Double, it.lng as Double)
                            map.addMarker(
                                MarkerOptions().position(latLng).title(place.name)
                                    .snippet(place.address)
                                    .icon(vectorToBitmap(R.drawable.icon_map_recycle, Color.parseColor("#006400")))
                            )
                            boundsBuilder.include(latLng)
                        }
                        binding.placeholderLayout.visibility = View.GONE
                    }

                    val bounds = boundsBuilder.build()
                    map.animateCamera(
                        CameraUpdateFactory.newLatLngBounds(
                            bounds,
                            resources.displayMetrics.widthPixels,
                            resources.displayMetrics.heightPixels,
                            300
                        )
                    )
                }

                is Result.Error -> {
                    showLoading(true)
                    Toast.makeText(requireContext(), "Error:", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap

        map.uiSettings.isZoomControlsEnabled = true
        map.uiSettings.isIndoorLevelPickerEnabled = true
        map.uiSettings.isCompassEnabled = true
        map.uiSettings.isMapToolbarEnabled = true

        val initialLocation = LatLng(-6.200000, 106.816666)
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(initialLocation, 15f))
    }

    private fun vectorToBitmap(@DrawableRes id: Int, @ColorInt color: Int): BitmapDescriptor {
        val vectorDrawable = ResourcesCompat.getDrawable(resources, id, null)
        if (vectorDrawable == null) {
            Log.e("BitmapHelper", "Resource not found")
            return BitmapDescriptorFactory.defaultMarker()
        }
        val bitmap = Bitmap.createBitmap(
            vectorDrawable.intrinsicWidth,
            vectorDrawable.intrinsicHeight,
            Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(bitmap)
        vectorDrawable.setBounds(0, 0, canvas.width, canvas.height)
        DrawableCompat.setTint(vectorDrawable, color)
        vectorDrawable.draw(canvas)
        return BitmapDescriptorFactory.fromBitmap(bitmap)
    }

    private fun hideKeyboard() {
        val imm = requireContext().getSystemService(android.content.Context.INPUT_METHOD_SERVICE) as android.view.inputmethod.InputMethodManager
        val view = requireActivity().currentFocus
        imm.hideSoftInputFromWindow(view?.windowToken, 0)
    }


}
