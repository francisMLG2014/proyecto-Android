package com.example.proyecto_android

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class PantallaRegistro : ActividadMadre() {
    private val btnRegistrarme by lazy{this.findViewById<Button>(R.id.btnRegistroRegistrarme)}
    private val btnIniciarSesion by lazy{this.findViewById<Button>(R.id.btnRegistroInicioSesion)}
    private val edtEmail by lazy{this.findViewById<EditText>(R.id.edtRegistroGmail)}
    private val edtContrasena by lazy{this.findViewById<EditText>(R.id.edtRegistroContrasena)}
    private val edtNombreUsuario by lazy{this.findViewById<EditText>(R.id.edtRegistroNombreUsuario)}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pantalla_registro)
        val bundle:Bundle?=intent.extras
        if (bundle != null) {
            edtEmail.setText(bundle.getString("Email"))
            edtContrasena.setText(bundle.getString("Contrasena"))
        }
    }

    override fun onStart() {
        super.onStart()
        btnRegistrarme.setOnClickListener(){
            Toast.makeText(this,R.string.no_implementado, Toast.LENGTH_SHORT).show()
        }
        btnIniciarSesion.setOnClickListener(){
            var intent:Intent= Intent(this,PantallaLogin::class.java)
            var bundle:Bundle=Bundle()
            bundle.putString("Email",edtEmail.text.toString())
            bundle.putString("Contrasena",edtContrasena.text.toString())
            intent.putExtras(bundle)
            this.startActivity(intent)
        }


    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}