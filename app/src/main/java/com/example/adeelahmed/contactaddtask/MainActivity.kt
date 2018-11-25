package com.example.adeelahmed.contactaddtask

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.view.ViewPager
import com.example.adeelahmed.contactaddtask.Adapters.ViewPagerAdapter
import com.example.adeelahmed.contactaddtask.Fragments.ContactAddFragment
import com.example.adeelahmed.contactaddtask.Fragments.ContactDetailViewFragment

class MainActivity : AppCompatActivity(), ContactAddFragment.myCallBack {
    override fun sendObj(userDetail: UserContactDetail) {
    Receiver?.receiveObj(userDetail)
    }
    var Receiver: ContactDetailViewFragment.myReceiver? = null
    fun objectExchange(rcvr : ContactDetailViewFragment.myReceiver) {
        this.Receiver = rcvr
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var viewPager: ViewPager = findViewById(R.id.view_pager)
        var tabLayout:TabLayout = findViewById(R.id.tab_layout)
        tabLayout.setupWithViewPager(viewPager)
        var myAdapter = ViewPagerAdapter(
            this@MainActivity, supportFragmentManager
        )
        viewPager.adapter = myAdapter
        myAdapter.notifyDataSetChanged()
    }
}
