package com.br.iluminar.presentation

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.br.iluminar.databinding.ActivityRegisterFormBinding
import com.br.iluminar.domain.model.User
import com.br.iluminar.domain.utils.Resource
import com.br.iluminar.presentation.viewmodels.RegisterFormViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterFormActivity : AppCompatActivity() {

    private val binding by lazy { ActivityRegisterFormBinding.inflate(layoutInflater) }
    private val viewModel: RegisterFormViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(binding.root)
        setupListener()
    }

    private fun setupListener() {
        binding.registerButton.setOnClickListener {
            val email = binding.registerEmailEt.text.toString()
            val password = binding.registerPasswordEt.text.toString()

            if (checkIfEditTextsAreEmpty()) {
                Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show()
            } else {
                registerUser(email, password)
            }
        }
    }

    private fun checkIfEditTextsAreEmpty(): Boolean {
        return binding.registerEmailEt.text.toString().isEmpty() ||
                binding.registerPasswordEt.text.toString().isEmpty() ||
                binding.registerNameEt.text.toString().isEmpty() ||
                binding.registerPeriodoEt.text.toString().isEmpty() ||
                binding.registerAnoLetivoEt.text.toString().isEmpty() ||
                binding.registerNomeResponsavelEt.text.toString().isEmpty() ||
                binding.registerCelularResponsavelEt.text.toString().isEmpty()
    }

    private fun registerUser(email: String, password: String) {

        viewModel.signUp(
            email, password, User(
                binding.registerNameEt.text.toString(),
                binding.registerPeriodoEt.text.toString(),
                Integer.valueOf(binding.registerAnoLetivoEt.text.toString()),
                binding.registerNomeResponsavelEt.text.toString(),
                binding.registerCelularResponsavelEt.text.toString(),
                ""
            )
        )

        viewModel.signUpFlow.observe(this) {
            when (it) {
                is Resource.Success -> goToLoginActivity()
                is Resource.Failure -> {
                    Toast.makeText(
                        this,
                        "${it.exception.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                    Log.e("erro cadastro", "${it.exception.message}", it.exception)
                }
                else -> {}
            }
        }
    }

    private fun goToLoginActivity() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }
}