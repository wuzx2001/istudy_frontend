package com.seecolab.istudy.data.local.dao

import androidx.room.*
import com.seecolab.istudy.data.model.User
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Query("SELECT * FROM users WHERE phoneNumber = :phoneNumber")
    suspend fun getUserByPhone(phoneNumber: String): User?
    
    @Query("SELECT * FROM users WHERE isLoggedIn = 1 LIMIT 1")
    suspend fun getCurrentUser(): User?
    
    @Query("SELECT * FROM users WHERE isLoggedIn = 1 LIMIT 1")
    fun getCurrentUserFlow(): Flow<User?>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: User)
    
    @Update
    suspend fun updateUser(user: User)
    
    @Query("UPDATE users SET isLoggedIn = 0")
    suspend fun logoutAllUsers()
    
    @Query("UPDATE users SET isLoggedIn = 1, lastLoginTime = :loginTime WHERE phoneNumber = :phoneNumber")
    suspend fun loginUser(phoneNumber: String, loginTime: Long = System.currentTimeMillis())
    
    @Delete
    suspend fun deleteUser(user: User)
}