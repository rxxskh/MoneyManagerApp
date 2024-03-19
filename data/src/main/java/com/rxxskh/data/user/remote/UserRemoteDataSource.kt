package com.rxxskh.data.user.remote

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.rxxskh.utils.FirebaseReferencesProvider
import com.rxxskh.utils.NameTakenException
import com.rxxskh.data.user.remote.model.UserData
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class UserRemoteDataSource @Inject constructor() {

    suspend fun saveUser(userData: UserData): Boolean {
        if (isNameTaken(userLogin = userData.user_login ?: "").not()) {
            val id = FirebaseReferencesProvider.USERS_REF.push().key!!
            FirebaseReferencesProvider.USERS_REF.child(id).setValue(userData.copy(user_id = id))
            return true
        }
        throw NameTakenException()
    }

    suspend fun checkUser(userData: UserData): UserData {
        val dbRef = FirebaseReferencesProvider.USERS_REF
        val dbOrdered = dbRef.orderByChild("userLogin").equalTo(userData.user_login)
        return suspendCoroutine { continuation ->
            dbOrdered.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        for (snap in snapshot.children) {
                            val data = snap.getValue(UserData::class.java)
                            if (data != null) {
                                if (userData.user_password == data.user_password) {
                                    continuation.resume(data)
                                    break
                                }
                            }
                        }
                    } else {
                        continuation.resume(UserData())
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