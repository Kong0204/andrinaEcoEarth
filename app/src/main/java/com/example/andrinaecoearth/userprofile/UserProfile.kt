package com.example.andrinaecoearth.userprofile

import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.icu.util.Calendar
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.*
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.example.andrinaecoearth.R
import java.util.prefs.Preferences


abstract class UserProfile : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    //or use
    // lateinit var viewModel: UserProfileViewModel
    private val viewModel: UserProfileViewModel by viewModels()
    private lateinit var binding: UserProfileBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var binding: UserProfileBinding = DataBindingUtil.inflate(layoutInflater)

        //setContentView(R.layout.layout_user_profile)
        setContentView(binding.root)

        //get the viewmodel
//        viewModel ViewModelProvider (this).get(UserProfileViewModel::class.java)


        //TODO: binding just doesn't work for some reason??


        //binding
        binding.userProfile = User


//        binding = DataBindingUtil.inflate(
//            inflater,
//            R.layout.layout_user_profile,
//            container,
//            false
//        )


        genderSpinner()
        setBirthdayDate()

        val profileEditImageButton = findViewById<Button>(R.id.profile_edit_image_button)
        profileEditImageButton.setOnClickListener {
            setProfilePic()
        }


        val profileBioText = findViewById<EditText>(R.id.profile_bio_text)
        val stringProfileBioText = profileBioText.text.toString()
        //button to save user profile bio text description
        val profileBioSaveButton = findViewById<Button>(R.id.profile_bio_save_button)
        profileBioSaveButton.setOnClickListener {
            saveBioText()

            viewModel.profileBioText.value = stringProfileBioText

        }


    }

    //change user profile picture
    private fun setProfilePic() {
        val profileImage = findViewById<ImageView>(R.id.profile_image)
        val profileEditImageButton = findViewById<Button>(R.id.profile_edit_image_button)

        profileEditImageButton.setOnClickListener {


            // Registers a photo picker activity launcher in single-select mode.
            val pickMedia =
                registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
                    // Callback is invoked after the user selects a media item or closes the
                    // photo picker.
                    if (uri != null) {
                        Log.d("PhotoPicker", "Selected URI: $uri")
                    } else {
                        Log.d("PhotoPicker", "No media selected")
                    }
                }
            // Launch the photo picker and let the user choose only images.
            pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))


        }


        // Function to check and request permission to access pictures/media
        fun checkPermission(permission: String, requestCode: Int) {
            if (ContextCompat.checkSelfPermission(
                    this@UserProfile,
                    permission
                ) == PackageManager.PERMISSION_DENIED
            ) {
                // Requesting the permission
                ActivityCompat.requestPermissions(
                    this@UserProfile,
                    arrayOf(permission),
                    requestCode
                )
            } else {
                Toast.makeText(this@UserProfile, "Permission already granted", Toast.LENGTH_SHORT)
                    .show()
            }
        }


        //click button, choose profile pic
//        profileEditImageButton.setOnClickListener {
//            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
//            intent.data = Uri.parse(donationLink1)
//            startActivity(intent)


    }

}


//spinner for gender option
private fun genderSpinner() {
    //1. create object to hold spinner
    val spinnerGenderSelection: Spinner = findViewById(R.id.profile_gender_spinner)
    spinnerGenderSelection.onItemSelectedListener = this

    //2. create an ArrayAdapter using the string array and a default spinner layout
    ArrayAdapter.createFromResource(
        this,
        R.array.profile_gender_selection,
        android.R.layout.simple_spinner_item
    ).also { adapter ->
        //specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        //apply the adapter to the spinner
        spinnerGenderSelection.adapter = adapter


    }

    fun onItemSelected(parent: AdapterView<*>, view: View?, pos: Int, id: Long) {
        //an item was selected. you can retrieve the selected item using parent.getItemAtPosition(pos)
        val selectionTv: TextView = findViewById(R.id.selectedGenderTextView)
        selectionTv.text = parent.getItemAtPosition(pos).toString()

        //do i need this 'return' or not?
        return
    }


}


private fun setBirthdayDate() {
    //getting reference to the Birthday EditText
    val setBirthday = findViewById<EditText>(R.id.profile_birthday_input)

    //set OnClickListener on the Birthday EditText to show DatePickerDialog
    setBirthday.setOnClickListener {
        //get current date from Calendar instance
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)

        //create and show DatePickerDialog
        val datePickerDialog = DatePickerDialog(
            this,
            { _, selectedYear, selectedMonth, selectedDayOfMonth ->
                //set selected date on setBirthday EditText
                setBirthday.setText("$selectedYear/${selectedMonth + 1}/$selectedDayOfMonth")
            },
            year,
            month,
            dayOfMonth
        )
        datePickerDialog.show()

    }


}


//save user profile description
private fun saveBioText() {

    val profileBioText = findViewById<EditText>(R.id.profile_bio_text)


    var userText = editText.text.toString()
    profileBioText.setText(userText)
    //do something with text

}