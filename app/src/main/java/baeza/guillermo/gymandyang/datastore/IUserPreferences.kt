package baeza.guillermo.gymandyang.datastore

import baeza.guillermo.gymandyang.ui.models.User

interface IUserPreferences {
    suspend fun addUser(key: String, value: String)
    suspend fun getUser(key: String): User?
}