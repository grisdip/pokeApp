package com.ehernndez.poketest.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.ehernndez.poketest.R
import com.ehernndez.poketest.data.persistantData.Data
import com.ehernndez.poketest.ui.home.HomeActivity
import com.ehernndez.poketest.utils.NetworkConnection
import com.ehernndez.poketest.utils.Utils
import com.ehernndez.poketest.utils.loader.LoaderUtils
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.logEvent
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {
    lateinit var containerPassword: TextInputLayout
    lateinit var edtxtPassword: TextInputEditText
    lateinit var txtEmailuser: TextView
    lateinit var btnLogin: Button
    lateinit var cardLoginWithPassword: MaterialCardView
    lateinit var cardLoginWithBiometric: MaterialCardView
    lateinit var btnLoginWithBiometric: MaterialButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        containerPassword = findViewById(R.id.container_edtxt_login)
        edtxtPassword = findViewById(R.id.edtxt_login)
        txtEmailuser = findViewById(R.id.txt_email_user)
        btnLogin = findViewById(R.id.btn_login)
        cardLoginWithPassword = findViewById(R.id.card_view_login)
        cardLoginWithBiometric = findViewById(R.id.card_view_login_with_biometric)
        btnLoginWithBiometric = findViewById(R.id.btn_login_with_biometric)

        if (Data.shared.useBiometric) {
            btnLogin.visibility = View.GONE
            cardLoginWithPassword.visibility = View.GONE

        } else {
            btnLogin.visibility = View.VISIBLE
            cardLoginWithBiometric.visibility = View.GONE

            txtEmailuser.text = Data.shared.email
        }

        edtxtPassword.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                if (edtxtPassword.text!!.length == 8) {
                    containerPassword.error = null
                    Utils().enableButton(
                        this@LoginActivity, btnLogin,
                        isEnable = true,
                        isVariant = true
                    )
                } else {
                    containerPassword.error = "La contraseña esde 8 dígitos"
                }
            }
        })

        btnLoginWithBiometric.setOnClickListener {
            Utils().createIntent(this@LoginActivity, HomeActivity())
            finish()
        }

        btnLogin.setOnClickListener {
            /*Data.shared.psw = edtxtPassword.text.toString()
            Utils().createIntent(this@LoginActivity, HomeActivity())
            finish()*/

            LoaderUtils.showLoader(this)

            FirebaseAuth.getInstance()
                .signInWithEmailAndPassword(Data.shared.email, edtxtPassword.text.toString())
                .addOnCompleteListener {
                    LoaderUtils.hideLoader()
                    if (it.isSuccessful) {
                        val bundle = Bundle()
                        bundle.putBoolean("user_login_by_password", true)
                        Data.firebaseAnalytics.logEvent(FirebaseAnalytics.Event.LOGIN, bundle)

                        Utils().createIntent(this@LoginActivity, HomeActivity())
                        finish()
                    } else {

                        val bundle = Bundle()
                        bundle.putBoolean("failed_user_login", true)
                        Data.firebaseAnalytics.logEvent(FirebaseAnalytics.Event.LOGIN, bundle)

                        Log.e("Login User ---->", it.exception.toString())
                        Toast.makeText(
                            this@LoginActivity,
                            "Error al iniciar sesión",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
        }
    }
}