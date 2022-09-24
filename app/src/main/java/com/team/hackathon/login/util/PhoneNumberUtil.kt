package com.team.hackathon.login.util

import java.util.regex.Pattern

//Only for indian number
fun isValidPhoneNumber(number: String) : Boolean {
    val REG = "^(\\+91[\\-\\s]?)?[0]?(91)?[789]\\d{9}\$"
    val PATTERN: Pattern = Pattern.compile(REG)
    fun CharSequence.isValid(): Boolean = PATTERN.matcher(this).find()
    return number.isValid()
}