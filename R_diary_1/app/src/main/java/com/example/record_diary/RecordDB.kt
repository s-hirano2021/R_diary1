package com.example.record_diary

import android.net.Uri

data class RecordDB(val uid:String?="",
                    val Time:String?="",
                    val Name:String?="",
                    val Caption:String?="",
                    val ImageName:String?="",
                    val ImageDataUrl:String?="",
                    val DiaryImage:Int?=null)
//エラー発生のためコメントアウト、ID変更にて解消
//val ImagePhoto:Int=0,