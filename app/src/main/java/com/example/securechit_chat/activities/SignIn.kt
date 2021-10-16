package com.example.securechit_chat.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import com.example.securechit_chat.R
import com.example.securechit_chat.databinding.ActivitySignInBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth

class SignIn : AppCompatActivity() {

    // ViewBinding class
    private lateinit var binding : ActivitySignInBinding
    private val auth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // switching  to signUp
        binding.createNewAccount.setOnClickListener {
            val intent = Intent(this , SignUp::class.java)
            startActivity(intent)
            finish()
        }

        // signin button
        binding.buttonSignIn.setOnClickListener {
            val email = binding.inputEmail.text.toString()
            val password = binding.inputPassword.text.toString()

            if(TextUtils.isEmpty(email) || TextUtils.isEmpty(password)){
                showSnackBar("Fields cannot be empty")
                return@setOnClickListener
            }

            auth.signInWithEmailAndPassword(email , password)
                .addOnCompleteListener {
                    if(it.isSuccessful){

                        if(auth.currentUser?.isEmailVerified == true){
                            val intent = Intent(this, MessagesList::class.java)
                            startActivity(intent)
                            finish()
                        }else{
                            showSnackBar("verify your email to login")
                        }


                    }else{
                        showSnackBar(it.exception?.message ?: "Some problem occurred while signing in")
                    }
                }

        }

    }

    // snackbar function
    private fun showSnackBar(message: String) {
        val snackBar = Snackbar.make(binding.signInLayout, message, Snackbar.LENGTH_INDEFINITE)
        snackBar.setAction("okay") {}
        snackBar.show()
    }
}