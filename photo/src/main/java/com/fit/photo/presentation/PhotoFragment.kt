package com.fit.photo.presentation

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.fit.photo.databinding.FragmentPhotoBinding
import com.fit.photo.utils.showPermissionDeniedDialog
import dagger.hilt.android.AndroidEntryPoint
import java.io.ByteArrayOutputStream
import com.bumptech.glide.Glide
import java.io.InputStream

@AndroidEntryPoint
class PhotoFragment : Fragment() {

    private val viewModel: PhotoViewModel by viewModels()

    private lateinit var binding: FragmentPhotoBinding
    private var imageBitmap: Bitmap? = null
    private var listUri: List<Uri> = listOf()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPhotoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.buttonTakePicture.setOnClickListener {
            if (checkPermissions()) {
                takePicture()
            } else {
                requestPermissions()
            }
        }
        binding.buttonSavePhoto.setOnClickListener {
            binding.imageView.setImageBitmap(null)
            val baos = ByteArrayOutputStream()
            imageBitmap?.compress(Bitmap.CompressFormat.JPEG, 80, baos)
            val data = baos.toByteArray()
            viewModel.sendPhotoToFirestorage(data)
        }

        binding.buttonSavePhotos.setOnClickListener {
            syncImageList()
        }
        binding.buttonSelectFromGallery.setOnClickListener {
            openGalleryForImages()
        }
        observerViewModel()
    }

    private fun syncImageList() {
        listUri.forEach { uri ->
            val byteArray = uriToByteArray(uri)
            if (byteArray != null) {
                viewModel.sendPhotoToFirestorage(byteArray)
            }
        }
    }

    private fun observerViewModel() {
        viewModel.uiModel.observe(this.requireActivity(), Observer {
            when (it) {
                PhotoViewModel.UIModel.Loading -> {

                }

                PhotoViewModel.UIModel.NoConnection -> {

                }

                is PhotoViewModel.UIModel.ShowView -> {
                    Glide.with(this)
                        .load(it.photoUrl)
                        .into(binding.imageView)
                }

                is PhotoViewModel.UIModel.ShowCountImage -> {
                    listUri = it.listUri
                    binding.txtCountImages.text = it.listUri.size.toString()
                }
            }
        })
    }

    private fun checkPermissions(): Boolean {
        val cameraPermission =
            ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA)
        val storagePermission = ContextCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
        return cameraPermission == PackageManager.PERMISSION_GRANTED && storagePermission == PackageManager.PERMISSION_GRANTED
    }

    private fun requestPermissions() {
        requestPermissions(
            arrayOf(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE),
            PERMISSION_REQUEST_CODE
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<out String>, grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                takePicture()
            } else {
                showPermissionDeniedDialog(requireActivity())
            }
        }
    }

    private fun takePicture() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        try {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
        } catch (e: Exception) {
            Toast.makeText(requireContext(), "Error al abrir la cámara", Toast.LENGTH_SHORT).show()
        }
    }

    private fun openGalleryForImages() {
        val intent = Intent(Intent.ACTION_GET_CONTENT).apply {
            type = "image/*"
            putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
        }
        startActivityForResult(Intent.createChooser(intent, "Select Pictures"), PICK_IMAGES_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
            imageBitmap = data?.extras?.get("data") as Bitmap
            binding.imageView.setImageBitmap(imageBitmap)
        }
        if (requestCode == PICK_IMAGES_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            if (data?.clipData != null) {

                val count = data.clipData!!.itemCount
                val imageUris = mutableListOf<Uri>()
                for (i in 0 until count) {
                    val imageUri = data.clipData!!.getItemAt(i).uri
                    imageUris.add(imageUri)
                }
                handleSelectedImages(imageUris)
            } else if (data?.data != null) {
                val imageUri = data.data!!
                handleSelectedImages(listOf(imageUri))
            }
        }
    }

    private fun handleSelectedImages(imageUris: List<Uri>) {
        viewModel.updateImageUris(imageUris)
    }

    private fun uriToByteArray(uri: Uri): ByteArray? {
        return try {
            val inputStream: InputStream? = this.requireActivity().contentResolver.openInputStream(uri)
            val byteArrayOutputStream = ByteArrayOutputStream()
            val buffer = ByteArray(1024)
            var length: Int
            while (inputStream?.read(buffer).also { length = it ?: -1 } != -1) {
                byteArrayOutputStream.write(buffer, 0, length)
            }
            byteArrayOutputStream.toByteArray()
        } catch (e: Exception) {
            Log.e("MainActivity", "Error al convertir URI a ByteArray: ${e.message}")
            null
        }
    }

    companion object {
        private const val PERMISSION_REQUEST_CODE = 1003
        private const val REQUEST_IMAGE_CAPTURE = 1004
        private const val PICK_IMAGES_REQUEST_CODE = 1005
    }
}