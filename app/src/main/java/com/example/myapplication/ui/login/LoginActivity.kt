package com.example.myapplication.ui.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityLoginBinding
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class LoginActivity : AppCompatActivity(), KodeinAware {

    override val kodein by kodein()

    private val isLogin by lazy { this.intent.getStringExtra("is_login") }
    private val binding by lazy { ActivityLoginBinding.inflate(layoutInflater) }


    private val viewModelFactory: LoginViewModelFactory by instance()
    private lateinit var viewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupToolbar()
        setupViewModel()
    }

    private fun setupViewModel(){
        viewModel = ViewModelProvider(this, viewModelFactory).get(LoginViewModel::class.java)
    }

    override fun onBackPressed() {
        /*if(intent.getBooleanExtra("is_tracking", false)) finish()
        else */
            super.onBackPressed()

    }

    private fun setupToolbar(){
        var title = if(isLogin=="1"){
            resources.getString(R.string.login)
        }else{
            resources.getString(R.string.register)
        }
        supportActionBar!!.title = title
    }





}