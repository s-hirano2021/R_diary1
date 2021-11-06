package com.example.record_diary

import android.app.AlertDialog
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.util.ArrayList

class RecyAdapter_user :RecyclerView.Adapter<RecyAdapter_user.MyViewHolder>,Filterable{

    lateinit var db: FirebaseFirestore
    lateinit var DBrecy:RecordDB_Recy
    private lateinit var auth: FirebaseAuth

    //filter
    private val context:Context
    public var categoryArrayList:ArrayList<RecordDB_Recy>
    private lateinit var filterList:ArrayList<RecordDB_Recy>

    private var filter : FilterCategory?=null

    constructor(context: Context, categoryArrayList: ArrayList<RecordDB_Recy>) : super() {
        this.context = context
        this.categoryArrayList = categoryArrayList
        this.filterList=categoryArrayList
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyAdapter_user.MyViewHolder {

//        lateinit var db: FirebaseFirestore
//        lateinit var DBrecy:RecordDB_Recy

        val itemView=LayoutInflater.from(parent.context).inflate(R.layout.new_listitem_recycler_user,parent,false)
        return MyViewHolder(itemView)

    }

    override fun onBindViewHolder(holder: RecyAdapter_user.MyViewHolder, position: Int) {

        db= FirebaseFirestore.getInstance()

        //val user:RecordDB_Recy=userList[position]
        //val user:RecordDB_Recy=filterList[position]
        val user:RecordDB_Recy=categoryArrayList[position]
        holder.Recy_text_TIme.text=user.Time
        holder.Recy_text_Name.text=user.Name
        holder.Recy_text_Caption.text=user.Caption

        //画像をImageViewに表示させる
        Glide.with(holder.itemView)
                .load(user.ImageDataUrl)
                .into(holder.Recy_image)

    }

    private fun deleteRecord(user: RecordDB_Recy, holder: MyViewHolder,position:Int) {

        val user:RecordDB_Recy=categoryArrayList[position]
        val id = user.Time

        auth = FirebaseAuth.getInstance()
        val uid = auth.uid
        db.collection("users").document("${uid}").collection("record").document("${uid}")
            .delete()
            .addOnSuccessListener {
                Toast.makeText(context,"削除",Toast.LENGTH_SHORT).show()

            }
            .addOnFailureListener { e ->
                Toast.makeText(context,"削除できませんでした。",Toast.LENGTH_SHORT).show()

            }

    }

    override fun getItemCount(): Int {
        //return userList.size
        return categoryArrayList.size
    }

    inner class MyViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){

        val Recy_text_TIme:TextView=itemView.findViewById(R.id.Recy_text_Time)
        val Recy_text_Name:TextView=itemView.findViewById(R.id.Recy_text_Name)
        val Recy_text_Caption:TextView=itemView.findViewById(R.id.Recy_text_Caption)
        val Recy_image: ImageView =itemView.findViewById(R.id.Recy_image)
    }

    override fun getFilter(): Filter {
        if (filter == null){
            filter = FilterCategory(filterList,this)
        }
        return filter as FilterCategory
    }
}