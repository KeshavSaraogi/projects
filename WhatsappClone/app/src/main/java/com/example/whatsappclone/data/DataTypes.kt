package com.example.whatsappclone.data

data class UserData(
    val userID   : String? = "",
    val name     : String? = "",
    val number   : String? = "",
    val imageURL : String? = "",
) {
    fun toMap() = mapOf(
        "userID" to userID,
        "name" to name,
        "number" to number,
        "imageURL" to imageURL
    )
}