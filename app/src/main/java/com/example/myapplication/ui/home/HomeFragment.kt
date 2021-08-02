package com.example.myapplication.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.Database.preferences.CekTokoPreference
import com.example.myapplication.Database.preferences.changeOrigin
import com.example.myapplication.Network.Resource
import com.example.myapplication.databinding.HomeFragmentBinding
import com.example.myapplication.ui.proccess.AddEditActivity


class HomeFragment : Fragment() {

    private val viewModel by lazy { ViewModelProvider(requireActivity()).get(HomeViewModel::class.java) }
    private lateinit var binding: HomeFragmentBinding
    private lateinit var itemAdapter: HomeAdapter
    private lateinit var sessionManager: CekTokoPreference

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        sessionManager = CekTokoPreference(requireActivity())
        binding = HomeFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        viewModel.getPreferences()
        if(!sessionManager.getString(changeOrigin).isNullOrEmpty()){
            sessionManager.put(changeOrigin,"")
            viewModel.fetchItem()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //setupView()
        setupListener()
        setupRecyclerView()
        setupObserver()

    }

    private fun setupView(){
        //viewModel.titleBar.postValue("Pilih Barang")
    }

    private fun setupListener(){
        //untuk search array
        /*binding.editSearch.doAfterTextChanged {
            itemAdapter.filter.filter(it.toString())
        }*/
        /*untuk search DB*/
        binding.editSearch.doAfterTextChanged {
            if(binding.editSearch.text.isNullOrEmpty()){
                viewModel.fetchItem()
            }else{
                //viewModel.fetchSearch(binding.editSearch.text.toString())
            }
        }
        binding.buttonSearch.setOnClickListener(View.OnClickListener {

            viewModel.fetchSearch(binding.editSearch.text.toString())
        })
        binding.refreshItem.setOnRefreshListener {
            viewModel.fetchItem()
        }
        binding.btnAdd.setOnClickListener(View.OnClickListener {
            val intent = Intent(requireActivity(), AddEditActivity::class.java)
            intent.putExtra("is_edit", "0")
            startActivity(intent)
        })
    }

    private fun setupRecyclerView(){
        itemAdapter = HomeAdapter(arrayListOf(),viewModel)
        binding.listItem.adapter = itemAdapter
    }
    private fun setupObserver(){
        viewModel.searchResponse.observe(viewLifecycleOwner, Observer {
            when (it){
                is Resource.Loading -> {
                    binding.refreshItem.isRefreshing = false
                }
                is Resource.Success -> {
                    if(it.data!!.id !== 0){
                        itemAdapter.setDataSearch(it.data!!)
                    }
                    binding.refreshItem.isRefreshing = false
                }
                is Resource.Error -> {
                    binding.refreshItem.isRefreshing = false
                }
            }
        })
        viewModel.itemsResponse.observe(viewLifecycleOwner, Observer {
            when (it){
                is Resource.Loading -> {
                    binding.refreshItem.isRefreshing = true
                }
                is Resource.Success -> {
                    binding.refreshItem.isRefreshing = false
                    itemAdapter.setData(it.data!!)
                }
                is Resource.Error -> {
                    binding.refreshItem.isRefreshing = false
                }
            }
        })
    }
}