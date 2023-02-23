package com.example.proyecto_android

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class PantallaLogin : ActividadMadre() {
    private val btnIniciarSesion by lazy{this.findViewById<Button>(R.id.btnLoginIniciarSesion)}
    private val btnRegistrarme by lazy{this.findViewById<Button>(R.id.btnLoginRegistrarme)}
    private val edtEmail by lazy{this.findViewById<EditText>(R.id.edtLoginGmail)}
    private val edtContrasena by lazy{this.findViewById<EditText>(R.id.edtLoginContrasena)}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pantalla_login)
        val bundle:Bundle?=intent.extras
            if (bundle != null) {
                edtEmail.setText(bundle.getString("Email"))
                edtContrasena.setText(bundle.getString("Contrasena"))
            }

    }

    override fun onStart() {
        super.onStart()
        btnRegistrarme.setOnClickListener(){
            val bundle:Bundle=Bundle()
            bundle.putString("Email",edtEmail.text.toString())
            bundle.putString("Contrasena",edtContrasena.text.toString())
            val intent:Intent= Intent(this,PantallaRegistro::class.java)
            intent.putExtras(bundle)
            this.startActivity(intent)

        }
        btnIniciarSesion.setOnClickListener(){
            Toast.makeText(this,R.string.no_implementado,Toast.LENGTH_SHORT).show()
        }


    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("Email",edtEmail.text.toString())
        outState.putString("Contrasena",edtContrasena.text.toString())
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}