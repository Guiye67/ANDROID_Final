package baeza.guillermo.gymandyang.login.data.datastore

import baeza.guillermo.gymandyang.login.data.dto.UserDTO

interface IUserPreferences {
    suspend fun addUser(key: String, value: String)
    suspend fun getUser(key: String): UserDTO?
    suspend fun clearData()
}