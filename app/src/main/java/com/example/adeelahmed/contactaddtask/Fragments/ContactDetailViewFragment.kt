package com.example.adeelahmed.contactaddtask.Fragments


import android.content.DialogInterface
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.adeelahmed.contactaddtask.Adapters.RecycleViewAdapter
import com.example.adeelahmed.contactaddtask.MainActivity
import com.example.adeelahmed.contactaddtask.R
import com.example.adeelahmed.contactaddtask.UserContactDetail

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class ContactDetailViewFragment : Fragment(){

    interface myReceiver {
        fun receiveObj (userDetail: UserContactDetail)
    }
    var itemPosition = 0
    lateinit var userDetailList: ArrayList<UserContactDetail>
    lateinit var myAdapter: RecycleViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        userDetailList = ArrayList()
    //    userDetailList.add(UserContactDetail("Adeel", "0342",null))

        (activity as MainActivity).objectExchange(object :myReceiver {
            override fun receiveObj(userDetail: UserContactDetail) {
                userDetailList.add(userDetail)
                myAdapter.notifyDataSetChanged()
            }
        })
        var view = inflater.inflate(R.layout.fragment_contact_detail_view, container, false)
        var recyclerView:RecyclerView = view.findViewById(R.id.recycler_view)
        myAdapter = RecycleViewAdapter(activity!!,userDetailList)
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = myAdapter
        return view
    }



}
