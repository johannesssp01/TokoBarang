package com.example.myapplication.ui.proccess

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.Database.preferences.CekTokoPreference
import com.example.myapplication.Database.preferences.changeOrigin
import com.example.myapplication.Network.Resource
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityAddEditBinding
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance


class AddEditActivity : AppCompatActivity(), KodeinAware {

    override val kodein by kodein()

    private val binding by lazy { ActivityAddEditBinding.inflate(layoutInflater) }

    private val viewModelFactory: AddEditViewModelFactory by instance()
    private lateinit var viewModel: AddEditViewModel
    private lateinit var sessionManager: CekTokoPreference

    private val isEdit by lazy { intent.getStringExtra("is_edit") }
    private val position by lazy { intent.getStringExtra("position") }
    private val kodeBarang by lazy { intent.getStringExtra("kode_barang") }
    private val namaBarang by lazy { intent.getStringExtra("nama_barang") }
    private val jumlahBarang by lazy { intent.getStringExtra("jumlah_barang") }
    private val hargaBarang by lazy { intent.getStringExtra("harga_barang") }
    private val satuanBarang by lazy { intent.getStringExtra("satuan_barang") }
    private val statusBarang by lazy { intent.getStringExtra("status_barang") }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sessionManager = CekTokoPreference(this)
        setContentView(binding.root)
        setupToolbar()
        setupListener()
        setupViewModel()
        setupObserver()
    }

    private fun setupToolbar(){
        var title = if(isEdit=="1"){
            resources.getString(R.string.edit_barang)
        }else{
            resources.getString(R.string.add_barang)
        }
        supportActionBar!!.title = title
    }
    private fun setupListener(){
        var txtKodeBarang = binding.txtKodeBarang
        var txtNamaBarang = binding.txtNamaBarang
        var txtJumlahBarang = binding.txtJumlahBarang
        var txtHargaBarang = binding.txtHargaBarang
        var txtSatuanBarang = binding.txtSatuanBarang
        var txtStatusBarang = binding.txtStatusBarang
        if(isEdit=="1"){
            txtKodeBarang.visibility = View.GONE
            txtKodeBarang.setText(kodeBarang)
            txtNamaBarang.setText(namaBarang)
            txtJumlahBarang.setText(jumlahBarang)
            txtHargaBarang.setText(hargaBarang)
            txtSatuanBarang.setText(satuanBarang)
            txtStatusBarang.setText(statusBarang)
        }else{
            txtKodeBarang.visibility = View.VISIBLE
        }
        binding.btnSave.setOnClickListener(View.OnClickListener {

            if(isEdit=="1"){
                viewModel.fetchEdit(
                    txtKodeBarang.text.toString(),
                    txtNamaBarang.text.toString(),
                    txtJumlahBarang.text.toString(),
                    txtHargaBarang.text.toString(),
                    txtSatuanBarang.text.toString(),
                    txtStatusBarang.text.toString(),
                )
            }else{
                viewModel.fetchAdd(
                    txtKodeBarang.text.toString(),
                    txtNamaBarang.text.toString(),
                    txtJumlahBarang.text.toString(),
                    txtHargaBarang.text.toString(),
                    txtSatuanBarang.text.toString(),
                    txtStatusBarang.text.toString(),
                )
            }
        })
    }

    private fun setupObserver(){
        viewModel.addResponse.observe(this, Observer {
            when (it){
                is Resource.Loading -> {

                }
                is Resource.Success -> {
                    sessionManager.put(changeOrigin,"1")
                    finish()
                }
                is Resource.Error -> {

                }
            }
        })
    }
    private fun setupViewModel(){
        viewModel = ViewModelProvider(this, viewModelFactory).get(AddEditViewModel::class.java)
    }
}
