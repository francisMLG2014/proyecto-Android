package com.example.proyecto_android

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import clases.DAOUsuarioLogado
import com.google.firebase.firestore.FirebaseFirestore
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
        if(intent.extras?.getBoolean("registro")==true){
            //REGISTRO
            try{dao.registrarNuevoUsuario(this.usuario)
                tvDatos.text=R.string.bienvenida.toString()+" "+this.usuario?.nombreUsuario
            }catch (e: Exception){
                Toast.makeText(this,R.string.algo_ha_ido_mal,Toast.LENGTH_LONG)
                cambiarPantalla(PantallaRegistro::class.java,intent.extras)
                finish()
            }
        }else{
            //LOGIN
            Log.v("ERROR","NO HA ENTRADO")
        }


        cambiarPantalla(PantallaPrincipal::class.java,intent.extras)


    }
    private fun esperar(millis:Long){
        try{
        var n=System.currentTimeMillis()
        while(millis>System.currentTimeMillis()-n){
            Thread.sleep(50)
        }}catch (e:Exception){

        }
    }

}