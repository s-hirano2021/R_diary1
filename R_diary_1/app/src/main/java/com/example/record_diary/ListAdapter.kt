package com.example.record_diary

import android.content.Context
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.module.AppGlideModule
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage


class ListAdapter(context: Context, var dogList:ArrayList<RecordDB>) :ArrayAdapter<RecordDB>(context,0,dogList){

    private val layoutInflater=context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    //テスト
    lateinit var db: FirebaseFirestore

    lateinit var DBDB:RecordDB


    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val rec=dogList[position]
        var view=convertView

        if (null==view){
            view=layoutInflater.inflate(R.layout.list_item,parent,false)
        }


//        val image_list=view?.findViewById<ImageView>(R.id.image_list)
//        GlideApp.with(context)
//            .load(rec.ImageDataUrl)
//            .into(context.image_list)

        val name=view?.findViewById<TextView>(R.id.text_Name)
        name?.text=rec.Name

        val time=view?.findViewById<TextView>(R.id.text_Time)
        time?.text=rec.Time.toString()

        val caption=view?.findViewById<TextView>(R.id.text_Caption)
        caption?.text=rec.Caption

        val image_name=view?.findViewById<TextView>(R.id.textView3)
        image_name?.text=rec.ImageName


        return view!!
    }

    //LIstViewの行数
   override fun getCount(): Int {
        return dogList.size
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    //↓　return以下について不明　要調査
//    override fun getItem(position: Int): Any {
//        return position.size
//    }



//    private inner class ViewHolder{
//        var Name:TextView?=null
//        var Time:TextView?=null
//        internal val image:ImageView?=null
//    }



}