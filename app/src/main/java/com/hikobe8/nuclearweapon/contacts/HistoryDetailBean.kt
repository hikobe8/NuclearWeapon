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
    var personId: Int = (name + number).hashCode()
)