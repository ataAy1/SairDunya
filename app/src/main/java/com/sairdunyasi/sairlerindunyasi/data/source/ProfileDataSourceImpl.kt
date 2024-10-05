package com.sairdunyasi.sairlerindunyasi.data.source

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.sairdunyasi.sairlerindunyasi.domain.model.ProfileModel
import kotlinx.coroutines.tasks.await

class ProfileDataSourceImpl(
    private val firestore: FirebaseFirestore,
    private val firebaseAuth: FirebaseAuth
) : ProfileDataSource {

    val userMail =
        firebaseAuth.currentUser?.email.toString() ?: throw Exception("User not authenticated")

    override suspend fun getUserProfileData(): ProfileModel {

        return try {
            val documentSnapshot =
                firestore.collection("sairProfil").document(userMail).get().await()
            val profileData = documentSnapshot.toObject(ProfileModel::class.java)

            if (profileData == null) {
                throw Exception("Profile data not found")
            }

            return profileData
        } catch (e: Exception) {
            throw e
        }
    }


    override suspend fun updateUserProfileData(sairNicki: String): Boolean {
        val nicknameTaken = isNicknameTaken(sairNicki)

        if (nicknameTaken) {
            return false
        }

        try {
            val userData = mapOf("sairNicki" to sairNicki)
            firestore.collection("sairProfil").document(userMail).update(userData).await()
            return true
        } catch (e: Exception) {
            return false
        }
    }

    private suspend fun isNicknameTaken(newNickname: String): Boolean {
        return try {
            val snapshot = firestore.collection("sairProfil")
                .get()
                .await()

            snapshot.documents.any { document ->
                val existingNickname = document.getString("sairNicki")?.lowercase()
                existingNickname == newNickname.lowercase()
            }
        } catch (e: Exception) {
            false
        }
    }

    override suspend fun updateUserProfileDataWithUri(uri: String): Boolean {
        try {
            val userData = mapOf("dowloandUri" to uri)
            firestore.collection("sairProfil").document(userMail).update(userData).await()
            return true
        } catch (e: Exception) {
            return false
        }
    }

}