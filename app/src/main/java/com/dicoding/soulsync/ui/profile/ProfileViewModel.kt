import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ProfileViewModel : ViewModel() {

    // LiveData untuk nama pengguna
    private val _name = MutableLiveData<String>()
    val name: LiveData<String> = _name

    // LiveData untuk tanggal lahir
    private val _dateOfBirth = MutableLiveData<String>()
    val dateOfBirth: LiveData<String> = _dateOfBirth

    // LiveData untuk jenis kelamin
    private val _gender = MutableLiveData<String>()
    val gender: LiveData<String> = _gender

    // LiveData untuk email
    private val _email = MutableLiveData<String>()
    val email: LiveData<String> = _email

    // Fungsi untuk memperbarui nama
    fun updateName(newName: String) {
        _name.value = newName
    }

    // Fungsi untuk memperbarui tanggal lahir
    fun updateDateOfBirth(newDate: String) {
        _dateOfBirth.value = newDate
    }

    // Fungsi untuk memperbarui jenis kelamin
    fun updateGender(newGender: String) {
        _gender.value = newGender
    }

    // Fungsi untuk memperbarui email
    fun updateEmail(newEmail: String) {
        _email.value = newEmail
    }

    // Fungsi untuk menyimpan data profil
    fun saveProfile() {
        // Di sini, Anda bisa menyimpan data ke database atau API
        // Implementasi logika penyimpanan sesuai kebutuhan Anda
    }
}