package com.example.proyecto_android

import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import android.widget.Toast
import clases.Usuario
import clases.UsuarioLogado
import java.time.LocalDate
import java.time.ZoneId
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pantalla_registro)
        recogerUsuario(intent)
        var bundle:Bundle?=intent.extras
        if (bundle != null) {
            edtEmail.setText(bundle.getString("Email"))
            edtContrasena.setText(bundle.getString("Contrasena"))
        }
    }
    //TODO Crear un datepicker para recoger la fecha de nacimiento

    override fun onStart() {
        super.onStart()
        btnRegistrarme.setOnClickListener(){
            //TODO Aqui tienes que hacer que recoja los datos y se registre correctamente
            Toast.makeText(this,R.string.no_implementado, Toast.LENGTH_SHORT).show()
        }
        btnIniciarSesion.setOnClickListener(){
            val bundle:Bundle=Bundle()
            bundle.putString("Email",edtEmail.text.toString())
            bundle.putString("Contrasena",edtContrasena.text.toString())
            var fecha:LocalDate=LocalDate.now()
            this.usuario= UsuarioLogado("Franchute","fran@gmail.com",null,10,200
                ,fecha, fecha,ArrayList<Usuario>())
            this.cambiarPantalla(PantallaLogin::class.java,bundle)
        }
        val dateSetListener: DatePickerDialog.OnDateSetListener =
            DatePickerDialog.OnDateSetListener() { datePicker: DatePicker, year: Int, month: Int, day: Int ->

                val hoy: LocalDate = LocalDate.now()
                fechaNacimiento= LocalDate.of(year, month, day);
                /*Aqui es donde deberia meter el codigo que quiero que se haga a la vez que se lee la fecha*/
                btnFechaNacimiento.text = fechaNacimiento.toString()
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
            datePicker.setMessage(R.string.fecha_nacimiento.toString())
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
}