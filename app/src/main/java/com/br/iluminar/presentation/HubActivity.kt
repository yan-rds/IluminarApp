package com.br.iluminar.presentation

import android.app.Activity
import android.content.Intent
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.br.iluminar.domain.utils.Resource
import com.br.iluminar.databinding.ActivityHubBinding
import com.br.iluminar.presentation.viewmodels.HubActivityViewModel
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class HubActivity : AppCompatActivity() {

    private val viewModel: HubActivityViewModel by viewModels()
    private val binding by lazy { ActivityHubBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(binding.root)
        setupListener()
    }

    override fun onStart() {
        super.onStart()
        getDataBaseData()

    }

    private fun getDataBaseData() {
        viewModel.getUserData()

        viewModel.dataFlow.observe(this) {
            when (it) {
                is Resource.Loading -> {}
                is Resource.Failure -> {}
                is Resource.Success -> {
                    Log.e("Result result", "${it.result}")
                    binding.studentNameTv.text = it.result.name
                    binding.periodTv.text = it.result.period
                    binding.yearTv.text = it.result.schoolYear.toString()
                    if (!it.result.profileUri.isNullOrEmpty()){
                        updateProfilePicture(it.result.profileUri!!)
                    }
                }
                else -> {}
            }
        }
    }

    private fun setupListener() {
        binding.logoutBt.setOnClickListener {
            viewModel.signOut()
            goToLoginActivity()
        }

        binding.profileImage.setOnClickListener {
            pickImage()
        }

        binding.dailyActivities.setOnClickListener {
            val intent = Intent(this, DailyActivitiesScreen::class.java)
            startActivity(intent)
        }

        binding.calendar.setOnClickListener {
            
        }

        binding.messages.setOnClickListener {
            val intent = Intent(this, MessagesScreen::class.java)
            startActivity(intent)
        }
    }

    private fun pickImage() {
        ImagePicker.Companion.with(this)
            .galleryMimeTypes(arrayOf("image/*"))
            .crop(
                1f,
                1f
            )                    //Crop image(Optional), Check Customization for more option
            .compress(512)            //Final image size will be less than 1 MB(Optional)
            .maxResultSize(
                1080,
                1080
            )    //Final image resolution will be less than 1080 x 1080(Optional)
            .galleryOnly()
            .start()
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (resultCode) {
            Activity.RESULT_OK -> {
                val uri: Uri = data?.data!!
                if (Build.VERSION.SDK_INT < 28) {
                    val bitmap = MediaStore.Images.Media.getBitmap(
                        this.contentResolver,
                        uri
                    )
                    binding.profileImage.setImageBitmap(bitmap)
                } else {
                    val source = ImageDecoder.createSource(this.contentResolver, uri)
                    val bitmap = ImageDecoder.decodeBitmap(source)
                    binding.profileImage.setImageBitmap(bitmap)
                }
                saveUserImageInFirebase(uri)
            }
            ImagePicker.RESULT_ERROR -> {
                Toast.makeText(this, ImagePicker.getError(data), Toast.LENGTH_SHORT).show()
            }
            else -> {
                Toast.makeText(this, "Ação cancelada", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun saveUserImageInFirebase(uri: Uri) {
        val currentUser = FirebaseAuth.getInstance().currentUser?.uid
        val fileName: String = UUID.randomUUID().toString()
        val storage = Firebase.storage.getReference("/images/$fileName")
        storage.putFile(uri).addOnSuccessListener {
            storage.downloadUrl.addOnCompleteListener() { task ->
                if (task.isSuccessful) {
                    if (currentUser != null) {
                        FirebaseFirestore.getInstance().collection("Usuarios")
                            .document(currentUser).update("profileUri", task.result)
                    }
                }
            }
        }
    }

    private fun updateProfilePicture(uri: String) {
        Picasso.get().load(uri).into(binding.profileImage)
    }

    private fun goToLoginActivity() {
        val intent = Intent(this, LoginActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
        finish()
        startActivity(intent)
    }

}