package com.example.messenger.util.converters

import androidx.room.TypeConverter
import org.joda.time.LocalDateTime
import java.util.*

class CConverterUUID {
    @TypeConverter
    fun stringFromUUID(uuid: UUID): String{
        return uuid.toString()
    }
    @TypeConverter
    fun uuidFromString(string: String?): UUID {
        return UUID.fromString(string)
    }
    @TypeConverter
    fun stringFromDateTime(dateTime: LocalDateTime): String{
        return dateTime.toString()
    }
    @TypeConverter
    fun localDateTimeToString(string: String?): LocalDateTime {
        return LocalDateTime.parse(string)
    }
}