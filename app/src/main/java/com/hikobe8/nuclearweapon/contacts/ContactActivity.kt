package com.hikobe8.nuclearweapon.contacts

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.hikobe8.nuclearweapon.R
import kotlinx.android.synthetic.main.recycler_layout.*

class ContactActivity : AppCompatActivity() {

    companion object {

        fun launch(context: Context){
            context.startActivity(Intent(context, ContactActivity::class.java))
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.recycler_layout)
        rv_content
    }
}
