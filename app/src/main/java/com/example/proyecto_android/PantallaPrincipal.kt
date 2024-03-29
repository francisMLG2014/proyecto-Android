package com.example.proyecto_android

import android.Manifest
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AlertDialog.Builder;
import android.content.DialogInterface
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import fragments.FragmentAjustes
import fragments.FragmentDesafios
import fragments.FragmentPerfil
import org.w3c.dom.Text


class PantallaPrincipal : ActividadMadre() {
    /*     private val btnDesafios by lazy{this.findViewById<Button>(R.id.btnPrincipalDesafios)}
        private val btnRecompensas by lazy{this.findViewById<Button>(R.id.btnPrincipalRecompensas)}
       private val btnPerfil by lazy{this.findViewById<Button>(R.id.btnPrincipalMiPerfil)}
        private val btnAmigos by lazy{this.findViewById<Button>(R.id.btnPrincipalAmigos)}
        private val btnAjustes by lazy{this.findViewById<Button>(R.id.btnPrincipalAjustes)}
        private val ivImagen by lazy{this.findViewById<ImageView>(R.id.ivPrincipal)}
*/
    private val PERMISO_LEER_ALMACENAMIENTO_EXTERNO=123
    private val tvNombreUsuario by lazy{this.findViewById<TextView>(R.id.tvPrincipalNombreUsuario)}
    private val tvPuntosActuales by lazy{this.findViewById<TextView>(R.id.tvPrincipalPuntosActuales)}
    private val btnSocial by lazy{this.findViewById<Button>(R.id.btnPrincipalSuperior)}
    private val holderFragment by lazy{this.findViewById<FrameLayout>(R.id.fmPrincipal)}
    private val mbPrincipal by lazy{this.findViewById<BottomNavigationView>(R.id.mbPrincipal)}


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pantalla_principal)
        recogerUsuario(intent)
        comprobarPermisosGaleria()
        Toast.makeText(this,usuario?.ruta+" - "+usuario?.imagenUsuario,Toast.LENGTH_SHORT).show()


    }
    //TODO mirar fragments y echarle un ojo para esto también
    override fun onStart() {
        super.onStart()
        tvNombreUsuario?.text=(usuario?.nombreUsuario)
        tvPuntosActuales?.text=(usuario?.puntosActuales.toString())

        mbPrincipal.setOnItemSelectedListener {
            try {
                when (it.itemId) {
                    R.id.menu_bar_perfil -> cambiarFragment(FragmentPerfil(this.usuario!!))
                    R.id.menu_bar_desafios -> cambiarFragment(FragmentDesafios())
                    R.id.menu_bar_ajustes -> cambiarFragment(FragmentAjustes())
                }
            }catch (e:Exception){
                false
            }
            true
        }
        if(holderFragment.childCount<1){
            cambiarFragment(FragmentDesafios())
            mbPrincipal.selectedItemId=R.id.menu_bar_desafios
        }

        btnSocial.setOnClickListener(){
            tvNombreUsuario?.text=(usuario?.nombreUsuario)
            tvPuntosActuales?.text=(usuario?.puntosActuales.toString())
        }


        /*tvNombreUsuario.text=usuario?.nombreUsuario
        tvPuntosActuales.text=usuario?.puntosActuales.toString()
        */

/*        Toast.makeText(this,usuario?.nombreUsuario+" - "+usuario?.email,Toast.LENGTH_SHORT).show()
        Toast.makeText(this,usuario?.imagenUsuario+" - "+usuario?.ruta,Toast.LENGTH_SHORT).show()
        Toast.makeText(this,usuario?.puntosActuales.toString()+" - "+usuario?.totalPuntosRegistrados.toString(),Toast.LENGTH_SHORT).show()
        Toast.makeText(this,usuario?.fechaNacimiento.toString()+" - "+usuario?.fechaRegistro.toString(),Toast.LENGTH_SHORT).show()
*/
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



    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        super.onSaveInstanceState(outState, outPersistentState)

    }


    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        tvNombreUsuario?.text=(usuario?.nombreUsuario)
        tvPuntosActuales?.text=(usuario?.puntosActuales.toString())
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


    private fun comprobarPermisosGaleria(){
        if(ContextCompat.checkSelfPermission(this,Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
            ContextCompat.checkSelfPermission(this,Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
        {
            //Permiso no aceptado
            pedirPermisosGaleria()
        }else{
            Toast.makeText(this,"Borra este toast, ya tienes los permisos aceptados",Toast.LENGTH_LONG).show()
        }

    }

    fun pedirPermisosGaleria(){
        if(ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE) ||
            ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE))
        {
            //Ya has rechazado los permisos
            Toast.makeText(this,"Permisos ya rechazados. Puedes conceder los permisos en ajustes",Toast.LENGTH_LONG).show()
        }
        else{
            ActivityCompat.requestPermissions(this,arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE,android.Manifest.permission.WRITE_EXTERNAL_STORAGE),PERMISO_LEER_ALMACENAMIENTO_EXTERNO)
        }
    }

    //TODO crear strings y hacer toast de aceptacion de permisos
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode==PERMISO_LEER_ALMACENAMIENTO_EXTERNO){
            if(grantResults.isNotEmpty()&&grantResults.size>1&&grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this,"HAS CONCEDIDO LOS PERMISOS",Toast.LENGTH_LONG).show()
            }else{
                Toast.makeText(this,"Has rechazado los permisos por primera vez",Toast.LENGTH_LONG).show()
            }

        }


    }


}