package com.example.proyecto_android

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import clases.Usuario
import clases.UsuarioLogado

class PantallaPrincipal : ActividadMadre() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pantalla_principal)
        recogerUsuario(intent)
    }
}