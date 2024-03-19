package com.rxxskh.data.user.remote

import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.rxxskh.data.user.remote.model.UserRemoteData
import com.rxxskh.utils.FirebaseReferencesProvider
import com.rxxskh.utils.NameTakenException
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class UserRemoteDataSource @Inject constructor() {

    suspend fun saveUser(userRemoteData: UserRemoteData): Boolean {
        if (isNameTaken(userLogin = userRemoteData.user_login ?: "").not()) {
            val id = FirebaseReferencesProvider.USERS_REF.push().key!!
            FirebaseReferencesProvider.USERS_REF.child(id).setValue(userRemoteData.copy(user_id = id))
            return true
        }
        throw NameTakenException()
    }

    suspend fun checkUser(userRemoteData: UserRemoteData): String? {
        val dbRef = FirebaseReferencesProvider.USERS_REF
        val dbOrdered = dbRef.orderByChild("user_login").equalTo(userRemoteData.user_login)
        return suspendCoroutine { continuation ->
            dbOrdered.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        for (snap in snapshot.children) {
                            val data = snap.getValue(UserRemoteData::class.java)
                            if (data != null) {
                                if (userRemoteData.user_password == data.user_password) {
                                    continuation.resume(data.user_id!!)
                                    break
                                }
                            }
                        }
                    } else {
                        continuation.resume(null)
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    continuation.resumeWithException(error.toException())
                }
            })
        }
    }

    private suspend fun isNameTaken(userLogin: String): Boolean {
        val dbRef = FirebaseReferencesProvider.USERS_REF
        val dbOrdered = dbRef.orderByChild("userLogin").equalTo(userLogin)
        return suspendCoroutine { continuation ->
            dbOrdered.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    continuation.resume(snapshot.exists())
                }

                override fun onCancelled(error: DatabaseError) {
                    continuation.resumeWithException(error.toException())
                }
            })
        }
    }
}