package com.example.proyecto_android

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import clases.UsuarioLogado

open abstract class ActividadMadre : AppCompatActivity() {
     var usuario:UsuarioLogado?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    public fun cambiarPantalla(pantalla:String):Unit{
        var intent:Intent=Intent(this,pantalla.javaClass)
        intent.putExtra("usuario",usuario)
        this.startActivity(intent)
    }



}