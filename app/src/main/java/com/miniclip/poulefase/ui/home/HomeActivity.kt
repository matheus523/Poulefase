package com.miniclip.poulefase.ui.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.miniclip.poulefase.databinding.ActivityHomeBinding
import com.miniclip.poulefase.ui.pool.PoolViewAdapter
import com.miniclip.poulefase.utilities.GlobalFunctions

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var homeViewModel: HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        recyclerView = binding.poolsRecyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)

        homeViewModel = ViewModelProvider(this)[HomeViewModel::class.java]
        homeViewModel.homeState.observe(this@HomeActivity, Observer {
            val homeDataState = it ?: return@Observer
            recyclerView.adapter = PoolViewAdapter(homeDataState.pools, this, homeViewModel)
        })
    }

    override fun onBackPressed() { GlobalFunctions.closeApplication(this) }
}