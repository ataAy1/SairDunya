package com.sairdunyasi.sairlerindunyasi.data.utils

import android.net.Uri
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import coil.compose.rememberImagePainter
import coil.transform.CircleCropTransformation
import com.sairdunyasi.sairlerindunyasi.R
import androidx.activity.compose.rememberLauncherForActivityResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await

object ImageUtils {
    @Composable
    fun rememberImagePickerLauncher(
        onImagePicked: (Uri?) -> Unit
    ): ActivityResultLauncher<String> {
        val launcher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.GetContent(),
            onResult = { uri -> onImagePicked(uri) }
        )
        return launcher
    }

    @Composable
    fun loadImage(uri: String?) = rememberImagePainter(
        data = uri,
        builder = {
            transformations(CircleCropTransformation())
            placeholder(R.drawable.ic_profile_photo)
        }
    )

    suspend fun uploadImageToStorage(uri: Uri): String {
        val storageReference = FirebaseStorage.getInstance().reference
        val userId = FirebaseAuth.getInstance().currentUser?.uid ?: throw Exception("User not authenticated")
        val imageRef = storageReference.child("profile_images/$userId/${System.currentTimeMillis()}.jpg")

        imageRef.putFile(uri).await()

        return imageRef.downloadUrl.await().toString()
    }


}
