package com.ehernndez.poketest

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class OnBoardingActivity : AppCompatActivity() {
    lateinit var btnStart: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding)
        Log.e("--->", "method onCreate was called")

        btnStart = findViewById(R.id.btn_start)

        btnStart.setOnClickListener {
           val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
            finish()
        }

        val email = "ehernndez@gmail.com"
        Log.e("isValid email -->", email.isValidEmail().toString())
    }

    override fun onStart() {
        super.onStart()

        Log.e("-->", "method onStart was called ")
    }

    override fun onStop() {
        super.onStop()

        Log.e("--->", "method onStop was called")
    }

    override fun onDestroy() {
        super.onDestroy()

        Log.e("--->", "method onDestroy was called")
    }

    override fun onRestart() {
        super.onRestart()

        Log.e("--->", "method onRestart was called")
    }

    override fun onResume() {
        super.onResume()

        Log.e("--->", "method onResume was called")
    }
}

// extension that verify if an email is valid
fun String.isValidEmail() = isNotEmpty() && android.util.Patterns.EMAIL_ADDRESS.matcher(this).matches()