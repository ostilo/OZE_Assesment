package com.oze.dev.utils

import java.util.*


fun String.convertStringToUpperCase(): String {
    /*
     * Program that first convert all uper case into lower case then
     * convert fist letter into uppercase
     */
    val calStr = this.split(" ").map { it ->
        it.lowercase(Locale.ENGLISH)
            .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.ENGLISH) else it.toString() }
    }
    return calStr.joinToString(separator = " ")
}

fun String.shortStringLength(length: Int): String {
    /*
     * Program that first convert all uper case into lower case then
     * convert fist letter into uppercase
     */
    var calStr = this
    if (this.length > length)
        calStr = this.substring(0, length).plus("...")
    return calStr
}

