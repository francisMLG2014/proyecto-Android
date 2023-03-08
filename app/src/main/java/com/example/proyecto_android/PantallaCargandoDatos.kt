package com.example.proyecto_android

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import clases.DAOUsuarioLogado
import clases.PreferenciasAplicacion
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.w3c.dom.Text

class PantallaCargandoDatos : ActividadMadre() {
    private val tvDatos by lazy {this.findViewById<TextView>(R.id.tvCargaDatosCargaDatos)}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pantalla_cargando_datos)
        recogerUsuario(intent)
    }

    override  fun onStart() {
        super.onStart()
        var dao:DAOUsuarioLogado= DAOUsuarioLogado(this)
        var reg=intent.extras?.getBoolean(getString(R.string.variable_registro))
        try{

        if(reg==true){
            //REGISTRO
            GlobalScope.launch(Dispatchers.Main) {
                dao.registrarNuevoUsuario(usuario)
                tvDatos.text=getString(R.string.bienvenida)+" "+usuario?.nombreUsuario
                delay(1000)
                cambiarPantalla(PantallaPrincipal::class.java,intent.extras)
                finish()
            }
        }else{
            //LOGIN

            GlobalScope.launch(Dispatchers.Main) {
                usuario = dao.recuperarDatosUsuarioLogado(intent.extras?.getString(getString(R.string.variable_email))!!)
                tvDatos.text=getString(R.string.bienvenida)+" "+usuario?.nombreUsuario
                delay(1000)
                cambiarPantalla(PantallaPrincipal::class.java,intent.extras)
                finish()
            }
        }
        }catch (e: Exception){
            Toast.makeText(this,R.string.algo_ha_ido_mal,Toast.LENGTH_LONG)
            if(reg==true){
                cambiarPantalla(PantallaRegistro::class.java,intent.extras)
            }else{
                cambiarPantalla(PantallaLogin::class.java,intent.extras)
            }

            finish()
        }
    }
}