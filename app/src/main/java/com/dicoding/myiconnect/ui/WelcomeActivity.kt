package com.dicoding.myiconnect.ui

import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.myiconnect.databinding.ActivityWelcomeBinding
import com.dicoding.myiconnect.ui.login.LoginActivity

class WelcomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWelcomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWelcomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val textViewAppName: TextView = binding.textViewAppName

        val scaleXAnimator = ObjectAnimator.ofFloat(textViewAppName, "scaleX", 1.2f, 1f)
        scaleXAnimator.duration = 1900
        scaleXAnimator.repeatCount = ObjectAnimator.INFINITE
        scaleXAnimator.repeatMode = ObjectAnimator.REVERSE

        val scaleYAnimator = ObjectAnimator.ofFloat(textViewAppName, "scaleY", 1.2f, 1f)
        scaleYAnimator.duration = 1900
        scaleYAnimator.repeatCount = ObjectAnimator.INFINITE
        scaleYAnimator.repeatMode = ObjectAnimator.REVERSE

        scaleXAnimator.start()
        scaleYAnimator.start()

        binding.btnGetStarted.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            Toast.makeText(this, "Selamat Datang", Toast.LENGTH_SHORT).show()
        }
    }
}
