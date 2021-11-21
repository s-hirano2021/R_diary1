package com.example.record_diary

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.fragment.app.FragmentManager
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.new_profile_activity.*



class ProfileActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var actionBar:ActionBar
    private var email=""
    private var password=""
    private var name=""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.new_profile_activity)

        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance()

        val Bt_sign_prof=findViewById<Button>(R.id.prof_button_create)

        Bt_sign_prof.setOnClickListener {

            validateData()
        }
    }

    private fun validateData() {

        val Et_mail_prof=findViewById<EditText>(R.id.prof_email_edit)
        val Et_pass_prof=findViewById<EditText>(R.id.prof_pass_edit)
        email=Et_mail_prof.text.toString().trim()
        password=Et_pass_prof.text.toString().trim()

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            Et_mail_prof.error="メールアドレスを正しく入力してください。"
        }else if (TextUtils.isEmpty(password)){
            Et_pass_prof.error="パスワードを入力してください。"
        }else if (password.length<6){
            Et_pass_prof.error="パスワードは６文字以上です。"
        }else{
            signinprofile()
        }

    }

    private fun signinprofile(){
        val Et_mail_prof=findViewById<EditText>(R.id.prof_email_edit)
        val Et_pass_prof=findViewById<EditText>(R.id.prof_pass_edit)
        val prof_name_edit=findViewById<EditText>(R.id.prof_name_edit)
        val Bt_sign_prof=findViewById<Button>(R.id.prof_button_create)

        val email=Et_mail_prof.text.toString()
        val password=Et_pass_prof.text.toString()
        //空値でクリックした時の処理
        if(email.isEmpty() || password.isEmpty()){
            Toast.makeText(this,"メールアドレスとパスワードを入力して新規登録してください。",Toast.LENGTH_SHORT).show()
            return

        }
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) {
                if (it.isSuccessful) {
                    uptoUserInfo()

                    sendEmailVerification()
                    Log.d("profile","新規登録　UID${it.result?.user?.uid}")
                    val intent=Intent(this,MainActivity::class.java)
                    startActivity(intent)
                    //登録成功
                    Log.d(TAG, "createUserWithEmail:success")
                    val user = auth.currentUser
                    updateUI(user)
                    //UIDを表示させる。
                    Log.d("ProfileActivity","新規登録UID:${it.result?.user?.uid}")
                } else {
                    // 登録失敗
                    Log.w(TAG, "createUserWithEmail:failure", it.exception)
                    Toast.makeText(
                        baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT
                    ).show()
                    updateUI(null)
                }

            }
    }

    private fun uptoUserInfo() {
        val timestamp=System.currentTimeMillis()
        val name =prof_name_edit?.text.toString()
        val uid = auth.uid

        val hashMap:HashMap<String,Any?> = HashMap()
        hashMap["uid"]=uid
        hashMap["email"]=email
        hashMap["name"]=name
        hashMap["profileImage"]=""
        hashMap["timestamp"]=timestamp

        val ref = FirebaseFirestore.getInstance().collection("admin")
            .add(hashMap)
            .addOnSuccessListener {
                val intent=Intent(this,MainActivity::class.java)
                startActivity(intent)

            }
            .addOnFailureListener { e->

            }
        
    }

    // ユーザがログイン状態かの確認
    public override fun onStart() {
        super.onStart()
        // ユーザーの確認
        val currentUser = auth.currentUser
        if(currentUser != null){
            reload()
        }
    }


    private fun getUserProfile() {
        // [START get_user_profile]
        val user = Firebase.auth.currentUser
        user?.let {
            // Name, email address, and profile photo Url
            val name = user.displayName
            val email = user.email
            val photoUrl = user.photoUrl

            // Check if user's email is verified
            val emailVerified = user.isEmailVerified

            // 一意のユーザーID

            val uid = user.uid
        }
    }


    private fun sendEmailVerification() {
        // 認証メール送信処理
        val user = auth.currentUser!!
        user.sendEmailVerification()
            .addOnCompleteListener(this) { task ->
                // Email Verification sent
            }
        // [END send_email_verification]
    }

    private fun updateUI(user: FirebaseUser?) {

    }
    private fun reload() {

    }
    companion object {
        private const val TAG = "EmailPassword"
    }

}