package com.dicoding.myiconnect.ui.login

import android.animation.ObjectAnimator
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.AnimationUtils
import android.widget.TextView
import android.widget.Toast
import com.dicoding.myiconnect.R
import com.dicoding.myiconnect.databinding.ActivityLoginBinding
import com.dicoding.myiconnect.ui.home.MainActivity
import com.dicoding.myiconnect.ui.register.RegisterActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

class LoginActivity : AppCompatActivity() {

    lateinit var binding : ActivityLoginBinding
    lateinit var auth : FirebaseAuth
    lateinit var googleSignInClient: GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val textView1: TextView = binding.textview1
        val textView2: TextView = binding.textview2

        val scaleXAnimator = ObjectAnimator.ofFloat(textView1, "scaleX", 1.2f, 1f)
        val scaleXAnimator2 = ObjectAnimator.ofFloat(textView2, "scaleX", 1.2f, 1f)
        scaleXAnimator.duration = 2000
        scaleXAnimator.repeatCount = ObjectAnimator.INFINITE
        scaleXAnimator.repeatMode = ObjectAnimator.REVERSE

        scaleXAnimator2.duration = 2000
        scaleXAnimator2.repeatCount = ObjectAnimator.INFINITE
        scaleXAnimator2.repeatMode = ObjectAnimator.REVERSE

        val scaleYAnimator = ObjectAnimator.ofFloat(textView1, "scaleY", 1.2f, 1f)
        val scaleYAnimator2 = ObjectAnimator.ofFloat(textView2, "scaleY", 1.2f, 1f)
        scaleYAnimator.duration = 2000
        scaleYAnimator.repeatCount = ObjectAnimator.INFINITE
        scaleYAnimator.repeatMode = ObjectAnimator.REVERSE

        scaleYAnimator2.duration = 2000
        scaleYAnimator2.repeatCount = ObjectAnimator.INFINITE
        scaleYAnimator2.repeatMode = ObjectAnimator.REVERSE

        scaleXAnimator.start()
        scaleXAnimator2.start()
        scaleYAnimator.start()
        scaleYAnimator2.start()



        val currentUser = FirebaseAuth.getInstance().currentUser
        if (currentUser != null) {
            // Pengguna sudah masuk, lanjutkan ke MainActivity
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        auth = FirebaseAuth.getInstance()
        binding.tvToRegister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }


        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .build()


        googleSignInClient = GoogleSignIn.getClient(this, gso)

        binding.btnLoginGoogle.setOnClickListener {
            val signInIntent = googleSignInClient.signInIntent
            startActivityForResult(signInIntent, 1)
        }

        binding.btnLogin.setOnClickListener() {
            val email = binding.edtEmailLogin.text.toString().trim()
            val password = binding.edtPasswordLogin.text.toString().trim()
            // Email Validation
            if (email.isEmpty()) {
                binding.edtEmailLogin.error = "Email Tidak Boleh Kosong"
                binding.edtEmailLogin.requestFocus()
                return@setOnClickListener
            }
            // Email Focus
            if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                binding.edtEmailLogin.error = "Email Tidak Valid"
                binding.edtEmailLogin.requestFocus()
                return@setOnClickListener
            }

            // Password Validation
            if (password.isEmpty() || password.length < 8) {
                binding.edtPasswordLogin.error = "Password Tidak Boleh Kosong dan Lebih dari 8 Karakter"
                binding.edtPasswordLogin.requestFocus()
                return@setOnClickListener
            }

            LoginFirebase(email, password)
        }
    }

    private fun LoginFirebase(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) {
                if (it.isSuccessful) {
                    Toast.makeText(this, "Login Berhasil", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(this, "Login Gagal", Toast.LENGTH_SHORT).show()
                }
            }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 1) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            val exception = task.exception
            if (task.isSuccessful) {
                try {
                    val account = task.getResult(ApiException::class.java)
                    if (account != null) {
                        firebaseAuthWithGoogle(account)
                    }
                } catch (e: Exception) {
                    Toast.makeText(this, "Google sign in failed:(", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun firebaseAuthWithGoogle(account: GoogleSignInAccount) {
        val credential = GoogleAuthProvider.getCredential(account.idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) {
                if (it.isSuccessful) {
                    Toast.makeText(this, "Login Berhasil", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(this, "Login Gagal", Toast.LENGTH_SHORT).show()
                }
            }
    }
}
