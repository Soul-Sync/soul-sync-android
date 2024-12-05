import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.dicoding.soulsync.R
import com.dicoding.soulsync.databinding.FragmentProfileBinding

class ProfileFragment : Fragment(R.layout.fragment_profile) {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private val profileViewModel: ProfileViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentProfileBinding.bind(view)

        // Observe LiveData
        profileViewModel.name.observe(viewLifecycleOwner, Observer { name ->
            binding.etName.setText(name)
        })

        profileViewModel.dateOfBirth.observe(viewLifecycleOwner, Observer { dateOfBirth ->
            binding.etDateOfBirth.setText(dateOfBirth)
        })

        profileViewModel.gender.observe(viewLifecycleOwner, Observer { gender ->
            when (gender) {
                getString(R.string.male) -> binding.rbMale.isChecked = true
                getString(R.string.female) -> binding.rbFemale.isChecked = true
            }
        })

        profileViewModel.email.observe(viewLifecycleOwner, Observer { email ->
            binding.etEmail.setText(email)
        })

        // Set up listeners for Save button
        binding.btnSave.setOnClickListener {
            val name = binding.etName.text.toString()
            val dateOfBirth = binding.etDateOfBirth.text.toString()
            val gender = if (binding.rbMale.isChecked) getString(R.string.male) else getString(R.string.female)
            val email = binding.etEmail.text.toString()

            profileViewModel.updateName(name)
            profileViewModel.updateDateOfBirth(dateOfBirth)
            profileViewModel.updateGender(gender)
            profileViewModel.updateEmail(email)

            profileViewModel.saveProfile()
            Toast.makeText(requireContext(), getString(R.string.profile_saved), Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}