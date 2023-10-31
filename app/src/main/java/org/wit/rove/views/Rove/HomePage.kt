package org.wit.rove.views.Rove

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import org.wit.rove.R
import org.wit.rove.main.MainApp
import org.wit.rove.views.roveList.RoveListView

class HomePage: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        var setEmail = findViewById(R.id.editEmail) as EditText
        var setPassword = findViewById(R.id.editPassword) as EditText

        var buttonLogin = findViewById(R.id.buttonLogin) as Button

        findViewById<ImageView>(R.id.roveLogo).apply {
            setImageResource(R.drawable.rove)
        }
        buttonLogin.setOnClickListener {
            val email = setEmail.text;
            val setPassword = setPassword.text;
            Toast.makeText(this@HomePage, email, Toast.LENGTH_LONG).show()
            val intent = Intent(this, RoveListView::class.java)
            startActivity(intent)
        }
    }
}