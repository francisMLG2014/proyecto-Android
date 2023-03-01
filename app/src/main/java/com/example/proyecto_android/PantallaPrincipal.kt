package com.example.proyecto_android

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Button
import android.widget.TextView
import clases.Usuario
import clases.UsuarioLogado

class PantallaPrincipal : ActividadMadre() {
    private val btnDesafios by lazy{this.findViewById<Button>(R.id.btnPrincipalDesafios)}
    private val btnRecompensas by lazy{this.findViewById<Button>(R.id.btnPrincipalRecompensas)}
    private val btnPerfil by lazy{this.findViewById<Button>(R.id.btnPrincipalMiPerfil)}
    private val btnAmigos by lazy{this.findViewById<Button>(R.id.btnPrincipalAmigos)}
    private val btnAjustes by lazy{this.findViewById<Button>(R.id.btnPrincipalAjustes)}
    private val tvNombreUsuario by lazy{this.findViewById<TextView>(R.id.tvPrincipalNombreUsuario)}
    private val tvPuntosActuales by lazy{this.findViewById<TextView>(R.id.tvPrincipalPuntosActuales)}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pantalla_principal)
        recogerUsuario(intent)
    }

    override fun onStart() {
        super.onStart()
        tvNombreUsuario.text=usuario?.nombreUsuario
        tvPuntosActuales.text=usuario?.puntosActuales.toString()

      /*  btnDesafios.setOnClickListener(){
            cambiarPantalla()
        }
        btnRecompensas.setOnClickListener(){
            cambiarPantalla()
        }
        btnPerfil.setOnClickListener(){
            cambiarPantalla()
        }
        btnAmigos.setOnClickListener(){
            cambiarPantalla()
        }
        btnAjustes.setOnClickListener(){
            cambiarPantalla()
        }*/

    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
    }

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        super.onSaveInstanceState(outState, outPersistentState)
    }

    override fun onDestroy() {
        super.onDestroy()
    }



}