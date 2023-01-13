package com.br.iluminar.presentation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.br.iluminar.model.User
import com.br.iluminar.databinding.ActivityRegisterFormBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore

class RegisterFormActivity : AppCompatActivity() {

    private val binding by lazy { ActivityRegisterFormBinding.inflate(layoutInflater) }

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
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Cadastro realizado com sucesso", Toast.LENGTH_SHORT)
                        .show()
                    saveUserData()
                    goToLoginActivity()
                } else {
                    val erro: String = try {
                        throw task.exception!!
                    } catch (e: FirebaseAuthWeakPasswordException) {
                        "Senha fraca"
                    } catch (e: FirebaseAuthUserCollisionException) {
                        "Esse email já foi cadastrado"
                    } catch (e: FirebaseAuthInvalidCredentialsException) {
                        "Email inválido"
                    } catch (e: Exception) {
                        "Erro ao cadastrar usuãrio"
                    }

                    Toast.makeText(this, erro, Toast.LENGTH_SHORT).show()
                }

            }
    }

    private fun saveUserData() {
        val db: FirebaseFirestore = FirebaseFirestore.getInstance()

        val userId = FirebaseAuth.getInstance().currentUser?.uid

        val documentReference: DocumentReference? = userId?.let {
            db.collection("Usuarios").document(
                it
            )
        }
        documentReference?.set(
            User(
                binding.registerNameEt.text.toString(),
                binding.registerPeriodoEt.text.toString(),
                Integer.valueOf(binding.registerAnoLetivoEt.text.toString()),
                binding.registerNomeResponsavelEt.text.toString(),
                binding.registerCelularResponsavelEt.text.toString(),
                ""
            )
        )?.addOnSuccessListener {

        }?.addOnFailureListener { e ->
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show()
        }
    }

    private fun goToLoginActivity() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }


}