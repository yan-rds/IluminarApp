package com.br.iluminar.presentation

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.br.iluminar.databinding.ActivityLoginBinding
import com.br.iluminar.domain.utils.Resource
import com.br.iluminar.presentation.viewmodels.LoginViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    private val viewModel: LoginViewModel by viewModels()
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

        if (currentUser != null) {
            goToHubActivity()
        }
    }

    private fun requestLogin() {
        val email: String = binding.emailEt.text.toString()
        val password: String = binding.passwordEt.text.toString()

        viewModel.login(email, password)

        viewModel.loginFlow.observe(this) {
            when (it) {
                is Resource.Loading -> binding.loginProgressBar.visibility = View.VISIBLE
                is Resource.Failure -> {
                    Toast.makeText(
                        this,
                        "Usuário ou senha inválidos",
                        Toast.LENGTH_SHORT
                    ).show()
                    binding.loginProgressBar.visibility = View.GONE
                }
                is Resource.Success -> goToHubActivity()
                else -> {}
            }
        }
    }

    private fun setupListener() {

        binding.loginBt.setOnClickListener {
            val email: String = binding.emailEt.text.toString()
            val password: String = binding.passwordEt.text.toString()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show()
            } else {
                requestLogin()
            }

        }

        binding.goToRegister.setOnClickListener { goToRegisterActivity() }


    }

    private fun goToHubActivity() {
        val intent = Intent(this, HubActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
    }

    private fun goToRegisterActivity() {
        val intent = Intent(this, RegisterFormActivity::class.java)
        startActivity(intent)
    }
}