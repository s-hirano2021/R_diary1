package com.example.record_diary

import android.app.Activity.RESULT_OK
import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.InputFilter
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage

import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.HashMap


class Record_Fragment : Fragment() {

    lateinit var storage:FirebaseStorage
    private lateinit var auth: FirebaseAuth
    lateinit var ImageUri:Uri

    private var caption=""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        storage=Firebase.storage
        auth= FirebaseAuth.getInstance()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_record_, container, false)

        val editTextDate = view.findViewById<EditText>(R.id.editTextDate)
        val firebaseimage = view.findViewById<ImageView>(R.id.imagepic)
        val takepic = view.findViewById<ImageButton>(R.id.img_bt_takepic)
        val BtnSave = view.findViewById<Button>(R.id.BtnSave)
        val Et_name_rec=view.findViewById<EditText>(R.id.Et_name_rec)
        val Et_caption=view.findViewById<EditText>(R.id.Et_caption)
        val img_name=view.findViewById<TextView>(R.id.img_name)

        val lengthnameFilter = InputFilter.LengthFilter(16)
        Et_name_rec.filters = arrayOf(lengthnameFilter)
        Et_name_rec.error="タイトルは15文字以内です。"

        //キャプション、説明の文章は５０文字以上入力できないようにする
        val lengthFilter = InputFilter.LengthFilter(51)
        Et_caption.filters = arrayOf(lengthFilter)
            Et_caption.error="キャプションは50文字以内です。"

        //現在　日時を取得して、TextViewに表示させる。
        val df = SimpleDateFormat("yyyy/MM/dd/ HH:mm")
        val date = Date()
        editTextDate.setText(df.format(date))



        //保存ボタンクリック　実装
        BtnSave.setOnClickListener {

            //画像を選択していない時のエラー処理
            if(firebaseimage.getDrawable()==null){
                Toast.makeText(requireContext(),"画像を選択してください。",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            uploadImage()
            //画像をfirebaseへアップロード

            Log.i("画像アップロード＋画面遷移","")


        }
        //ボタンクリックにより画像をimageviewに取得する
        takepic.setOnClickListener {
            selectPhoto()
        }

        return view

    }

    //画像のアップロード処理
    private fun uploadImage() {
        //プログレスダイアログを表示　現在は非推奨となっているため？
        val progressDialog= ProgressDialog(requireContext())
        progressDialog.setMessage("アップロード中です")
        progressDialog.setCancelable(false)
        progressDialog.show()

        val img_name=view?.findViewById<TextView>(R.id.img_name)
        val Et_name_rec=view?.findViewById<EditText>(R.id.Et_name_rec)
        val Et_caption=view?.findViewById<EditText>(R.id.Et_caption)
        val editTextDate = view?.findViewById<EditText>(R.id.editTextDate)

        val pic_name=img_name?.text.toString()
        val storageReference=FirebaseStorage.getInstance().getReference("images/$pic_name")

        val firebaseimage = view?.findViewById<ImageView>(R.id.imagepic)

        storageReference.putFile(ImageUri)
            .addOnSuccessListener{
                Log.d("Record_Fragment","画像のアップロード成功：${it.metadata?.path}")
                firebaseimage?.setImageURI(null)
                progressDialog.dismiss()
                Toast.makeText(requireContext(),"成功",Toast.LENGTH_SHORT).show()
                //
                storageReference.downloadUrl.addOnSuccessListener {
                    Log.d("Record_Fragment","ファイルロケーション：$it")

                    val Time =editTextDate?.text.toString()
                    val Name = Et_name_rec?.text.toString()
                    val Caption = Et_caption?.text.toString()
                    val ImageName = img_name?.text.toString()
//                    //FireStoreへの保存処理
                    saveFireStore(Time,Name,Caption,ImageName,it.toString())


                }
            }.addOnFailureListener{
                Toast.makeText(requireContext(),"失敗",Toast.LENGTH_SHORT).show()

            }
    }


    private fun saveFireStore(Time:String,Name:String,Caption:String,ImageName:String,ImageDataUrl:String){

        val db=FirebaseFirestore.getInstance()
        val timestamp=System.currentTimeMillis()
        val uid = auth.uid

        val user:MutableMap<String,Any?> = HashMap()
        user["Time"]=Time
        user["Name"]=Name
        user["Caption"]=Caption
        user["ImageName"]=ImageName
        user["ImageDataUrl"]=ImageDataUrl
        //uidの取得
        user["uid"]="${auth.uid}"


        //ユーザ管理するため、サブコレクションを作成。ユーザ管理はFirebaseFirestoreのルールに記述
        db.collection("users").document("${uid}").collection("record")
            .add(user)
            .addOnSuccessListener {

                //104にも記述あり　どちらがよいのか？
                findNavController().navigate(R.id.action_record_Fragment_to_newMainList_User)
                Toast.makeText(this.activity,"保存しました",Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                Toast.makeText(this.activity,"失敗",Toast.LENGTH_SHORT).show()
            }
    }


    //画像アプリを起動し、画像ファイルを取得
    private fun selectPhoto() {
        val intent = Intent()
            intent.type = "image/*"
        intent.action=Intent.ACTION_GET_CONTENT

        startActivityForResult(intent, 100)
    }


    //取得した画像をimage_pic(imageview)に貼り付け
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        val firebaseimage = view?.findViewById<ImageView>(R.id.imagepic)

        imageNameget()

        if (requestCode == 100 && resultCode == RESULT_OK) {

            ImageUri=data?.data!!

            firebaseimage?.setImageURI(ImageUri)
        }
    }

    private fun imageNameget() {
        //UUIDを取得してファイル名とする。
        val fileName=UUID.randomUUID().toString()
        val img_name=view?.findViewById<TextView>(R.id.img_name)
        img_name?.setText(fileName).toString()

    }
}