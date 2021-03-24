package br202.androidtodo.repositories

import br202.androidtodo.models.User

object UserRepository {
    private val users = mutableListOf<User>(
        User("br", "br")
    )

    fun getByEmail(email: String): User? {
        return users.find { it.email == email }
    }

    fun save(user: User): Boolean {
        return users.add(user)
    }
}