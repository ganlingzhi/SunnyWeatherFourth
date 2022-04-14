package com.example.sunnyweather.logic.model

data class Person(
    val name: String,
    var telephone: String,
    var telephoneCount: Int,
    var duration: Int
) : Comparable<Person> {
    override fun compareTo(other: Person): Int {

        if (this.duration > other.duration) {
            return 1
        }else if (this.duration < other.duration) {
            return -1
        } else {
            if (this.telephoneCount > other.telephoneCount) {
                return 1
            } else if (this.telephoneCount < other.telephoneCount) {
                return -1

            } else return 0
        }
    }
}


