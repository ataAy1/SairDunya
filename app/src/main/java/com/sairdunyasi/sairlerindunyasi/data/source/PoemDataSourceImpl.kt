package com.sairdunyasi.sairlerindunyasi.data.source

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.sairdunyasi.sairlerindunyasi.data.utils.DateUtils
import com.sairdunyasi.sairlerindunyasi.domain.model.PoemModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import java.sql.Timestamp

class PoemDataSourceImpl(
    private val firestore: FirebaseFirestore,
    private val firebaseAuth: FirebaseAuth
) : PoemDataSource {

    val usermail = firebaseAuth.currentUser?.email.toString() ?: throw Exception("User not authenticated")

    override suspend fun getUserPoems(): Flow<List<PoemModel>> = flow {
        try {

            val snapshot = firestore.collection("siirler")
                .whereEqualTo("usermail", usermail)
                .get()
                .await()


            val poems = snapshot.documents.map { document ->
                val siir = document.getString("siir") ?: ""
                val siirBasligi = document.getString("siirBasligi") ?: ""
                val dateString = document.getString("date") ?: "Unknown Date"

                PoemModel(
                    id = document.id,
                    siir = siir,
                    siirBasligi = siirBasligi,
                    userEmail = usermail,
                    date = dateString
                )
            }

            emit(poems)
        } catch (e: Exception) {
            emit(emptyList())
        }
    }

    override suspend fun deletePoem(poemId: String): Boolean {
        return try {
            firestore.collection("siirler").document(poemId).delete().await()

            true
        } catch (e: Exception) {
            false
        }
    }

    override suspend fun addPoem(title: String, content: String): Boolean {
        return try {
            val currentDateInTurkey = DateUtils.getCurrentDateInTurkey()
            println(currentDateInTurkey)

            val poemData = hashMapOf(
                "siirBasligi" to title,
                "siir" to content,
                "usermail" to usermail,
                "date" to currentDateInTurkey
            )

            val documentReference = firestore.collection("siirler").add(poemData).await()

            true
        } catch (e: Exception) {
            false
        }
    }

}