package com.dicoding.soulsync.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.dicoding.soulsync.R

class ProfileFragment : Fragment() {

    private val viewModel: ProfileViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_profile, container, false)

        val progressBar = rootView.findViewById<ProgressBar>(R.id.progressBar)
        val imgProfile = rootView.findViewById<ImageView>(R.id.imgProfile)
        val etName = rootView.findViewById<EditText>(R.id.etName)
        val etDateOfBirth = rootView.findViewById<EditText>(R.id.etDateOfBirth)
        val rbMale = rootView.findViewById<RadioButton>(R.id.rbMale)
        val rbFemale = rootView.findViewById<RadioButton>(R.id.rbFemale)
        val etEmail = rootView.findViewById<EditText>(R.id.etEmail)
        val btnSave = rootView.findViewById<Button>(R.id.btnSave)
        val logoutButton = rootView.findViewById<Button>(R.id.logoutButton)

        // Observe loading state
        viewModel.loading.observe(viewLifecycleOwner) { isLoading ->
            progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }

        // Observe and bind profile data
        viewModel.name.observe(viewLifecycleOwner) { name ->
            etName.setText(name)
        }

        viewModel.dateOfBirth.observe(viewLifecycleOwner) { dateOfBirth ->
            etDateOfBirth.setText(dateOfBirth)
        }

        viewModel.gender.observe(viewLifecycleOwner) { gender ->
            rbMale.isChecked = gender == getString(R.string.male)
            rbFemale.isChecked = gender == getString(R.string.female)
        }

        viewModel.email.observe(viewLifecycleOwner) { email ->
            etEmail.setText(email)
        }

        // Load profile image
        Glide.with(this)
            .load("https://example.com/profile.jpg") // Ganti URL dengan sumber gambar sebenarnya
            .placeholder(R.drawable.profile)
            .into(imgProfile)

        // Save profile on button click
        btnSave.setOnClickListener {
            val name = etName.text.toString()
            val dateOfBirth = etDateOfBirth.text.toString()
            val gender = if (rbMale.isChecked) getString(R.string.male) else getString(R.string.female)
            val email = etEmail.text.toString()

            viewModel.saveProfile(name, dateOfBirth, gender, email)
        }

        btnSave.setOnClickListener {
            val name = etName.text.toString()
            val dateOfBirth = etDateOfBirth.text.toString()
            val gender = if (rbMale.isChecked) getString(R.string.male) else getString(R.string.female)
            val email = etEmail.text.toString()

            // Panggil setProfile untuk memperbarui data di ViewModel
            viewModel.setProfile(name, dateOfBirth, gender, email)
        }


        // Logout button functionality
        logoutButton.setOnClickListener {
            viewModel.logout()
            Toast.makeText(context, "Logged out", Toast.LENGTH_SHORT).show()
        }

        return rootView
    }
}
