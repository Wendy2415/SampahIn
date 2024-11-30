package com.capstone.sampahin.ui.chat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstone.sampahin.R
import com.capstone.sampahin.databinding.FragmentTopicsBinding
import com.capstone.sampahin.ui.chat.adapter.TopicsAdapter

class TopicsFragment : Fragment() {
    private lateinit var binding: FragmentTopicsBinding

    private var topicsAdapter: TopicsAdapter? = null

    private var topicsTitle = emptyList<String>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentTopicsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val dataSetClient = DatasetClient(requireActivity())
        dataSetClient.loadJsonData()?.let {
            topicsTitle = it.getTitles()
        }

        topicsAdapter = TopicsAdapter(topicsTitle, object : TopicsAdapter.OnItemSelected {
            override fun onItemClicked(itemID: Int, itemTitle: String) {
                startChatScreen(itemID, itemTitle)
            }
        })

        val linearLayoutManager = LinearLayoutManager(requireContext())
        val decoration = DividerItemDecoration(
            binding.rvTopics.context,
            linearLayoutManager.orientation
        )

        with(binding.rvTopics) {
            layoutManager = linearLayoutManager
            adapter = topicsAdapter
            addItemDecoration(decoration)
        }

    }

    private fun startChatScreen(itemID: Int, itemTitle: String) {
        val action = TopicsFragmentDirections.actionTopicsFragmentToChatFragment(
            itemID,
            itemTitle
        )
        Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main)
            .navigate(action)
    }

}