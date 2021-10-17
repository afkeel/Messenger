package com.example.messenger.model

import org.joda.time.LocalDateTime

class CMessage (
    var username : String,
    var msg : String,
    var dateTime: LocalDateTime
)