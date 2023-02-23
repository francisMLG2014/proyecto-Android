package com.example.proyecto_android

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import clases.UsuarioLogado
import java.time.LocalDate

class PantallaRegistro : ActividadMadre() {
    private val btnRegistrarme by lazy{this.findViewById<Button>(R.id.btnRegistroRegistrarme)}
    private val btnIniciarSesion by lazy{this.findViewById<Button>(R.id.btnRegistroInicioSesion)}
    private val edtEmail by lazy{this.findViewById<EditText>(R.id.edtRegistroGmail)}
    private val edtContrasena by lazy{this.findViewById<EditText>(R.id.edtRegistroContrasena)}
    private val edtNombreUsuario by lazy{this.findViewById<EditText>(R.id.edtRegistroNombreUsuario)}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pantalla_registro)
        recogerUsuario(intent)
        var bundle:Bundle?=intent.extras
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
            val bundle:Bundle=Bundle()
            bundle.putString("Email",edtEmail.text.toString())
            bundle.putString("Contrasena",edtContrasena.text.toString())
            this.usuario= UsuarioLogado("Franchute","fran@gmail.com",null,10,200,LocalDate.now(), LocalDate.now(),null)
            this.cambiarPantalla(PantallaLogin::class.java,bundle)
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