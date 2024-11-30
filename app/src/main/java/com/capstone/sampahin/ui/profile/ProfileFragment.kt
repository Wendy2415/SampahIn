package com.capstone.sampahin.ui.profile

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.capstone.sampahin.R
import com.capstone.sampahin.data.UidPreferences
import com.capstone.sampahin.databinding.FragmentProfileBinding
import com.capstone.sampahin.ui.login.LoginActivity
import com.capstone.sampahin.ui.login.LoginViewModelFactory
import kotlinx.coroutines.launch

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private lateinit var prefUid: UidPreferences
    private val viewModel: ProfileViewModel by viewModels {
        LoginViewModelFactory.getInstance(requireContext())
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        prefUid = UidPreferences(requireContext())

        binding.signOutButton.setOnClickListener {
            logout()
        }

        lifecycleScope.launch {
            val uid = prefUid.getUid()
            if (uid != null) {
                val user = viewModel.getUser(uid)
                binding.tvUsername.text = user.name
                binding.tvEmail.text = user.email
            } else {
                Toast.makeText(requireActivity(), "UID not found", Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun logout() {
        prefUid.clearUid()
        Toast.makeText(requireActivity(), getString(R.string.logout_success), Toast.LENGTH_SHORT)
            .show()
        val intent = Intent(activity, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }

}
