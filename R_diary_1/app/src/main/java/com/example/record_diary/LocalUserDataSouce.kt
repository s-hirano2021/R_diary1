package com.example.record_diary

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class LocalUserDataSouce {

    private val userMap:Map<String,User>

    init{
        val builder=UserBuilder()

        userMap= mutableMapOf()
    }
    fun getUser(userId:String):LiveData<User>{
        val user =userMap[userId]?:UserBuilder().build()
        return MutableLiveData<User>().apply{
            postValue(user as User?)
        }
    }
}
