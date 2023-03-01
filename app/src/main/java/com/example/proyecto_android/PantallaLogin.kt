package com.example.proyecto_android

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Switch
import android.widget.Toast
import clases.Usuario
import clases.UsuarioLogado
import com.google.firebase.auth.FirebaseAuth
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.time.LocalDate

class PantallaLogin : ActividadMadre() {
    private val btnIniciarSesion by lazy{this.findViewById<Button>(R.id.btnLoginIniciarSesion)}
    private val btnRegistrarme by lazy{this.findViewById<Button>(R.id.btnLoginRegistrarme)}
    private val edtEmail by lazy{this.findViewById<EditText>(R.id.edtLoginGmail)}
    private val edtContrasena by lazy{this.findViewById<EditText>(R.id.edtLoginContrasena)}
    private val switchGuardarEmail by lazy{this.findViewById<Switch>(R.id.switchLoginRecuerdame)}


    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pantalla_login)
        recogerUsuario(intent)
        Toast.makeText(this,this.usuario?.nombreUsuario+" - "+this.usuario?.email,Toast.LENGTH_SHORT).show()
        Toast.makeText(this,this.usuario?.puntosActuales.toString()+" - "+this.usuario?.totalPuntosRegistrados.toString(),Toast.LENGTH_SHORT).show()
        Toast.makeText(this,""+this.usuario?.fechaNacimiento.toString()+" - "+this.usuario?.fechaRegistro.toString(),Toast.LENGTH_SHORT).show()

    }

    override fun onStart() {
        super.onStart()
        val preferences = getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE)
        if(preferences.getBoolean("switchEmail",false)){
            switchGuardarEmail.isChecked=true
            edtEmail.setText(preferences.getString("email",""))
        }

        btnRegistrarme.setOnClickListener(){
            val bundle:Bundle=Bundle()
            bundle.putString("Email",edtEmail.text.toString())
            bundle.putString("Contrasena",edtContrasena.text.toString())
            this.cambiarPantalla(PantallaRegistro::class.java,bundle)

        }
        btnIniciarSesion.setOnClickListener(){
            Toast.makeText(this,R.string.no_implementado,Toast.LENGTH_SHORT).show()

            if(comprobarCampos()) {

                iniciarSesion()


                if (switchGuardarEmail.isChecked) {
                    val editor=preferences.edit()
                    editor.putBoolean("switchEmail", true)
                    editor.putString("email", edtEmail.text.toString())
                    editor.commit()
                }
                this.cambiarPantalla(PantallaPrincipal::class.java, null)
            }
        }
        switchGuardarEmail.setOnClickListener(){
            val editor=preferences.edit()
            if(!switchGuardarEmail.isChecked){
                editor.putBoolean("switchEmail",false)
                editor.commit()
            }else{
                editor.putBoolean("switchEmail",true)
                editor.commit()
            }
        }



    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("Email",edtEmail.text.toString())
        outState.putString("Contrasena",edtContrasena.text.toString())
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
    }

    override fun onDestroy() {
        super.onDestroy()
    }



    private fun comprobarCampos():Boolean{
        var bool:Boolean=true
            if(this.edtEmail.text.isBlank()){
                edtEmail.error=R.string.error_campo_no_valido.toString()
                bool=false
            }
            if(this.edtContrasena.text.isBlank()||this.edtContrasena.text.length<6){
                edtContrasena.error=R.string.error_contrasena.toString()
                bool=false
            }
        return bool
    }


    private fun iniciarSesion():Unit{
        //TODO Intento de inicio de sesion con email y contrasena de firebase.
        val auth:FirebaseAuth=FirebaseAuth.getInstance()
        auth.signInWithEmailAndPassword(this.edtEmail.text.toString(),this.edtContrasena.text.toString()).addOnCompleteListener {
            if(it.isSuccessful){
                //TODO: Crear conexion a firebase. Pasar a pantalla carga datos con email y contrasena
                /*usuarioLogado = Usuario(
                    binding.campoEmail.text.toString(),
                    binding.campoContraseA.text.toString()
                )*/

                intent.extras?.putString("Email",edtEmail.text.toString())
                intent.extras?.putString("Contrasena",edtContrasena.text.toString())
                cambiarPantalla(PantallaCargandoDatos::class.java,intent.extras)

            }else{
                //TODO hacer un error en condiciones cuando falle el login
                it.exception?.printStackTrace()
                Toast.makeText(
                    this,
                    it.exception.toString(),
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }
}