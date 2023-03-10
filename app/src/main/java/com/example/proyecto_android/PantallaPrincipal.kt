package com.example.proyecto_android

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AlertDialog.Builder;
import android.content.Context
import android.content.DialogInterface
import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.view.menu.MenuView
import androidx.core.view.children
import androidx.fragment.app.Fragment
import clases.PreferenciasAplicacion
import com.example.proyecto_android.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import fragments.FragmentAjustes
import fragments.FragmentDesafios
import fragments.FragmentPerfil
import java.io.File


class PantallaPrincipal : ActividadMadre() {
    /*     private val btnDesafios by lazy{this.findViewById<Button>(R.id.btnPrincipalDesafios)}
        private val btnRecompensas by lazy{this.findViewById<Button>(R.id.btnPrincipalRecompensas)}
       private val btnPerfil by lazy{this.findViewById<Button>(R.id.btnPrincipalMiPerfil)}
        private val btnAmigos by lazy{this.findViewById<Button>(R.id.btnPrincipalAmigos)}
        private val btnAjustes by lazy{this.findViewById<Button>(R.id.btnPrincipalAjustes)}
        private val ivImagen by lazy{this.findViewById<ImageView>(R.id.ivPrincipal)}

    private val tvNombreUsuario by lazy{this.findViewById<TextView>(R.id.tvPrincipalNombreUsuario)}
    private val tvPuntosActuales by lazy{this.findViewById<TextView>(R.id.tvPrincipalPuntosActuales)}
*/
    private val mbPrincipal by lazy{this.findViewById<BottomNavigationView>(R.id.mbPrincipal)}
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pantalla_principal)
        recogerUsuario(intent)
        cambiarFragment(FragmentDesafios())

    }
    //TODO mirar fragments y echarle un ojo para esto también
    override fun onStart() {
        super.onStart()

        mbPrincipal.setOnItemSelectedListener {
            try {
                when (it.itemId) {
                    R.id.menu_bar_perfil -> cambiarFragment(FragmentPerfil())
                    R.id.menu_bar_desafios -> cambiarFragment(FragmentDesafios())
                    R.id.menu_bar_ajustes -> cambiarFragment(FragmentAjustes())
                }
            }catch (e:Exception){
                false
            }
            true
        }

        /*tvNombreUsuario.text=usuario?.nombreUsuario
        tvPuntosActuales.text=usuario?.puntosActuales.toString()
        */

        Toast.makeText(this,usuario?.nombreUsuario+" - "+usuario?.email,Toast.LENGTH_SHORT).show()
        Toast.makeText(this,usuario?.imagenUsuario+" - "+usuario?.ruta,Toast.LENGTH_SHORT).show()
        Toast.makeText(this,usuario?.puntosActuales.toString()+" - "+usuario?.totalPuntosRegistrados.toString(),Toast.LENGTH_SHORT).show()
        Toast.makeText(this,usuario?.fechaNacimiento.toString()+" - "+usuario?.fechaRegistro.toString(),Toast.LENGTH_SHORT).show()

        /*
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
               btnPerfil.setOnClickListener(){
               //TODO pantalla de perfil funcional
                     cambiarPantalla()
                 }
                 btnAmigos.setOnClickListener(){
                     cambiarPantalla()
                 }
                 btnAjustes.setOnClickListener(){
                 //TODO pantalla de ajustes
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
        //usuario.deslogar()    auth.logOut()    Cualquier metodo del estilo estará en el onDestroy() de ActividadMadre.
        //TODO Al llamar a recreate en cualquier sitio, se llamará a on destroy, create, start y resume. Esto es un problema por ahora. ir a ActividadMadre.
    }

    override fun onBackPressed() {
        //TODO crear una alerta más agradable visualmente, añadir strings a los ficheros string

            val builder: AlertDialog.Builder = Builder(this)
            builder.setTitle("Salir")
            builder.setMessage("¿Estás seguro que quieres salir?")
            builder.setPositiveButton("Sí",
                DialogInterface.OnClickListener { dialog, which -> finish() })
            builder.setNegativeButton("No", null)
            builder.show()

    }

    private fun cambiarFragment(fragment : Fragment):Unit{
        val fragmentManager=supportFragmentManager
        val fragmentTransaction=fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fmPrincipal,fragment)
        fragmentTransaction.commit()
    }

}