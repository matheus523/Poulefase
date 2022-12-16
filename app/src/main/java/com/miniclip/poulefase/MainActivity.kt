package com.miniclip.poulefase

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.miniclip.poulefase.databinding.ActivityMainBinding
import com.miniclip.poulefase.ui.welcome.WelcomeActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        goToWelcomePage()
    }

    //OnBack removed
    override fun onBackPressed() {}

    private fun goToWelcomePage(){
        val intent = Intent(this, WelcomeActivity::class.java)
        startActivity(intent)
    }
}