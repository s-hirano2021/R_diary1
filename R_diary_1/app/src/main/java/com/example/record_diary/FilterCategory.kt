package com.example.record_diary

import android.widget.Filter

class FilterCategory: Filter {

    private var filterList:ArrayList<RecordDB_Recy>
    //private lateinit var categoryArrayList: ArrayList<RecordDB_Recy>
    public lateinit var categoryArrayList: ArrayList<RecordDB_Recy>

    private var recyadapterUser:RecyAdapter_user


    constructor(filterList: ArrayList<RecordDB_Recy>, recyadapterUser: RecyAdapter_user) : super() {
        this.filterList = filterList
        this.recyadapterUser = recyadapterUser
    }

    override fun performFiltering(constraint: CharSequence?): FilterResults {
        var constraint = constraint
        val results =  FilterResults()

        //値はnullでも空でもない
        if (constraint !=null && constraint.isNotEmpty()){
            //検索値はnullでも空でもない

             // 大文字または小文字に変更
            constraint=constraint.toString().uppercase()
            val filterModels:ArrayList<RecordDB_Recy> = ArrayList()
            for (i in 0 until filterList.size){
                if (filterList[i].Name!!.uppercase().contains(constraint)){
                    filterModels.add(filterList[i])
                }
            }
            results.count = filterModels.size
            results.values=filterModels

        }else{
            //検索値がnullまたは空のどちらか
            results.count=filterList.size
            results.values=filterList
        }
        return  results
    }

    override fun publishResults(constraint: CharSequence?, results: FilterResults) {

        recyadapterUser.categoryArrayList = results.values as ArrayList<RecordDB_Recy>

        recyadapterUser.notifyDataSetChanged()
    }
}