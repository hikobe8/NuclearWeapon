package com.hikobe8.nuclearweapon.contacts

/***
 *  Author : ryu18356@gmail.com
 *  Create at 2019-02-11 16:36
 *  description :
 */
data class HistoryDetailBean(
    var name: String,
    var number: String,
    var type: String,
    var time: String,
    var info: String,
    var personId: Int = (name + number + type).hashCode()



) {
    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + number.hashCode()
        result = 31 * result + type.hashCode()
        result = 31 * result + time.hashCode()
        result = 31 * result + info.hashCode()
        result = 31 * result + personId
        return result
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as HistoryDetailBean

        if (name != other.name) return false
        if (number != other.number) return false
        if (type != other.type) return false
        if (time != other.time) return false
        if (info != other.info) return false
        if (personId != other.personId) return false

        return true
    }
}