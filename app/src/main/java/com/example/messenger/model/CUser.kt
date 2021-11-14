package com.example.messenger.model

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
class CUser(val userName: String? = null, val userSurname: String?, val userEmail: String? = null)