    package com.sairdunyasi.sairlerindunyasi.data.source

    import android.util.Log
    import com.google.firebase.auth.FirebaseAuth
    import com.google.firebase.firestore.FirebaseFirestore
    import com.sairdunyasi.sairlerindunyasi.domain.model.ProfileModel
    import kotlinx.coroutines.tasks.await

    class AuthenticationDataSourceImpl(
        private val firebaseAuth: FirebaseAuth,
        private val firestore: FirebaseFirestore

    ) : AuthenticationDataSource {

        override suspend fun login(email: String, password: String): Result<Unit> {
            return try {

                firebaseAuth.signInWithEmailAndPassword(email, password).await()
                Result.success(Unit)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }

        override suspend fun logout(): Result<Unit> {
            return try {
                firebaseAuth.signOut()
                val currentUser = firebaseAuth.currentUser
                Result.success(Unit)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }


        override suspend fun register(userProfile: ProfileModel, password: String): Result<Unit> {
            return try {
                val nicknameExists = firestore.collection("sairProfil")
                    .whereEqualTo("sairNicki", userProfile.sairNicki)
                    .get()
                    .await()
                    .isEmpty.not()

                if (nicknameExists) {
                    return Result.failure(Exception("Nickname is already taken. Please choose another one."))
                }

                firebaseAuth.createUserWithEmailAndPassword(userProfile.usermail, password).await()

                firestore.collection("sairProfil")
                    .document(userProfile.usermail)
                    .set(userProfile)
                    .await()

                Result.success(Unit)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }

        override suspend fun resetPassword(email: String): Result<Unit> {
            return try {
                firebaseAuth.sendPasswordResetEmail(email).await()
                Result.success(Unit)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }