package com.example.messenger.dao

import androidx.room.*
import com.example.messenger.model.CMessage
import kotlinx.coroutines.flow.Flow
import java.util.*

@Dao
interface IDAOLessons {

    @Query("SELECT * FROM post")
    suspend fun getAll(): List<CMessage>

    @Query("SELECT * FROM post")
    fun getAllFlow(): Flow<List<CMessage>>

    @Query("SELECT * FROM post WHERE id = :first")
    suspend fun findByName(first: UUID): CMessage

    @Insert
    suspend fun insert(post: CMessage)

    @Update
    suspend fun update(post: CMessage)

    @Delete
    suspend fun delete(post: CMessage)
}