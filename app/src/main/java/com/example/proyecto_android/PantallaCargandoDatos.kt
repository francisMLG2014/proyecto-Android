package com.example.proyecto_android

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import org.w3c.dom.Text

class PantallaCargandoDatos : ActividadMadre() {
    private val tvDatos by lazy {this.findViewById<TextView>(R.id.tvCargaDatosCargaDatos)}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pantalla_cargando_datos)
        recogerUsuario(intent)
    }

    override fun onStart() {
        super.onStart()


    }

}