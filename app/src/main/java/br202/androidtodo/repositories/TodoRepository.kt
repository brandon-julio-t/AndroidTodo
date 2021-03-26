package br202.androidtodo.repositories

import br202.androidtodo.models.Todo
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot

object TodoRepository {
    private val db get() = FirebaseFirestore.getInstance().collection("todos")

    fun getAll(callback: (it: Task<QuerySnapshot>) -> Unit) {
        db.get().addOnCompleteListener { callback(it) }
    }

    fun save(todo: Todo, callback: (it: Task<DocumentReference>) -> Unit) {
        db.add(todo).addOnCompleteListener { callback(it) }
    }

    fun update(todo: Todo, callback: (it: Task<Void>) -> Unit) {
        db.document().set(todo) .addOnCompleteListener { callback(it) }
    }

    fun delete(id: String, callback: (it: Task<Void>) -> Unit) {
        db.document(id).delete().addOnCompleteListener { callback(it) }
    }
}