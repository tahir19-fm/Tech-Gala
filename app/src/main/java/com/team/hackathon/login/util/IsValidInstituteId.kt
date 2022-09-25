package com.team.hackathon.login.util

import java.util.regex.Pattern

fun isValidId(id: String) : Boolean {
    val REG ="^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$"
    val PATTERN: Pattern = Pattern.compile(REG)
    fun CharSequence.isValid(): Boolean = PATTERN.matcher(this).find()
    return id.isValid()
}