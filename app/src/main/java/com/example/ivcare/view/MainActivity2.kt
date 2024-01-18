package com.example.ivcare.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ivcare.R
import com.example.ivcare.databinding.ActivityMain2Binding
import com.example.ivcare.databinding.FragmentHomeBinding
import com.example.ivcare.display.MyRecyclerViewAdapter
import com.example.ivcare.display.UzerViewModel
import com.example.ivcare.uzerdatabase.Uzer
import com.example.ivcare.uzerdatabase.UzerDatabase
import com.example.ivcare.uzerdatabase.UzerRepository
import com.example.ivcare.display.UzerViewModelFactory

class MainActivity2 : AppCompatActivity() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var uzerViewModel: UzerViewModel
    private lateinit var adapter: MyRecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.fragment_home)
        val dao = UzerDatabase.getInstance(application).uzerDAO
        val repository = UzerRepository(dao)
        val factory = UzerViewModelFactory(repository)
        uzerViewModel = ViewModelProvider(this,factory)[UzerViewModel::class.java]
        binding.myViewModel = uzerViewModel
        binding.lifecycleOwner = this

        initRecyclerView()

        uzerViewModel.message.observe(this, Observer {
            it.getContentIfNotHandled()?.let {
                Toast.makeText(this, it, Toast.LENGTH_LONG).show()
            }
        })
    }
    private fun initRecyclerView(){
        binding.userRecyclerView.layoutManager = LinearLayoutManager(this)
        adapter = MyRecyclerViewAdapter({selectedItem:Uzer->listItemClicked(selectedItem)})
        binding.userRecyclerView.adapter = adapter
        displayUzersList()
    }
    private fun displayUzersList(){
        uzerViewModel.uzers.observe(this, Observer {
            Log.i("MYTAG",it.toString())
            //binding.subscriberRecyclerView.adapter = MyRecyclerViewAdapter(it,{selectedItem:Subscriber->listItemClicked(selectedItem)})
            adapter.setList(it)
            adapter.notifyDataSetChanged()
        })
    }
    private fun listItemClicked(uzer: Uzer){
        Toast.makeText(this,"selected name is ${uzer.name}", Toast.LENGTH_LONG).show()
        uzerViewModel.initUpdateAndDelete(uzer)
    }
}