package com.hikobe8.nuclearweapon.contacts

import android.content.Context
import android.text.TextUtils
import android.util.SparseArray
import java.io.BufferedReader
import java.io.InputStreamReader

/***
 *  Author : ryu18356@gmail.com
 *  Create at 2019-02-11 16:27
 *  description :
 */
object DataManager {

    private val contactDataMap = SparseArray<ArrayList<HistoryDetailBean>>()

    fun loadData(context: Context, assetsPath: String) {
        val inputStream = context.assets.open(assetsPath)
        val bIs = BufferedReader(InputStreamReader(inputStream))
        val dataList = ArrayList<HistoryDetailBean>()
        var exit = false
        while (exit) {
            val line = bIs.readLine()
            if (TextUtils.isEmpty(line)) {
                exit = true
                continue
            }
            val split = line.split(Regex("\\s+"), 0)
            dataList += HistoryDetailBean(
                split[0], split[1], split[2], split[3], split[4]
            )
        }
        groupData(dataList)
    }

    private fun groupData(originDataList: List<HistoryDetailBean>) {

        for (historyDetailBean in originDataList) {
            if (contactDataMap.get(historyDetailBean.personId) == null) {
                contactDataMap.put(historyDetailBean.personId, ArrayList<HistoryDetailBean>().apply {
                    add(historyDetailBean)
                })
            } else {
                contactDataMap.get(historyDetailBean.personId).add(historyDetailBean)
            }
        }
    }

    fun getGroupedData(): ArrayList<HistoryDetailBean> {
        val dataList = ArrayList<HistoryDetailBean>()
        val size = contactDataMap.size()
        for (i in 0 until size) {
            dataList[i] = contactDataMap.valueAt(i)[0].apply {
                info = "${contactDataMap.valueAt(i).size}个电话"
            }
        }
        return dataList
    }

    fun getDataList(personId: Int) = contactDataMap.get(personId)

}
