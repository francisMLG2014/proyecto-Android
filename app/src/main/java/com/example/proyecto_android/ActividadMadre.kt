package com.example.proyecto_android

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import clases.UsuarioLogado

open abstract class ActividadMadre : AppCompatActivity() {
     var usuario:UsuarioLogado?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

     fun <T>cambiarPantalla(clase:Class<T>,bundle: Bundle?):Unit {
         var intent = Intent(this, clase)

         if (bundle != null) {
             intent.putExtras(bundle)
         }
             intent.putExtra(getString(R.string.variable_usuario), usuario)
             this.startActivity(intent)

     }


         fun recogerUsuario(intent: Intent?): Unit {
             if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                 this.usuario = intent?.getParcelableExtra(getString(R.string.variable_usuario), UsuarioLogado::class.java)
             } else {
                 this.usuario = intent?.getParcelableExtra(getString(R.string.variable_usuario))
             }

         }



}