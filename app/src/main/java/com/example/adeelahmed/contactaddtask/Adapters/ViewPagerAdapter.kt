package com.example.adeelahmed.contactaddtask.Adapters

import android.content.Context
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.example.adeelahmed.contactaddtask.Fragments.ContactAddFragment
import com.example.adeelahmed.contactaddtask.Fragments.ContactDetailViewFragment
import java.text.FieldPosition

class ViewPagerAdapter (var ctx:Context ,fm: FragmentManager) : FragmentPagerAdapter(fm) {
    override fun getItem(position: Int): Fragment {
        when(position) {
            0 ->    return ContactAddFragment()
            else -> return ContactDetailViewFragment()
        }
    }
    override fun getCount(): Int {
        return 2
    }
    override fun getPageTitle(position: Int): CharSequence? {
            when(position) {
                0 -> return "Add Contact Detail"
                else -> return   "View Contact"
            }
    }
}