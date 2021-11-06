package com.example.record_diary

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.record_diary.databinding.FragmentNewMainListBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.*
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.Item
import kotlinx.android.synthetic.main.new_listitem_recycler.view.*
import java.util.ArrayList


class NewMainList : Fragment() {

    private lateinit var recyAdapter: RecyAdapter
    private lateinit var userList: ArrayList<RecordDB_Recy>
    private lateinit var auth: FirebaseAuth

    private var _binding: FragmentNewMainListBinding? = null
    private val binding get() = _binding!!

    //private lateinit var useRecyclerView: RecyclerView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //   Inflate the layout for this fragment
        _binding = FragmentNewMainListBinding.inflate(inflater, container, false)
        return binding.root

        return inflater.inflate(R.layout.fragment_new_main_list, container, false)
        return view


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val Recyclerview_list = view.findViewById<RecyclerView>(R.id.Recycler_List)
        Recyclerview_list.layoutManager=LinearLayoutManager(requireContext())
        Recyclerview_list.setHasFixedSize(true)

        userList= arrayListOf()

        recyAdapter=RecyAdapter(userList)

        Recyclerview_list.adapter=recyAdapter

        setDiarys()

    }

    private fun setDiarys() {
        auth = FirebaseAuth.getInstance()
        val uid = auth.uid
        val ref = FirebaseFirestore.getInstance().collection("users").document("${uid}").collection("record")
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

                    if (dc.type == DocumentChange.Type.ADDED) {

                        userList.add(dc.document.toObject(RecordDB_Recy::class.java))
                    }
                }
                recyAdapter.notifyDataSetChanged()
            }

        })


    }
}


class UseruserItem(val RDB:RecordDB_Recy): Item<GroupieViewHolder>() {

        override fun bind(viewHolder: GroupieViewHolder, position: Int) {

            viewHolder.itemView.Recy_text_Time.text = RDB.Time

        }


        override fun getLayout(): Int {
            return R.layout.new_listitem_recycler

        }

}
