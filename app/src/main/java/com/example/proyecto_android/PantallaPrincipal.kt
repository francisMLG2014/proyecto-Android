package com.example.proyecto_android

import android.content.Context
import android.graphics.BitmapFactory
import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import clases.DAOUsuarioLogado
import clases.PreferenciasAplicacion
import clases.Usuario
import clases.UsuarioLogado
import java.io.File

class PantallaPrincipal : ActividadMadre() {
    private val btnDesafios by lazy{this.findViewById<Button>(R.id.btnPrincipalDesafios)}
    private val btnRecompensas by lazy{this.findViewById<Button>(R.id.btnPrincipalRecompensas)}
    private val btnPerfil by lazy{this.findViewById<Button>(R.id.btnPrincipalMiPerfil)}
    private val btnAmigos by lazy{this.findViewById<Button>(R.id.btnPrincipalAmigos)}
    private val btnAjustes by lazy{this.findViewById<Button>(R.id.btnPrincipalAjustes)}
    private val tvNombreUsuario by lazy{this.findViewById<TextView>(R.id.tvPrincipalNombreUsuario)}
    private val tvPuntosActuales by lazy{this.findViewById<TextView>(R.id.tvPrincipalPuntosActuales)}
    private val ivImagen by lazy{this.findViewById<ImageView>(R.id.ivPrincipal)}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pantalla_principal)
        recogerUsuario(intent)
    }

    override fun onStart() {
        super.onStart()
        tvNombreUsuario.text=usuario?.nombreUsuario
        tvPuntosActuales.text=usuario?.puntosActuales.toString()
        Toast.makeText(this,usuario?.nombreUsuario+" - "+usuario?.email,Toast.LENGTH_SHORT).show()
        Toast.makeText(this,usuario?.imagenUsuario+" - "+usuario?.ruta,Toast.LENGTH_SHORT).show()
        Toast.makeText(this,usuario?.puntosActuales.toString()+" - "+usuario?.totalPuntosRegistrados.toString(),Toast.LENGTH_SHORT).show()
        Toast.makeText(this,usuario?.fechaNacimiento.toString()+" - "+usuario?.fechaRegistro.toString(),Toast.LENGTH_SHORT).show()


        var file=File(filesDir,usuario?.ruta)
        val bitmap=BitmapFactory.decodeFile(file.toString())
        ivImagen.setImageBitmap(bitmap)




          btnDesafios.setOnClickListener(){
              val preferenciasCompartidas=this.getSharedPreferences(getString(R.string.variable_shared_preferences),
                Context.MODE_PRIVATE)
            Toast.makeText(this,preferenciasCompartidas.getBoolean(getString(R.string.variable_registro),true).toString(),Toast.LENGTH_LONG).show()
            var preferencias= PreferenciasAplicacion(preferenciasCompartidas,this)
            preferencias.setIdioma(getString(R.string.shared_preferences_idioma_es))
              recreate()
          }
          btnRecompensas.setOnClickListener(){
              //TODO quitar esta implementacion de aqui
              val preferenciasCompartidas=this.getSharedPreferences(getString(R.string.variable_shared_preferences),
                  Context.MODE_PRIVATE)
              Toast.makeText(this,preferenciasCompartidas.getBoolean(getString(R.string.variable_registro),true).toString(),Toast.LENGTH_LONG).show()
              var preferencias= PreferenciasAplicacion(preferenciasCompartidas,this)
              preferencias.setIdioma(getString(R.string.shared_preferences_idioma_en))
              recreate()
          }
        /*  btnPerfil.setOnClickListener(){
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