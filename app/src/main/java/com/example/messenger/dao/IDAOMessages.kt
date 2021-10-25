package com.example.messenger.dao

import androidx.room.*
import com.example.messenger.model.CMessage
import kotlinx.coroutines.flow.Flow
import java.util.*

@Dao
interface IDAOLessons {

    @Query("SELECT * FROM messages")
    suspend fun getAll(): List<CMessage>

    @Query("SELECT * FROM messages")
    fun getAllFlow(): Flow<List<CMessage>>

    @Query("SELECT * FROM messages WHERE id = :first")
    suspend fun findByName(first: UUID): CMessage

    @Insert
    suspend fun insert(lesson: CMessage)

    @Update
    suspend fun update(lesson: CMessage)

    @Delete
    suspend fun delete(lesson: CMessage)
}