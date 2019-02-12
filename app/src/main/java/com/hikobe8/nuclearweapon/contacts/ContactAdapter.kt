package com.hikobe8.nuclearweapon.contacts

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hikobe8.nuclearweapon.R
import kotlinx.android.synthetic.main.item_contact.view.*

/***
 *  Author : ryu18356@gmail.com
 *  Create at 2019-02-11 18:33
 *  description :
 */
class ContactAdapter(private val dataList: ArrayList<HistoryDetailBean>) : RecyclerView.Adapter<ContactHolder>() {

    private var mOnItemClickListener:OnItemClickListener?=null

    fun setOnItemClickListener(onItemClickListener:OnItemClickListener) {
        mOnItemClickListener = onItemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ContactHolder =
        ContactHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_contact, parent, false))

    override fun getItemCount() = dataList.size

    override fun onBindViewHolder(holder: ContactHolder, pos: Int) {

        holder.bindData(dataList[pos], mOnItemClickListener)

    }

}

interface OnItemClickListener{
    fun onItemClick(personId:Int)
}

class ContactHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bindData(
        history: HistoryDetailBean,
        onItemClickListener: OnItemClickListener?
    ) {
        itemView.tv_name.text = history.name
        itemView.tv_number.text = history.number
        itemView.tv_time.text = history.time
        itemView.tv_type.text = history.type
        itemView.tv_info.text = history.info
        itemView.setOnClickListener {
            onItemClickListener?.onItemClick(history.personId)
        }
    }

}