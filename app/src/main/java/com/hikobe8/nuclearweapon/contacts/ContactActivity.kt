package com.hikobe8.nuclearweapon.contacts

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.widget.Toast
import com.hikobe8.nuclearweapon.R
import kotlinx.android.synthetic.main.recycler_layout.*

class ContactActivity : AppCompatActivity(), OnItemClickListener, DataObserver {

    override fun onDataChanged() {
        rv_content.adapter = ContactAdapter(DataManager.getGroupedData()).apply {
            setOnItemClickListener(this@ContactActivity)
        }
    }

    companion object {

        const val DELAYED_TIME = 2000L

        fun launch(context: Context) {
            context.startActivity(Intent(context, ContactActivity::class.java))
        }

    }

    private val mAddRunnable = Runnable{
        DataManager.addData(this@ContactActivity, "2-添加.txt")
        Toast.makeText(this@ContactActivity, "添加数据", Toast.LENGTH_SHORT).show()
        rv_content.postDelayed(mRemoveRunnable, DELAYED_TIME)
    }

    private val mRemoveRunnable = Runnable{
        DataManager.removeData(this@ContactActivity, "3-删除.txt")
        Toast.makeText(this@ContactActivity, "删除数据", Toast.LENGTH_SHORT).show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.recycler_layout)
        DataManager.loadData(this, "1-初始.txt")
        DataManager.addDataObserver(this)
        rv_content.layoutManager = LinearLayoutManager(this)
        rv_content.adapter = ContactAdapter(DataManager.getGroupedData()).apply {
            setOnItemClickListener(this@ContactActivity)
        }
        rv_content.postDelayed(mAddRunnable, DELAYED_TIME)
    }

    override fun onDestroy() {
        super.onDestroy()
        rv_content.removeCallbacks(mAddRunnable)
        rv_content.removeCallbacks(mRemoveRunnable)
        DataManager.removeDataObserver(this)
        DataManager.clear()
    }

    override fun onItemClick(personId: Int) {
        ContactDetailActivity.launch(this, personId)
    }

}
