package com.dicoding.soulsync.ui.profile

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.dicoding.soulsync.api.ApiClient
import com.dicoding.soulsync.databinding.FragmentProfileBinding
import com.dicoding.soulsync.model.ProfileResponse
import com.dicoding.soulsync.model.User
import com.dicoding.soulsync.ui.login.LoginActivity
import com.dicoding.soulsync.utils.UserPreference
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private lateinit var userPreference: UserPreference
    private lateinit var editProfileLauncher: ActivityResultLauncher<Intent>

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

        // Setup ActivityResultLauncher untuk EditProfileActivity
        editProfileLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                // Refresh profil setelah kembali dari EditProfileActivity
                fetchProfile()
            }
        }

        // Ambil data profil
        fetchProfile()

        // Tombol untuk membuka EditProfileActivity
        binding.updateProfileButton.setOnClickListener {
            val intent = Intent(requireContext(), EditProfileActivity::class.java)
            editProfileLauncher.launch(intent)
        }

        // Tombol Logout
        binding.btnLogout.setOnClickListener {
            performLogout()
        }
    }

    private fun fetchProfile() {
        lifecycleScope.launch {
            try {
                val localToken = userPreference.token.firstOrNull()
                if (localToken == null) {
                    Toast.makeText(requireContext(), "Token not found, please login again.", Toast.LENGTH_SHORT).show()
                    navigateToLogin()
                    return@launch
                }

                // Tampilkan ProgressBar jika binding tidak null
                _binding?.progressBar?.visibility = View.VISIBLE

                val apiClient = ApiClient(localToken).createService()
                val response: ProfileResponse = apiClient.getProfile()

                if (!isAdded || _binding == null) return@launch // Pastikan fragment masih aktif

                if (response.status == "success") {
                    val user = response.payload.user
                    displayProfile(user)
                } else {
                    Toast.makeText(requireContext(), response.message, Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Log.e("ProfileFragment", "Error fetching profile: ${e.message}")
                Toast.makeText(requireContext(), "Failed to fetch profile", Toast.LENGTH_SHORT).show()
            } finally {
                // Sembunyikan ProgressBar jika binding tidak null
                _binding?.progressBar?.visibility = View.GONE
            }
        }
    }

    private fun displayProfile(user: User) {
        _binding?.let { binding ->
            binding.nameTextView.text = user.name
            binding.dateTextView.text = user.date_of_birth ?: "-"
            binding.genderTextView.text = user.gender ?: "-"
        }
        lifecycleScope.launch {
            userPreference.saveName(user.name)
        }
    }

    private fun performLogout() {
        lifecycleScope.launch {
            try {
                userPreference.clearToken()
                Toast.makeText(requireContext(), "Logged out successfully", Toast.LENGTH_SHORT).show()
                navigateToLogin()
            } catch (e: Exception) {
                Log.e("ProfileFragment", "Error logging out: ${e.message}")
                Toast.makeText(requireContext(), "Failed to log out", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun navigateToLogin() {
        val intent = Intent(requireContext(), LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d("ProfileFragment", "onDestroyView called, clearing binding")
        _binding = null
    }
}
