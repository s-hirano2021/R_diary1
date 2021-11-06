package com.example.record_diary

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import kotlinx.android.synthetic.main.fragment_record_.*
import kotlinx.android.synthetic.main.list_item.*
import java.util.*


class Mainlistview_Fragment : Fragment() {

    lateinit var storage: FirebaseStorage
    lateinit var CustomAdapter:ListAdapter
    lateinit var dogList: ArrayList<RecordDB>
    lateinit var db:FirebaseFirestore
    lateinit var DBDB:RecordDB
    //lateinit var adapter: ArrayAdapter<String>
   // private var myname = ""
    private val TAG = "MainListView_Fragment"
    lateinit var ImageUri: Uri


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        storage= Firebase.storage
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_mainlistview_, container, false)

        //val list=view?.findViewById<ListView>(R.id.list)
        //storage= Firebase.storage

        return view

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //readFireStoreData()
        //setContentView(R.layout.fragment_mainlistview_)

        dogList= arrayListOf()

        val list=view.findViewById<ListView>(R.id.list)

        CustomAdapter=ListAdapter(requireActivity(),dogList)
        list?.adapter=CustomAdapter

        val Pic_image=list.findViewById<ImageView>(R.id.image_list)


        //画像のダウンロード用の記述
//        val storageImgRef=Firebase.storage.reference
//        db=FirebaseFirestore.getInstance()
//        val ImageDataRef=db.collection("/users")
//        Log.i("画像のダウンロード＋ダウンロード画像取得",ImageDataRef.toString())
//        ImageDataRef.get()
//            .addOnSuccessListener { document ->
//                if (document != null) {
//                    //Log.d(TAG, "DocumentSnapshot data: ${document.data}")
////                    GlideApp.with(requireActivity())
////                     .load("ImageDataUri")
////                     .into(Pic_image)
//                    Log.i("Mainlistview_Fragment","内容222：")
//
//                } else {
//                    Log.d(TAG, "No such document")
//                }
//            }
//            .addOnFailureListener { exception ->
//                Log.d(TAG, "get failed with ", exception)
//            }
//
//        Log.i("Mainlistview_Fragment","内容：${ImageDataRef.path}")
        //val imageRef = storageImgRef.child("images/$0cfbb392-0897-4d85-aff1-14de0082e39d")
        //val imageRef = storageImgRef.child("images/$0cfbb392-0897-4d85-aff1-14de0082e39d")
        //hashmap imagedata imageRefのような記述を行う


        //Log.i("ダウンロードの状況",imageRef.toString())
        //val image=view.findViewById<ImageView>(R.id.image)
        //val image = requireActivity().findViewById<View>(R.id.image) as ImageView
        //val list_item=layoutInflater.inflate(R.layout.list_item,false)
        //val Pic_image = list.findViewById<View>(R.id.image) as ImageView
//Glide処理１
//        getLayoutInflater().inflate(R.layout.list_item, linearLayout2)
//        val Pic_image=list.findViewById<ImageView>(R.id.image_list)
//        DBDB= RecordDB(ImageDataUri = String())

        //val imageView2=view.findViewById<ImageView>(R.id.imageView2)
        //Glide処理２　
//        GlideApp.with(requireActivity())
//            .load(DBDB.ImageDataUri)
//            .into(Pic_image)
//        Log.i("Mainlistview_Fragment","内容222：")


        //画像のダウンロード記述　ナンバー２
//        val storageRef = storage.reference
//        storageRef.child("users/ImageDataUri").downloadUrl.addOnSuccessListener {
//            // Got the download URL for 'users/me/profile.png'
//            Log.d("Uriデータのダウンロード","::ダウンロード成功{$it}")
//            val Pic_image=view?.findViewById<ImageView>(R.id.imageView2)
//            //Pic_image.setImageURI(null)
//            Pic_image.setImageURI(it)
//
//            GlideApp.with(requireActivity())
//            .load(it)
//            .into(Pic_image)
//
//        }.addOnFailureListener {
//            // Handle any errors
//        }
        //val storageImgReference=Firebase.storage.reference
        //val imageView_pic=list.findViewById<ImageView>(R.id.image_list)
        //val imageRef = storageImgRef.child("images/*").child(imgPathRemote).getDownloadURL()
        //Glide.with(requireContext()).load(storageImgReference).into(imageView_pic)

        EventChangeListener()

    }

    private fun EventChangeListener() {

        val Pic_image=view?.findViewById<EditText>(R.id.Et_Et)

        db=FirebaseFirestore.getInstance()
        db.collection("/users")
            .addSnapshotListener(object : EventListener,
                com.google.firebase.firestore.EventListener<QuerySnapshot> {
                override fun onEvent(
                    value: QuerySnapshot?,
                    error: FirebaseFirestoreException?
                ) {

                    if (error !=null){

                        Log.e("エラー",error.message.toString())
                        return
                    }


//                    //Log.d(TAG, "DocumentSnapshot data: ${document.data}")
//                    GlideApp.with(requireActivity())
//                     .load("ImageDataUri")
//                     .into(Pic_image)
//                    Log.i("Mainlistview_Fragment","内容222：")

//                    // Reference to an image file in Cloud Storage
//                    val storageReference = Firebase.storage.reference
//                    val imageimage=storageReference.child("images/*.jpg")
//// ImageView in your Activity
//                    val imageImages:Int=imageimage.toInt()
//                    val imageView = view?.findViewById<ImageView>(R.id.image_list)
//
//// Download directly from StorageReference using Glide
//// (See MyAppGlideModule for Loader registration)


                    //ここへの記述を試みる
//                    Glide.with(requireContext())
//                        .load(DBDB.ImageDataUri)
//                        .into(imageView)


                    for (dc:DocumentChange in value?.documentChanges!!){

                        if(dc.type==DocumentChange.Type.ADDED){

                            dogList.add(dc.document.toObject(RecordDB::class.java))
                        }
                    }
                     CustomAdapter.notifyDataSetChanged()
                }

            })

    }


    private fun readFireStoreData(){
        val db=FirebaseFirestore.getInstance()
        val Et_Et = view?.findViewById<EditText>(R.id.Et_Et)
        val list = view?.findViewById<ListView>(R.id.list)

        /*db.collection("users")
            .get()
            .addOnCompleteListener {
                //val record_list:StringBuilder=StringBuilder()
                val record_list=ArrayList<Any>()

                if(it.isSuccessful){
                    for(document in it.result!!){

                        record_list.add(document.data.getValue("Time"))
                        //record_list.add("→")
                        record_list.add(document.data.getValue("Name"))
                        record_list.add(document.data.getValue("Caption"))
                        //record_list.add("\n\n")

                        Log.i("エラー",record_list.toString())
                    }

                    //val Time=RecordDB<String>(Time)

                    //val adapter=ArrayAdapter<Any>(requireContext(),R.layout.activity_list_item,record_list)

                    CustomAdapter= ListAdapter(requireContext(),record_list)
                    list?.adapter=CustomAdapter
                    //list?.adapter=adapter
                    //Et_Et?.setText(result)

                    //リストをクリック


                }
            }

         */


    }
}


