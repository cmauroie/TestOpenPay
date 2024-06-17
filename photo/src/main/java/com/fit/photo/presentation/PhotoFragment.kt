package com.fit.photo.presentation

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
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

@AndroidEntryPoint
class PhotoFragment : Fragment() {

    private val viewModel: PhotoViewModel by viewModels()

    private lateinit var binding: FragmentPhotoBinding
    private var imageBitmap: Bitmap? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO: Use the ViewModel
    }

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
        observerViewModel()
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
                    /*it.locationModel.apply {
                        showMarker(latitude, longitude, date)
                    }*/
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
                //Toast.makeText(requireContext(), "Permisos requeridos para tomar fotos", Toast.LENGTH_SHORT).show()
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
            imageBitmap = data?.extras?.get("data") as Bitmap
            binding.imageView.setImageBitmap(imageBitmap)
        }
    }

    companion object {
        private const val PERMISSION_REQUEST_CODE = 1003
        private const val REQUEST_IMAGE_CAPTURE = 1004
    }
}