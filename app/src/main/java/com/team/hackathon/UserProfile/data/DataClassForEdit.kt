package com.team.hackathon.UserProfile.data


data class DataClassForEdit (
    val user: User
)

data class User(
    val _id: String,
    val name: String,
    val address: AddressForEdit,
    val weight : String,
    val Height : String,
    val gender : String,
    val age : String,
    val phoneNumber : String,
    val foodPreferences: FoodPreferencesForEdit,
    val profilePicture : String?=null,
    val dob : Long ? = null
)

data class AddressForEdit(
    val country: String,
    val city: String,
)


data class FoodPreferencesForEdit(
    val allergies: List<String>,
    val type : String
)



