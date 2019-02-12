package com.hikobe8.nuclearweapon.contacts

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.hikobe8.nuclearweapon.R
import kotlinx.android.synthetic.main.recycler_layout.*

class ContactDetailActivity : AppCompatActivity(), DataObserver {

    override fun onDataChanged() {
        val personId = intent.getIntExtra("id", -1)
        rv_content.adapter = ContactAdapter(DataManager.getDataList(personId))
    }

    companion object {

        fun launch(activity: Activity, personId:Int){
            activity.startActivity(Intent(activity, ContactDetailActivity::class.java).apply {
                putExtra("id", personId)
            })
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.recycler_layout)
        DataManager.addDataObserver(this)
        rv_content.layoutManager = LinearLayoutManager(this)
        val personId = intent.getIntExtra("id", -1)
        rv_content.adapter = ContactAdapter(DataManager.getDataList(personId))
    }

    override fun onDestroy() {
        super.onDestroy()
        DataManager.removeDataObserver(this)
    }

}
