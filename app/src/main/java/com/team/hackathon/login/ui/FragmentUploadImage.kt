package com.team.hackathon.login.ui

import android.app.DownloadManager
import android.content.Context
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.getSystemService
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask
import com.team.hackathon.databinding.FragmentUploadImageBinding
import com.team.hackathon.login.util.LoginViewModel
import java.io.File


class FragmentUploadImage : Fragment() {
    private val binding by lazy { FragmentUploadImageBinding.inflate(layoutInflater) }
    private val viewModel: LoginViewModel by activityViewModels()
    private val fireBaseStorage = FirebaseStorage.getInstance()
    var storageRef = fireBaseStorage.reference
    private val imageRef = fireBaseStorage.reference.child("certificate.pdf")

    companion object {
        fun getInstance() = FragmentUploadImage()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.download.setOnClickListener {

                downloadFiles()
                    Toast.makeText(context, "download!", Toast.LENGTH_LONG).show()


        }
    }

    private fun getUrl() {
        storageRef = fireBaseStorage.getReference()
        storageRef.child("certificate.pdf")
        storageRef.downloadUrl
            .addOnSuccessListener {
                Log.d("TAG", "uploadUserProfileToFireStore: ")
                downloadFiles()
            }

            .addOnFailureListener {
                Log.d("TAG", "uploadUserProfileToFireStore: ")
            }
    }


    private fun downloadFiles() {
        val url = "https://simg.nicepng.com/png/small/224-2248218_drawing-toon-link-download-drawing.png"
        // create a download request

        val request = DownloadManager.Request(Uri.parse(url)) // request a download url
            .setTitle("file")
            .setDescription("Downloading")
            .setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI or DownloadManager.Request.NETWORK_MOBILE)
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            .setAllowedOverMetered(true)
            .setAllowedOverRoaming(false)
            .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS,url)
        val downloadManager= context?.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        downloadManager.enqueue(request)
    }
}