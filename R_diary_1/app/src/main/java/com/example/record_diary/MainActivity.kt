package com.example.record_diary

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.service.voice.AlwaysOnHotwordDetector
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.webkit.CookieSyncManager.createInstance
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import javax.security.auth.callback.Callback

class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var actionBar: ActionBar


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        actionBar=supportActionBar!!
        actionBar.title="R.Diary"

        auth = FirebaseAuth.getInstance()

        //bottomnavigationviewの設定
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        val navController = supportFragmentManager.findFragmentById(R.id.fragment)
        bottomNavigationView.setupWithNavController(navController!!.findNavController())

    }

    //上部ツールバーにメニューを表示させる
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.sub_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        var fragment: Fragment? = null

        when (item.itemId) {
            //メニューのログアウトボタンを押したときにログアウト処理を行う
            R.id.action_logout -> {
                signOut()

                Toast.makeText(this, "ログアウト", Toast.LENGTH_SHORT).show()
                val intent=Intent(this,LoginActivity::class.java)
                startActivity(intent)
            }
            }
        return super.onOptionsItemSelected(item)

    }
    private fun signOut() {
        // [START auth_sign_out]
        Firebase.auth.signOut()
        // [END auth_sign_out]
    }

}


