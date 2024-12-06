package com.dicoding.soulsync.ui.profile

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.dicoding.soulsync.api.ApiClient
import com.dicoding.soulsync.databinding.FragmentProfileBinding
import com.dicoding.soulsync.model.ProfileResponse
import com.dicoding.soulsync.ui.login.LoginActivity
import com.dicoding.soulsync.utils.UserPreference
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private lateinit var userPreference: UserPreference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        userPreference = UserPreference(requireContext())

        // Fetch Profile Data
        fetchProfile()

        // Button Update Profile
        binding.updateProfileButton.setOnClickListener {
            startActivity(Intent(requireContext(), EditProfileActivity::class.java))
        }

        // Button Logout
        binding.logoutButton.setOnClickListener {
            performLogout()
        }
    }

    private fun fetchProfile() {
        lifecycleScope.launch {
            try {
                // Tampilkan ProgressBar
                binding.progressBar.visibility = View.VISIBLE

                val token = userPreference.token.firstOrNull()
                val apiClient = ApiClient(token).createService()
                val response: ProfileResponse = apiClient.getProfile()

                if (response.status == "success") {
                    val user = response.payload.user
                    binding.nameTextView.text = "Name: ${user.name}"
                    binding.dateTextView.text = "Date of Birth: ${user.date_of_birth}"
                    binding.genderTextView.text = "Gender: ${user.gender}"
                } else {
                    Toast.makeText(requireContext(), response.message, Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Log.e("ProfileFragment", "Error fetching profile: ${e.message}")
                Toast.makeText(requireContext(), "Failed to fetch profile", Toast.LENGTH_SHORT).show()
            } finally {
                // Sembunyikan ProgressBar
                binding.progressBar.visibility = View.GONE
            }
        }
    }


    private fun performLogout() {
        lifecycleScope.launch {
            try {
                // Tampilkan ProgressBar
                binding.progressBar.visibility = View.VISIBLE

                userPreference.clearToken()
                Toast.makeText(requireContext(), "Logged out successfully", Toast.LENGTH_SHORT).show()

                val intent = Intent(requireContext(), LoginActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
            } catch (e: Exception) {
                Log.e("ProfileFragment", "Error logging out: ${e.message}")
                Toast.makeText(requireContext(), "Failed to log out", Toast.LENGTH_SHORT).show()
            } finally {
                // Sembunyikan ProgressBar
                binding.progressBar.visibility = View.GONE
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
