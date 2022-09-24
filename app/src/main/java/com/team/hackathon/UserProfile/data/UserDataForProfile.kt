package com.team.hackathon.UserProfile.data


class UserDataForProfile(
    val _id: String,
    val name: String?=null,
    val address: Address,
    val weight : String?=null,
    val Height : String?=null,
    val gender : String?=null,
    val age : String?=null,
    val phoneNumber : String?=null,
    val foodPreferences: FoodPreferences,
    val profilePicture : String?=null,
    val dob : Long ? = null
)


data class Address(
    val country: String?=null,
    val city: String?=null,
)


data class FoodPreferences(
    val allergies: List<String>?=null,
    val type : String?=null
)


