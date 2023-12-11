package com.example.lealappstest.view.authentication

import android.content.Intent
import android.content.res.ColorStateList
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.example.lealappstest.R
import com.example.lealappstest.databinding.ActivityLoginBinding
import com.example.lealappstest.view.training.MainActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {
    lateinit var binding: ActivityLoginBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityLoginBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        openRegistration()
        loginAction()
    }

    private fun openRegistration() {
        binding.tvDontHaveAccount.setOnClickListener {
            Intent(this@LoginActivity, RegisterActivity::class.java).also {
                startActivity(it)
            }
        }
    }

    private fun loginAction() {
        binding.btnLogin.setOnClickListener {

            val context = this
            val colorState = ColorStateList(
                arrayOf(intArrayOf(android.R.attr.state_enabled)),
                intArrayOf(
                    ContextCompat.getColor(context, R.color.red)
                )
            )

            val colorStateValid = ColorStateList(
                arrayOf(intArrayOf(android.R.attr.state_enabled)),
                intArrayOf(
                    ContextCompat.getColor(context, R.color.grey)
                )
            )

            if (binding.tilUserEmail.editText?.text.toString().isNullOrEmpty()) {
                binding.tilUserEmail.editText?.error = getString(R.string.requiredField)
                binding.tilUserEmail.setBoxStrokeColorStateList(colorState)
                binding.tilUserEmail.hintTextColor =
                    ColorStateList.valueOf(ContextCompat.getColor(this, R.color.red))
            } else {
                binding.tilUserEmail.editText?.error = null
                binding.tilUserEmail.setBoxStrokeColorStateList(colorStateValid)
                binding.tilUserEmail.hintTextColor =
                    ColorStateList.valueOf(ContextCompat.getColor(this, R.color.grey))
            }

            if (binding.tilPassword.editText?.text.toString().isNullOrEmpty()) {
                binding.tilPassword.editText?.error = getString(R.string.requiredField)
                binding.tilPassword.setBoxStrokeColorStateList(colorState)
                binding.tilPassword.hintTextColor =
                    ColorStateList.valueOf(ContextCompat.getColor(this, R.color.red))
            } else {
                binding.tilPassword.editText?.error = null
                binding.tilPassword.setBoxStrokeColorStateList(colorStateValid)
                binding.tilPassword.hintTextColor =
                    ColorStateList.valueOf(ContextCompat.getColor(this, R.color.grey))
            }

            if (loginValidation()) {
                auth.signInWithEmailAndPassword(
                    binding.etUserEmail.text.toString(),
                    binding.etPassword.text.toString()
                )
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            Intent(this@LoginActivity, MainActivity::class.java).also {
                                startActivity(it)
                                finish()
                            }
                        } else {
                            loginError()
                        }
                    }
            }
        }
    }

    private fun loginError() {
        val builder = MaterialAlertDialogBuilder(this, R.style.MaterialAlertDialog_rounded)
        builder.setMessage(getString(R.string.invalidUserOrPassword))
        builder.setPositiveButton(getString(R.string.understand)) { dialog, which ->
        }
        builder.show()
    }

    private fun loginValidation(): Boolean {
        if (binding.tilUserEmail.editText?.text.toString().isNullOrEmpty()) {
            return false
        } else if (binding.tilPassword.editText?.text.toString().isNullOrEmpty()) {
            return false
        }
        return true
    }
}