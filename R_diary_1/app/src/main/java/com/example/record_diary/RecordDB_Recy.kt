package com.example.record_diary

import android.net.Uri

data class RecordDB_Recy(
    val Time:String?="",
    val ImageDataUrl:String?="",
    val Name:String?="",
    val Caption:String?="",
    val ImageRecy:Int?=null
)

//↑プライマリコンストラクタの省略形
//エラー発生のためコメントアウト、ID変更にて解消
//val ImagePhoto:Int=0,