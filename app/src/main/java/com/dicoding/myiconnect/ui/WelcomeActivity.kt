package com.dicoding.myiconnect.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.myiconnect.R
import com.dicoding.myiconnect.ui.home.MainActivity

class WelcomeActivity : AppCompatActivity() {

    private val SPLASH_TIME_OUT: Long = 2000
    private val MAX_RETRY: Int = 3
    private var retryCount: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

        navigateToMainActivityDelayed()
    }

    private fun navigateToMainActivityDelayed() {
        Handler().postDelayed({
            startMainActivity()
        }, SPLASH_TIME_OUT)
    }

    private fun startMainActivity() {
        try {
            val homeIntent = Intent(this@WelcomeActivity, MainActivity::class.java)
            startActivity(homeIntent)
            finish()
        } catch (e: Exception) {
            e.printStackTrace()
            handleNavigationError()
        }
    }

    private fun handleNavigationError() {
        if (retryCount < MAX_RETRY) {
            retryCount++
            startMainActivity()
        } else {
        }
    }
}
