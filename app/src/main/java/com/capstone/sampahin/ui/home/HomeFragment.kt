package com.capstone.sampahin.ui.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstone.sampahin.R
import com.capstone.sampahin.data.Category
import com.capstone.sampahin.databinding.FragmentHomeBinding
import com.capstone.sampahin.ui.maps.MapsFragment

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var categoryAdapter: CategoryAdapter
    private val list = ArrayList<Category>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val homeViewModel =
            ViewModelProvider(this)[HomeViewModel::class.java]


        categoryAdapter = CategoryAdapter(list)
        binding.rvCategoryHome.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = categoryAdapter
        }

        binding.mapLayout.setOnClickListener {
            navigateToMapFragment()
        }

        homeViewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }

        list.addAll(getListCategory())
        categoryAdapter.notifyDataSetChanged()


    }

    private fun getListCategory(): ArrayList<Category> {
        val dataName = resources.getStringArray(R.array.data_name)
        val dataPhoto = resources.getStringArray(R.array.data_photo)
        val listCategory = ArrayList<Category>()
        for (i in dataName.indices) {
            val category = Category(dataName[i], dataPhoto[i])
            listCategory.add(category)
        }
        return listCategory
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    private fun navigateToMapFragment() {
        val transaction = parentFragmentManager.beginTransaction()
        val mapFragment = MapsFragment()
        transaction.replace(R.id.action_navigation_home_to_navigation_maps, mapFragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}