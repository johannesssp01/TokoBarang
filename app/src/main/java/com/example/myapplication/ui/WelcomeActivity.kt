package com.example.myapplication.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.myapplication.Database.preferences.CekTokoPreference
import com.example.myapplication.Database.preferences.tokenOrigin
import com.example.myapplication.databinding.ActivityWelcomeBinding
import com.example.myapplication.ui.home.HomeActivity
import com.example.myapplication.ui.login.LoginActivity

class WelcomeActivity : AppCompatActivity() {

    private val binding by lazy { ActivityWelcomeBinding.inflate(layoutInflater) }
    private lateinit var sessionManager: CekTokoPreference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sessionManager = CekTokoPreference(this)
        checkLogin()
    }

    private fun checkLogin(){
        if(sessionManager.getString(tokenOrigin).isNullOrEmpty()){
            setContentView(binding.root)
            setupToolbar()
            setupListener()
        }else{
            val intent =  Intent(this, HomeActivity::class.java)
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            finish()
            startActivity(intent)
        }
    }

    private fun setupToolbar(){
        supportActionBar!!.hide()
    }
    private fun setupListener(){
        binding.buttonLogin.setOnClickListener(View.OnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            intent.putExtra("is_login", "1")
            startActivity(intent)
        })
        binding.buttonRegister.setOnClickListener(View.OnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            intent.putExtra("is_login", "2")
            startActivity(intent)
        })
    }

}