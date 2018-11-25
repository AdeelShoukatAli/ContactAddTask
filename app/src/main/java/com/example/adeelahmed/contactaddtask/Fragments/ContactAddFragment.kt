package com.example.adeelahmed.contactaddtask.Fragments


import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.support.v4.app.ActivityCompat
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import com.example.adeelahmed.contactaddtask.R
import com.example.adeelahmed.contactaddtask.UserContactDetail
import kotlinx.android.synthetic.main.fragment_contact_add.*
import java.lang.Exception


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class ContactAddFragment : Fragment() {
    interface myCallBack {
        fun sendObj(userDetail: UserContactDetail)
    }
    var communicator:myCallBack? = null
    var CAMERA_PERMISSION_CODE:Int = 55
    var CAMERA_REQUEST_CODE:Int = 464
    var GALLERY_PERMISSION_CODE = 11
    var GALLERY_REQUEST_CODE = 222
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment_contact_add, container, false)
        var contactImage:ImageView = view.findViewById(R.id.contactImage_et)
        var contactName: EditText = view.findViewById(R.id.contactName_et)
        var contactNumber:EditText = view.findViewById(R.id.contactNumber_et)
        var addbtn: Button = view.findViewById(R.id.add_btn)
        contactImage.setImageResource(R.drawable.user)
     //  contactImage.invalidate()
//       contactImage.buildDrawingCache()
//        val bmap = contactImage.getDrawingCache()
//        val bm = (contactImage.getDrawable() as BitmapDrawable).bitmap
        lateinit var bitMapImg:Bitmap



        contactImage.setOnClickListener {
            var chooserBuilder = AlertDialog.Builder(activity)
            var option: Array<String> = arrayOf("Camera","Gallery")
                chooserBuilder.create()
            var create = chooserBuilder.create()
            chooserBuilder.setTitle("Choose from")
            chooserBuilder.setCancelable(false)
            chooserBuilder.setIcon(R.drawable.gallery)
            chooserBuilder.setItems(option, object: DialogInterface.OnClickListener {
                override fun onClick(p0: DialogInterface?, p1: Int) {
                    if(option[p1].equals("Camera")) {
                        openCamera()
                    }
                    else if (option[p1].equals("Gallery")) {
                        pickImage()
                    }
                    else {
                        create.dismiss()
                    }
                    }})
            chooserBuilder.setNegativeButton("Cancel", object : DialogInterface.OnClickListener {
                override fun onClick(p0: DialogInterface?, p1: Int) {
                    Toast.makeText(activity,"Please choose image", Toast.LENGTH_SHORT).show()
                    create.dismiss()
                }})
            chooserBuilder.show()

         //   openCamera()
        }

        addbtn.setOnClickListener {
         if(contactName.text.length > 0) {
             if(contactNumber.text.length > 0) {
                 if(contactNumber.text.length == 11) {
                     var bitmapDrawable:BitmapDrawable? = contactImage.getDrawable() as? BitmapDrawable
                     bitMapImg = bitmapDrawable!!.bitmap
                     var ContactDetail: UserContactDetail = UserContactDetail(
                         contactName.text.toString(), contactNumber.text.toString(),
                         bitMapImg
                     )
                     if (communicator != null) {
                         communicator?.sendObj(ContactDetail)
                         Toast.makeText(activity,"Contact Saved",Toast.LENGTH_SHORT).show()
                         contactName.setText("")
                         contactNumber.setText("")
                         contactImage.setImageResource(R.drawable.user)
//                    var fragmentManager = getActivity()?.getSupportFragmentManager()
//                    var transaction = this.getFragmentManager()?.beginTransaction();
//                    transaction?.replace(R.id.view_fragment,ContactDetailViewFragment())
//                    transaction?.addToBackStack(null)
//                    transaction?.commit()
//                    Toast.makeText(activity, "Done", Toast.LENGTH_SHORT).show()
                     } else {Toast.makeText(activity, "Null Comunicator", Toast.LENGTH_SHORT).show()}
                 } else {contactNumber.setError("Digits size should be 11 only") }
             } else {contactNumber.setError("Please input numbers")}
             } else { contactName.setError("Contact Name must be Entered")}
         }
        return view
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        try {
            communicator = context as myCallBack?

        } catch (e:Exception) {e.printStackTrace()}
    }

    override fun onDetach() {
        super.onDetach()
        communicator = null
    }

    // funtions
    val IMAGE_PICK = 123

  private  fun openCamera() {
      if(ContextCompat.checkSelfPermission(activity!!, android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED ) {
          Toast.makeText(activity,"Permission granted", Toast.LENGTH_SHORT).show()
          val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
          startActivityForResult(intent, CAMERA_REQUEST_CODE)
      }
          else {
          ActivityCompat.requestPermissions(activity!!, arrayOf(Manifest.permission.CAMERA), CAMERA_PERMISSION_CODE)
      }
      }
    private fun pickImage() {
        if(ContextCompat.checkSelfPermission(activity!!,android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            val intent:Intent = Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(intent,GALLERY_REQUEST_CODE)
        } else {
            ActivityCompat.requestPermissions(activity!!, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),GALLERY_PERMISSION_CODE)
        }

    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode) {
            CAMERA_PERMISSION_CODE -> {
                if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openCamera()
                }
            }
                GALLERY_REQUEST_CODE -> {
                    if(grantResults.size> 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        pickImage()
                    }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(requestCode) {
            CAMERA_REQUEST_CODE -> {
                if (resultCode == Activity.RESULT_OK) {
                    val myData = data?.extras?.get("data")
                    contactImage_et.setImageBitmap(myData as Bitmap)
                }
            }
                GALLERY_REQUEST_CODE -> {
                    if(resultCode == Activity.RESULT_OK) {
                        val imgUri: Uri? = data!!.getData()
                        contactImage_et.setImageURI(imgUri)
                    }
                }
                }

        }



    }



