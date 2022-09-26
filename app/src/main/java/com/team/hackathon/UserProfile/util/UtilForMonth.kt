package com.team.hackathon.UserProfile.util

class UtilForMonth {
    companion object {

    fun returnMonthNumber(month: String): Int? {
        var number: Int? = null
        if (month == "January") {
            number = 0
        } else if (month == "February") {
            number = 1
        } else if (month == "March") {
            number = 2
        } else if (month == "April") {
            number = 3
        } else if (month == "May") {
            number = 4
        } else if (month == "June") {
            number = 5
        } else if (month == "July") {
            number = 6
        } else if (month == "August") {
            number = 7
        } else if (month == "September") {
            number = 8
        } else if (month == "October") {
            number = 9
        } else if (month == "November") {
            number = 10
        } else if (month == "December") {
            number = 11
        }
        return number

    }
}

}