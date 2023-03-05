package com.example.proyecto_android

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import clases.PreferenciasAplicacion
import java.util.*

class MainActivity : ActividadMadre() {
    val preferencias=PreferenciasAplicacion(this)
    val preferenciasCompartidas=getSharedPreferences(getString(R.string.variable_shared_preferences),
        Context.MODE_PRIVATE)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onStart() {
        super.onStart()
        preferencias.setIdioma(preferenciasCompartidas.getString(getString(R.string.shared_preferences_idioma),getString(R.string.shared_preferences_idioma_defval))!!)
        //preferencias.setModoOscuro()
        cambiarPantalla(PantallaLogin::class.java,intent.getExtras())
    }
}