package com.example.myapplication.ui.login

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.Network.Resource
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentLoginBinding
import com.example.myapplication.ui.home.HomeActivity

class LoginFragment  : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private val isLogin by lazy { requireActivity().intent.getStringExtra("is_login") }
    private var ss: String? = null
    private val viewModel by lazy {
        ViewModelProvider(requireActivity()).get(LoginViewModel::class.java)
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListener()
        setupObserver()
    }

    private fun setupListener(){
        var title = if(isLogin=="1"){
            resources.getString(R.string.login)
        }else{
            resources.getString(R.string.register)
        }
        ss = isLogin
        binding.btnLogin.text = title
        binding.btnLogin.isEnabled = true
        binding.btnLogin.setOnClickListener{
            var usernameTxt = binding.username.text.toString()
            var passwordTxt = binding.password.text.toString()
            if(usernameTxt.isNullOrEmpty() || passwordTxt.isNullOrEmpty()){
                Toast.makeText(requireActivity(), "Lengkapi Username dan Password", Toast.LENGTH_SHORT).show()
            }else{
                if(ss=="1"){
                    viewModel.fetchLogin(
                        email= usernameTxt,
                        password= passwordTxt
                    )
                }else{
                    viewModel.fetchRegister(
                        email= usernameTxt,
                        password= passwordTxt
                    )
                }
            }
        }

    }
    private fun setupObserver(){

        viewModel.registerResponse.observe(viewLifecycleOwner, Observer {
            when (it){
                is Resource.Loading -> loadingLogin(true)
                is Resource.Success -> {
                    if(it.data!!.success){
                        binding.btnLogin.text = resources.getString(R.string.login)
                        ss = "1"
                        binding.username.setText("")
                        binding.password.setText("")
                    }
                    binding.txtAlert.text = it.data.message
                    binding.txtAlert.visibility = View.VISIBLE
                    loadingLogin(false)
                }
                is Resource.Error -> {
                    loadingLogin(false)
                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                }
            }
        })
        viewModel.loginResponse.observe(viewLifecycleOwner, Observer {
            when (it){
                is Resource.Loading -> loadingLogin(true)
                is Resource.Success -> {
                    loadingLogin(false)
                    var token = it.data!!.token
                    viewModel.savePreferenceToken(token)
                    val intent = Intent(requireContext(), HomeActivity::class.java)
                    startActivity(intent)
                    requireActivity().finish()
                }
                is Resource.Error -> {
                    loadingLogin(false)
                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                }
            }
        })


    }

    private fun loadingLogin(loading: Boolean){
        if(loading){
            binding.loading.visibility = View.VISIBLE
            binding.btnLogin.visibility = View.INVISIBLE
        }else{
            binding.loading.visibility = View.GONE
            binding.btnLogin.visibility = View.VISIBLE
        }
    }

}