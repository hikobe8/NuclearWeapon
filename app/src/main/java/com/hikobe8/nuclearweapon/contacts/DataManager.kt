package com.hikobe8.nuclearweapon.contacts

import android.content.Context
import android.text.TextUtils
import android.util.SparseArray
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.Reader
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

/***
 *  Author : ryu18356@gmail.com
 *  Create at 2019-02-11 16:27
 *  description :
 */
object DataManager {

    private val contactDataMap = SparseArray<ArrayList<HistoryDetailBean>>()
    private val observers = ArrayList<DataObserver>() //数据观察者

    fun addDataObserver(observer: DataObserver){
        observers += observer
    }

    fun removeDataObserver(observer: DataObserver){
        observers.remove(observer)
    }

    fun clear(){
        contactDataMap.clear()
    }

    fun loadData(context: Context, assetsPath: String) {
        val inputStream = context.assets.open(assetsPath)
        val bIs = BufferedReader(InputStreamReader(inputStream) as Reader?)
        val dataList = ArrayList<HistoryDetailBean>()
        var exit = false
        while (!exit) {
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
        dataList.sortWith(HistoryComparator())
        groupData(dataList)
    }

    fun addData(context: Context, assetsPath: String) {
        loadData(context, assetsPath)
        for (observer in observers) {
            observer.onDataChanged()
        }
    }

    fun removeData(context: Context, assetsPath: String) {
        val inputStream = context.assets.open(assetsPath)
        val bIs = BufferedReader(InputStreamReader(inputStream) as Reader?)
        val dataList = ArrayList<HistoryDetailBean>()
        var exit = false
        while (!exit) {
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
        for (historyDetailBean in dataList) {
           if (contactDataMap.get(historyDetailBean.personId) != null) {
               val size = contactDataMap.get(historyDetailBean.personId).size
               for (i in size-1 downTo  0) {
                   if (contactDataMap.get(historyDetailBean.personId)[i] == historyDetailBean) {
                       contactDataMap.get(historyDetailBean.personId).removeAt(i)
                   }
               }
           }
        }
        for (observer in observers) {
            observer.onDataChanged()
        }
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
            dataList += contactDataMap.valueAt(i)[0].copy().apply {
                info = "${contactDataMap.valueAt(i).size}个电话"
            }
        }
        dataList.sortWith(HistoryComparator())
        return dataList
    }

    fun getDataList(personId: Int) = contactDataMap.get(personId)!!

}

class HistoryComparator : Comparator<HistoryDetailBean> {

    override fun compare(o1: HistoryDetailBean?, o2: HistoryDetailBean?): Int {
        return try {
            if (TextUtils.equals(o1?.time, o2?.time)) {
                0
            } else {
                val simpleDateFormat = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
                val lTime = simpleDateFormat.parse(o1?.time)
                val rTime = simpleDateFormat.parse(o2?.time)
                if (lTime.before(rTime)) 1 else -1
            }
        } catch (e: Exception) {
            0
        }

    }

}

interface DataObserver {
    fun onDataChanged()
}