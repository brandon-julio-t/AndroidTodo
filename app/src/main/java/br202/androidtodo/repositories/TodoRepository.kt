package br202.androidtodo.repositories

import br202.androidtodo.models.Todo
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot

object TodoRepository {
    private val db get() = FirebaseFirestore.getInstance().collection("todos")

    fun getAll(callback: (it: QuerySnapshot) -> Unit) {
        db.get().addOnSuccessListener { callback(it) }
    }

    fun save(todo: Todo, callback: (it: DocumentReference) -> Unit) {
        db.add(todo).addOnSuccessListener { callback(it) }
    }

    fun update(todo: Todo, callback: () -> Unit) {
        db.document(todo.id).set(todo).addOnSuccessListener { callback() }
    }

    fun delete(id: String, callback: () -> Unit) {
        db.document(id).delete().addOnSuccessListener { callback() }
    }
}