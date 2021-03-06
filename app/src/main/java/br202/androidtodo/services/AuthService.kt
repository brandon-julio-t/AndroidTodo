package br202.androidtodo.services

import android.content.Context
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

object AuthService {
    val user: FirebaseUser? get() = FirebaseAuth.getInstance().currentUser

    fun login(
        ctx: Context,
        email: String,
        password: String,
        successCallback: () -> Unit,
        failureCallback: () -> Unit,
    ) {
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    return@addOnCompleteListener successCallback()
                }

                it.exception?.let { e ->
                    Toast.makeText(ctx, e.message, Toast.LENGTH_SHORT).show()
                    failureCallback()
                }
            }
    }

    fun logout() = FirebaseAuth.getInstance().signOut()

    fun register(ctx: Context, email: String, password: String, callback: () -> Unit) {
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    return@addOnCompleteListener callback()
                }

                it.exception?.let { e ->
                    Toast.makeText(ctx, e.message, Toast.LENGTH_SHORT).show()
                }
            }
    }
}