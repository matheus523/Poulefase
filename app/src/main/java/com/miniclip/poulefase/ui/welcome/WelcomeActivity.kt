package com.miniclip.poulefase.ui.welcome

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.miniclip.poulefase.databinding.ActivityWelcomeBinding
import com.miniclip.poulefase.ui.home.HomeActivity
import com.miniclip.poulefase.utilities.GlobalFunctions

class WelcomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWelcomeBinding
    private lateinit var startButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityWelcomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        startButton = binding.startButton

        startListeners()
    }

    override fun onBackPressed() { GlobalFunctions.closeApplication(this) }

    private fun startListeners(){
        startButton.setOnClickListener{
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }
    }
}