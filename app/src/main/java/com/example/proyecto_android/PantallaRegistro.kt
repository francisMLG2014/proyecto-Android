package com.example.proyecto_android

import android.app.DatePickerDialog
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.widget.*
import clases.Usuario
import clases.UsuarioLogado
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import java.io.ByteArrayOutputStream
import java.time.LocalDate
import java.util.*
import kotlin.collections.ArrayList

class PantallaRegistro : ActividadMadre() {
    private val btnRegistrarme by lazy{this.findViewById<Button>(R.id.btnRegistroRegistrarme)}
    private val btnIniciarSesion by lazy{this.findViewById<Button>(R.id.btnRegistroInicioSesion)}
    private val edtEmail by lazy{this.findViewById<EditText>(R.id.edtRegistroGmail)}
    private val edtContrasena by lazy{this.findViewById<EditText>(R.id.edtRegistroContrasena)}
    private val btnFechaNacimiento by lazy{this.findViewById<Button>(R.id.btnRegistroFechaNacimiento)}
    private val edtNombreUsuario by lazy{this.findViewById<EditText>(R.id.edtRegistroNombreUsuario)}
    private var fechaNacimiento:LocalDate?=null
    //TODO Por ahora almaceno las imagenes iniciales aqui. En el puede que lo cambie
    private val libro1="resources/libro1.jpg"
    private val libro2="resources/libro2.jpg"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pantalla_registro)
        recogerUsuario(intent)
        var bundle:Bundle?=intent.extras
        if (bundle != null) {
            edtEmail.setText(bundle.getString(getString(R.string.variable_email)))
            edtContrasena.setText(bundle.getString(getString(R.string.variable_contrasena)))
        }
    }
    override fun onStart() {
        super.onStart()

        btnRegistrarme.setOnClickListener(){
            if(comprobarCampos()){
                val r=Random()
                val auth:FirebaseAuth=FirebaseAuth.getInstance()
                auth.createUserWithEmailAndPassword(edtEmail.text.toString(),edtContrasena.text.toString()).addOnCompleteListener(){
                    //Usando it podemos  comprobar el comportamiento
                    if(it.isSuccessful){
                        var imagen=if (r.nextBoolean()) libro1 else libro2
                        var nuevoUser:UsuarioLogado=UsuarioLogado(edtNombreUsuario.text.toString(),edtEmail.text.toString(),imagen,0,0,fechaNacimiento,LocalDate.now(),ArrayList<Usuario>(),"")

                        this.usuario=nuevoUser
                        var bundle=Bundle()
                        bundle.putBoolean(getString(R.string.variable_registro),true)
                        cambiarPantalla(PantallaCargandoDatos::class.java,bundle)
                    }else{
                         it.exception?.printStackTrace()
                        Toast.makeText(this,R.string.algo_ha_ido_mal,Toast.LENGTH_SHORT).show()
                     }
                }
            }else{
                Toast.makeText(this,R.string.completa_campos,Toast.LENGTH_LONG).show()
            }
        }
        btnIniciarSesion.setOnClickListener(){
            var bundle:Bundle=Bundle()
            bundle.putString(getString(R.string.variable_email),edtEmail.text.toString())
            bundle.putString(getString(R.string.variable_contrasena),edtContrasena.text.toString())
           this.cambiarPantalla(PantallaLogin::class.java,bundle)
        }
        val dateSetListener: DatePickerDialog.OnDateSetListener =
            DatePickerDialog.OnDateSetListener() { datePicker: DatePicker, year: Int, month: Int, day: Int ->
                fechaNacimiento= LocalDate.of(year, month+1, day);
                /*Aqui es donde deberia meter el codigo que quiero que se haga a la vez que se lee la fecha*/
                btnFechaNacimiento.text = fechaNacimiento.toString()
                btnFechaNacimiento.error=null
            }

        btnFechaNacimiento.setOnClickListener(){
            val calendario: Calendar = Calendar.getInstance()
            val datePicker: DatePickerDialog =
                DatePickerDialog(
                    this, dateSetListener,
                    calendario.get(Calendar.YEAR),
                    calendario.get(Calendar.MONTH),
                    calendario.get(Calendar.DAY_OF_MONTH)
                )
            datePicker.setMessage(getString(R.string.fecha_nacimiento))
            datePicker.show()
        }


    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
    }

    override fun onDestroy() {
        super.onDestroy()
    }
    private fun comprobarCampos():Boolean{
        var bool=true
        if(edtNombreUsuario.text.isBlank()){
            edtNombreUsuario.error=R.string.error_campo_no_valido.toString()
            bool=false
        }
        if(edtEmail.text.isBlank()){
            edtEmail.error=R.string.error_campo_no_valido.toString()
            bool=false
        }
        if(edtContrasena.text.isBlank()||edtContrasena.text.length<6){
            edtContrasena.error=R.string.error_campo_no_valido.toString()
            bool=false
        }
        if(fechaNacimiento==null){
            btnFechaNacimiento.error=R.string.error_campo_no_valido.toString()
            bool=false
        }

        return bool
    }
}