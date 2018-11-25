package com.example.adeelahmed.contactaddtask.Adapters

import android.app.AlertDialog
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.support.annotation.RequiresApi
import android.support.v4.app.ActivityCompat
import android.support.v4.app.ActivityCompat.startActivityForResult
import android.support.v4.content.ContextCompat
import android.support.v4.content.ContextCompat.startActivity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.example.adeelahmed.contactaddtask.MainActivity
import com.example.adeelahmed.contactaddtask.R
import com.example.adeelahmed.contactaddtask.UserContactDetail

class RecycleViewAdapter (
    var ctx:Context,
    var userList:ArrayList<UserContactDetail>
) : RecyclerView.Adapter<RecycleViewAdapter.customViewHolder>() {
    var contextPosition:Int = 0
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): customViewHolder {
        val view:View = LayoutInflater.from(ctx).inflate(R.layout.card_row, null)
        //contextPosition = view.tag.toString().toInt()

       view.setOnClickListener {
            showPopup(ctx, p1)
        }
        view.setOnLongClickListener {
            deleteUser(p1)
//            notifyItemRemoved(p1)
//            notifyItemChanged(p1)
            true
        }
        return customViewHolder(view)
    }
    private fun deleteUser(id:Int) {
        userList.removeAt(id)
        notifyDataSetChanged()
        Toast.makeText(ctx,"Contact Deleted",Toast.LENGTH_SHORT).show()
    }
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun showPopup(ctx:Context, position: Int) {
        // getting data of user from RecycleView Array List
        var userDetail = userList[position]
        var userName = userDetail.userName
        var userContactNum = userDetail.userContactNumber
        var userImage = userDetail.UserImage

        var alertPopup = AlertDialog.Builder(ctx)
        // creating view of Aleart dialogue for showing data of user
        var builderView:View = LayoutInflater.from(ctx).inflate(R.layout.alert_dialogue_view, null)
        // getting view of Custom Layout (alert_Dialouge_view)
        var userNameblder:TextView = builderView.findViewById(R.id.alert_name)
        var userNumber: TextView = builderView.findViewById(R.id.alert_number)
        var userImg: ImageView = builderView.findViewById(R.id.alert_img)
        // Parsing data from User List (Recycle) to AleartBuilder view
        userNameblder.text = userName
        userNumber.text =   userContactNum
        userImg.setImageBitmap(userImage)
        alertPopup.create()
        var dismiss = alertPopup.create()
        // Setting Custom view to AlertDialogue Builder
        alertPopup.setView(builderView)
        alertPopup.setMessage("Do you want to make a call to " + userName+"?")
        alertPopup.setTitle("Make a phone call")
        alertPopup.setIcon(R.drawable.call)
        alertPopup.setPositiveButton("No", object :DialogInterface.OnClickListener{
            override fun onClick(p0: DialogInterface?, p1: Int) {
                dismiss.dismiss()
            }
        })
        alertPopup.setNegativeButton("Yes", object : DialogInterface.OnClickListener {
            override fun onClick(p0: DialogInterface?, p1: Int) {
                try {
                    var intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel: "+userContactNum))
                    startActivity(ctx,intent,null)
                } catch (e: ActivityNotFoundException) {
                    Toast.makeText(ctx,e.message.toString(),Toast.LENGTH_SHORT).show()
                }
            }})
        //view =LayoutInflater.from(ctx).inflate(R.layout.alert_dialogue_view,null)
        alertPopup.show()
    }
    override fun getItemCount(): Int {
        return userList.size
    }
    override fun onBindViewHolder(holder: customViewHolder, position: Int) {
        val userDetail = userList[position]
        holder.contactName.text = userDetail.userName
       holder.contactNumber.text =  userDetail.userContactNumber
        holder.contactImage.setImageBitmap(userDetail.UserImage)
        this.contextPosition = userList.indexOf(userDetail)
        //        var bitmapDrawable: BitmapDrawable? = holder.contactImage.getDrawable() as? BitmapDrawable
//        var bitMapImg = bitmapDrawable!!.bitmap
//        holder.contactImage = userDetail.UserImage = bitMapImg
    }
    inner class customViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var contactImage:ImageView = view.findViewById(R.id.contact_image)
        var contactName:TextView = view.findViewById(R.id.contact_name)
        var contactNumber:TextView = view.findViewById(R.id.contact_number)
    }
}