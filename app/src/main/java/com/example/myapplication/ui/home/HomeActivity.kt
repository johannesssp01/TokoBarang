package com.example.myapplication.ui.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.Database.preferences.CekTokoPreference
import com.example.myapplication.Database.preferences.tokenOrigin
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityMainBinding
import com.example.myapplication.ui.WelcomeActivity
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class HomeActivity : AppCompatActivity(), KodeinAware {

    override val kodein by kodein()
    private val viewModelFactory: HomeViewModelFactory by instance()
    private lateinit var viewModel: HomeViewModel
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private lateinit var sessionManager: CekTokoPreference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sessionManager = CekTokoPreference(this)
        setContentView(binding.root)
        setupToolbar()
        setupViewModel()
    }

    private fun setupToolbar(){
        supportActionBar!!.title = resources.getString(R.string.home)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.nav_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            R.id.nav_logout -> logOut()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun logOut(){
        sessionManager.put(tokenOrigin, "" )
        val intent =  Intent(this, WelcomeActivity::class.java)
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        finish()
        startActivity(intent)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
    private fun setupViewModel(){
        viewModel = ViewModelProvider(this, viewModelFactory).get(HomeViewModel::class.java)
    }
}