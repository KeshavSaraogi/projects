package com.example.whatsappclone

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.whatsappclone.data.COLLECTION_USER
import com.example.whatsappclone.data.Event
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AppViewModel @Inject constructor(
    val auth   : FirebaseAuth,
    val db     : FirebaseFirestore,
    val storage: FirebaseStorage
    ) : ViewModel() {

    val inProgress = mutableStateOf(false)
    val popUpNotification = mutableStateOf<Event<String>?>(null)
    val signedIn = mutableStateOf(false)

    init {

    }

    fun onSignup(name: String, number: String, email: String, password: String) {
        if (name.isEmpty() or number.isEmpty() or email.isEmpty() or password.isEmpty()) {
            handleException(customMessage = "Please Fill In All The Fields")
            return
        }

        inProgress.value = true
        db.collection(COLLECTION_USER).whereEqualTo("number", number)
            .get()
            .addOnSuccessListener {
                if (it.isEmpty) {
                    auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener{task ->
                            if (task.isSuccessful) {
                                signedIn.value = true
                                // create user profile
                            }
                            else {
                                handleException(task.exception, "SIGNUP FAILED")
                            }
                        }
                }
                else {
                    handleException(customMessage = "Number Already Exists")
                }
                inProgress.value = false
            }
            .addOnFailureListener {
                handleException(it)
            }
    }

    private fun handleException(exception: Exception? = null, customMessage: String = "") {
        Log.e("Whatsapp Clone", "Chat Application Exception", exception)
        exception?.printStackTrace()
        val errorMsg = exception?.localizedMessage ?: ""
        val message = if (customMessage.isEmpty()) errorMsg else "$customMessage: $errorMsg"
        popUpNotification.value = Event(message)
        inProgress.value = false
    }

}