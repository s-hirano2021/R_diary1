package com.example.record_diary

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser



class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var actionBar:ActionBar
    private var email=""
    private var password=""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.new_login_activity)

        actionBar=supportActionBar!!
        actionBar.title="ログイン"

        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance()
        checkUser()

        val Et_mail=findViewById<EditText>(R.id.login_email_edit)
        val Et_pass=findViewById<EditText>(R.id.login_pass_edit)
        val Bt_login=findViewById<Button>(R.id.login_button)

        Bt_login.setOnClickListener {

            validateData()

            //変数の定義、ログイン処理
            val email=Et_mail.text.toString()
            val password=Et_pass.text.toString()

            //メールアドレスとパスワードに入力がない時のエラー処理
            Log.d("LoginActivity","メールアドレス　:"+email)
            Log.d("LoginActivity","パスワード　:$password")

            //メールアドレスとパスワードが空値なのにログインボタンを押した時
            if(email.isEmpty() || password.isEmpty()){
                Toast.makeText(this,"メールアドレスとパスワードを入力してください。",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

        }
        //会員登録画面への変遷処理
        val tx_signup=findViewById<TextView>(R.id.login_text_create)

        tx_signup.setOnClickListener {
            var intent= Intent(this,ProfileActivity::class.java)
            startActivity(intent)
            val user = auth.currentUser
            updateUI(user)
        }
    }

    //メールアドレスとパスワードの入力確認
    private fun validateData() {
        val Et_mail=findViewById<EditText>(R.id.login_email_edit)
        val Et_pass=findViewById<EditText>(R.id.login_pass_edit)
        email=Et_mail.text.toString().trim()
        password=Et_pass.text.toString().trim()

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            Et_mail.error="メールアドレスが違います"
        }else if(TextUtils.isEmpty(password)){
            Et_pass.error="パスワードを入力してください。"
        }else{
            firebaseLogin()
        }

        }

    private fun firebaseLogin() {
        //Firebaseによるログイン処理
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val firebaseUser=auth.currentUser
                    val email=firebaseUser!!.email
                    var intent=Intent(this,MainActivity::class.java)
                    startActivity(intent)
                    finish()

                    // Sign in success, update UI with the signed-in user's information
                    Toast.makeText(baseContext, "ログインしました。", Toast.LENGTH_SHORT).show()

                } else {
                    // If sign in fails, display a message to the user.
                    //Log.w(TAG, "createUserWithEmail:failure", task.exception)
                    Toast.makeText(
                        baseContext, "ログイン失敗",
                        Toast.LENGTH_SHORT
                    ).show()
                    updateUI(null)
                }
            }
    }


    // ユーザの確認
    private fun checkUser() {
        // ユーザーがサインインしているか確認　サインインしていなければログイン画面へ変遷
        val currentUser = auth.currentUser
        if(currentUser != null){
            startActivity(Intent(this,MainActivity::class.java))
            finish()
        }
    }

    //メール送信による確認　　Firebaseルールに記述
    private fun sendEmailVerification() {
        // [START send_email_verification]
        val user = auth.currentUser!!
        user.sendEmailVerification()
            .addOnCompleteListener(this) { task ->
                // Email Verification sent
            }
        // [END send_email_verification]
    }

    private fun updateUI(user: FirebaseUser?) {

    }

    companion object {
        private const val TAG = "EmailPassword"
    }

}