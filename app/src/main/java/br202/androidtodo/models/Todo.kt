package br202.androidtodo.models

import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.ServerTimestamp

data class Todo(
    @DocumentId
    val id: String = "",
    var title: String = "",
    var description: String = "",
    @ServerTimestamp
    val timestamp: Timestamp? = null,
)
