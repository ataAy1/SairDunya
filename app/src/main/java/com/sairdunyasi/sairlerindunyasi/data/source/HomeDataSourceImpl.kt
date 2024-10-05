package com.sairdunyasi.sairlerindunyasi.data.source

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.sairdunyasi.sairlerindunyasi.data.utils.DateUtils
import com.sairdunyasi.sairlerindunyasi.domain.model.LikedUserModel
import com.sairdunyasi.sairlerindunyasi.domain.model.PoemWithProfileModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.flow

class HomeDataSourceImpl(
    private val firestore: FirebaseFirestore,
    private val firebaseAuth: FirebaseAuth
)

    : HomeDataSource {

    val currentUserMail =
        firebaseAuth.currentUser?.email.toString() ?: throw Exception("User not authenticated")


    override suspend fun getAllPoems(): Flow<List<PoemWithProfileModel>> = callbackFlow {
        val poemsCollection = firestore.collection("siirler")

        val listener = poemsCollection.addSnapshotListener { snapshot, error ->
            if (error != null) {
                close(error)
                return@addSnapshotListener
            }

            if (snapshot != null && !snapshot.isEmpty) {
                CoroutineScope(Dispatchers.IO).launch {
                    val poemsWithProfile = snapshot.documents.map { document ->
                        val siir = document.getString("siir") ?: ""
                        val siirBasligi = document.getString("siirBasligi") ?: ""
                        val userMail = document.getString("usermail") ?: ""
                        val documentDateString: String =
                            document.get("date")?.toString() ?: DateUtils.getCurrentDateInTurkey()

                        val userProfile = firestore.collection("sairProfil")
                            .document(userMail)
                            .get()
                            .await()

                        val userNick = userProfile.getString("sairNicki") ?: "Unknown Nick"
                        val userProfilePhoto = userProfile.getString("dowloandUri") ?: ""
                        val userEmail = userProfile.getString("usermail") ?: ""

                        val likesSnapshot = firestore.collection("likes")
                            .whereEqualTo("poemID", document.id)
                            .get()
                            .await()

                        val likesCount = likesSnapshot.size()

                        val isFavorite = likesSnapshot.documents.any {
                            it.getString("mail") == currentUserMail
                        }

                        PoemWithProfileModel(
                            id = document.id,
                            siir = siir,
                            siirBasligi = siirBasligi,
                            date = documentDateString,
                            userNick = userNick,
                            userProfilePhoto = userProfilePhoto,
                            userEmail = userEmail,
                            isFavorite = isFavorite,
                            counterFavNumber = likesCount
                        )
                    }.shuffled()

                    trySend(poemsWithProfile)
                }
            }
        }

        awaitClose {
            listener.remove()
        }
    }


    override suspend fun addLike(poemId: String) {
        try {

            firestore.collection("likes")
                .add(mapOf("poemID" to poemId, "mail" to currentUserMail))
                .await()
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun removeLike(poemId: String) {
        try {
            val likesQuery = firestore.collection("likes")
                .whereEqualTo("poemID", poemId)
                .whereEqualTo("mail", currentUserMail)
                .get()
                .await()

            likesQuery.documents.forEach { document ->
                firestore.collection("likes").document(document.id).delete().await()
            }
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun fetchUsersWhoLiked(poemId: String): Flow<List<LikedUserModel>> = flow {
        try {
            val likesSnapshot = firestore.collection("likes")
                .whereEqualTo("poemID", poemId)
                .get()
                .await()

            val likedUsers = likesSnapshot.documents.map { document ->
                val userMail = document.getString("mail") ?: ""
                val userProfile =
                    firestore.collection("sairProfil").document(userMail).get().await()
                LikedUserModel(
                    userId = userMail,
                    userNick = userProfile.getString("sairNicki") ?: "Unknown Nick",
                    userProfilePhoto = userProfile.getString("dowloandUri") ?: ""
                )
            }
            emit(likedUsers)

        } catch (e: Exception) {
            emit(emptyList<LikedUserModel>())
        }
    }
}