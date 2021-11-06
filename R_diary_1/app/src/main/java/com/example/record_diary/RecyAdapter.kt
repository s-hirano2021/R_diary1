package com.example.record_diary

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.withContext

class RecyAdapter(private val userList:ArrayList<RecordDB_Recy>) :RecyclerView.Adapter<RecyAdapter.MyViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyAdapter.MyViewHolder {

        lateinit var db: FirebaseFirestore
        lateinit var DBrecy:RecordDB_Recy

        val itemView=LayoutInflater.from(parent.context).inflate(R.layout.new_listitem_recycler,parent,false)
        return MyViewHolder(itemView)

    }

    override fun onBindViewHolder(holder: RecyAdapter.MyViewHolder, position: Int) {
        val user:RecordDB_Recy=userList[position]
        holder.Recy_text_TIme.text=user.Time
        holder.Recy_text_Name.text=user.Name
        holder.Recy_text_Caption.text=user.Caption

        //画像をImageViewに表示させる
        Glide.with(holder.itemView)
                .load(user.ImageDataUrl)
                .into(holder.Recy_image)

    }

    override fun getItemCount(): Int {
        return userList.size
    }

    public class MyViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){

        val Recy_text_TIme:TextView=itemView.findViewById(R.id.Recy_text_Time)
        val Recy_text_Name:TextView=itemView.findViewById(R.id.Recy_text_Name)
        val Recy_text_Caption:TextView=itemView.findViewById(R.id.Recy_text_Caption)
        val Recy_image: ImageView =itemView.findViewById(R.id.Recy_image)
    }
}