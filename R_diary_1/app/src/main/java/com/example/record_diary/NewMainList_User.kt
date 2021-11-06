package com.example.record_diary

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.record_diary.databinding.FragmentNewMainListUserBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.*
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.fragment_new_main_list_user.*
import java.lang.Exception
import java.util.ArrayList



class NewMainList_User : Fragment() {

    private lateinit var db: FirebaseFirestore
    private lateinit var storage: FirebaseStorage
    private lateinit var recyAdapter_User: RecyAdapter_user
    private lateinit var userList: ArrayList<RecordDB_Recy>
    private lateinit var auth: FirebaseAuth
    //filterlist
    private lateinit var categoryArrayList: ArrayList<RecordDB_Recy>
    private var _binding: FragmentNewMainListUserBinding? = null
    private val binding get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //   Inflate the layout for this fragment
        _binding = FragmentNewMainListUserBinding.inflate(inflater, container, false)
        return binding.root
        return inflater.inflate(R.layout.fragment_new_main_list_user, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val Recyclerview_list = view.findViewById<RecyclerView>(R.id.Recycler_List2)
        Recyclerview_list.layoutManager=LinearLayoutManager(requireContext())
        Recyclerview_list.setHasFixedSize(true)

        setDiarys()

        //search機能
        search_view.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                try{
                    recyAdapter_User.filter.filter(s)
                }
                catch(e:Exception){
                }

            }

            override fun afterTextChanged(s: Editable?) {

            }

        })

    }

    private fun setDiarys() {

        val Recyclerview_list2 = view?.findViewById<RecyclerView>(R.id.Recycler_List2)
        categoryArrayList= ArrayList()

        auth = FirebaseAuth.getInstance()
        val uid = auth.uid

        val ref = FirebaseFirestore.getInstance().collection("users").document("${uid}")
            .collection("record")
        ref.addSnapshotListener(object : java.util.EventListener,
            com.google.firebase.firestore.EventListener<QuerySnapshot> {


            override fun onEvent(
                value: QuerySnapshot?,
                error: FirebaseFirestoreException?
            ) {

                if (error != null) {
                    Log.e("エラー", error.message.toString())
                    return
                }


                for (dc: DocumentChange in value?.documentChanges!!) {
                    Log.e("成功", "アップロード処理成功")

                    //categoryArrayList.clear()


                    if (dc.type == DocumentChange.Type.ADDED) {

                        categoryArrayList.add(dc.document.toObject(RecordDB_Recy::class.java))
                    }
                    try {
                        //ここでIllegalStateExceptionが発生していたため、try catchを追加
                        recyAdapter_User = RecyAdapter_user(requireContext(), categoryArrayList)
                        Recyclerview_list2?.adapter = recyAdapter_User

                    }
                    catch(e:IllegalStateException){

                    }

                }

            }
        })

        //データ削除の処理はAdapterに記述

    }

}