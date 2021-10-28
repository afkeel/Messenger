package com.example.messenger.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.joda.time.LocalDateTime
import java.util.*

@Entity(tableName = "post")
class CMessage (
    @PrimaryKey var id: UUID,
    @ColumnInfo(name = "user_name") var userName: String,
    @ColumnInfo(name = "msg") var msg: String,
    @ColumnInfo(name = "date_time") var dateTime: LocalDateTime,
)