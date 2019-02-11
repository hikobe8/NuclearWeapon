package com.hikobe8.nuclearweapon.contacts

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hikobe8.nuclearweapon.R
/***
 *  Author : ryu18356@gmail.com
 *  Create at 2019-02-11 18:33
 *  description :
 */
class ContactAdapter(val dataList:ArrayList<HistoryDetailBean>): RecyclerView.Adapter<ContactHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ContactHolder
    = ContactHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_contact, parent, false))

    override fun getItemCount() = dataList.size

    override fun onBindViewHolder(p0: ContactHolder, p1: Int) {
    }


}

class ContactHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {



}