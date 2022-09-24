package com.team.hackathon.login.data



data class UserRegistrationDto(

    val user: User
)

data class User(

    val id: String?=null,

    var name: String?=null,

    val phone: String?=null,

    val gender:String?=null,

    val dob:Long?=null,

    val address:Address,
)



data class Address(

    val country: String?=null,

    val city: String?=null,
)





