package com.br.iluminar.presentation

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.br.iluminar.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class LoginActivity : AppCompatActivity() {

    private val binding by lazy { ActivityLoginBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(binding.root)
        setupListener()
    }

    override fun onStart() {
        super.onStart()

        val currentUser: FirebaseUser? = FirebaseAuth.getInstance().currentUser

        if (currentUser != null){
            goToHubActivity()
        }
    }

    private fun setupListener(){
        binding.loginBt.setOnClickListener {
            val email:String = binding.emailEt.text.toString()
            val password:String = binding.passwordEt.text.toString()

            if (email.isEmpty() || password.isEmpty()){
                Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show()
            } else {
                authUser()
            }
        }

        binding.goToRegister.setOnClickListener{
            goToRegisterActivity()
        }

    }

    private fun goToHubActivity(){
        val intent = Intent(this, HubActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
    }

    private fun goToRegisterActivity(){
        val intent = Intent(this, RegisterFormActivity::class.java)
        startActivity(intent)
    }


    private fun authUser(){
        val email:String = binding.emailEt.text.toString()
        val password:String = binding.passwordEt.text.toString()

        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
            if (task.isSuccessful){
                binding.loginProgressBar.visibility = View.VISIBLE
                Handler(Looper.getMainLooper()).postDelayed(
                    {
                       goToHubActivity()
                    }, 3000
                )
            } else {
                try {
                    throw task.exception!!
                } catch (e: Exception) {
                    Toast.makeText(this, "Usuário ou senha inválidos", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }


}