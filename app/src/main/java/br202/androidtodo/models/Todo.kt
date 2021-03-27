package br202.androidtodo.models

import br202.androidtodo.services.AuthService
import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.ServerTimestamp

data class Todo(
    @DocumentId
    val id: String = "",
    var title: String = "",
    var description: String = "",
    val user_id: String? = AuthService.user?.uid,
    @ServerTimestamp
    val timestamp: Timestamp? = null,
)
