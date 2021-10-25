package com.example.messenger.util

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.messenger.dao.IDAOLessons
import com.example.messenger.model.CMessage
import com.example.messenger.util.converters.CConverterUUID

@Database(entities = [CMessage::class], version = 1)
@TypeConverters(CConverterUUID::class)
abstract class CDatabase : RoomDatabase() {

    abstract fun daoLessons(): IDAOLessons
    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: CDatabase? = null

        fun getDatabase(context: Context): CDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    CDatabase::class.java,
                    "database.db"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}