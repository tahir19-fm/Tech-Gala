package com.team.hackathon.UserProfile.data


data class UserDataClassForEdit (
    val user: UserForProfileEdit
)

data class UserForProfileEdit(
    val collage_id: String,
    val name: String,
    val year : String,
    val branch : String,
    val gender : String,
    val age : String,
    val phoneNumber : String,
    val interest : String ,
    val city : String ? = null,
    val collageName : String? = null
)


