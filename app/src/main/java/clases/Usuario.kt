package clases

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.os.Build
import android.os.Build.VERSION_CODES.TIRAMISU
import android.os.Parcel
import android.os.Parcelable
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.ZoneOffset
import java.util.*

/**
 * Clase que contiene los datos necesarios para almacenar los datos de un usuario
 */
open class Usuario(nombreUsuario: String?,email: String?,imagenUsuario: Bitmap?,puntosActuales: Int?,totalPuntosRegistrados: Int?,fechaNacimiento: LocalDate?,fechaRegistro: LocalDate?) :  Parcelable{

    var nombreUsuario : String? =nombreUsuario
     var email : String? =email
     var imagenUsuario : Bitmap? =imagenUsuario
     var puntosActuales : Int? =puntosActuales
     var totalPuntosRegistrados : Int? =totalPuntosRegistrados
     var fechaNacimiento : LocalDate? =fechaNacimiento
     var fechaRegistro:LocalDate? =fechaRegistro


    /**
     * Constructor vacío
     */
    constructor():this(null,null,null,null,null,null,null)



    //---------------


    @SuppressLint("SuspiciousIndentation")
    constructor(parcel: Parcel) : this() {
        nombreUsuario = parcel.readString()
        email = parcel.readString()

            if (Build.VERSION.SDK_INT >= TIRAMISU) {
                imagenUsuario = parcel.readParcelable(Bitmap::class.java.classLoader,Bitmap::class.java)
            }else{
                imagenUsuario = parcel.readParcelable(Bitmap::class.java.classLoader)
            }

        puntosActuales = parcel.readValue(Int::class.java.classLoader) as? Int
        totalPuntosRegistrados = parcel.readValue(Int::class.java.classLoader) as? Int
        fechaNacimiento=LocalDate.ofEpochDay(parcel.readLong())
        fechaRegistro=LocalDate.ofEpochDay(parcel.readLong())
    }
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(nombreUsuario)
        parcel.writeString(email)
        parcel.writeParcelable(imagenUsuario, flags)
        parcel.writeValue(puntosActuales)
        parcel.writeValue(totalPuntosRegistrados)
        parcel.writeValue(Date.from(fechaNacimiento?.atStartOfDay()!!.toInstant(ZoneOffset.UTC)).time)
        parcel.writeValue(Date.from(fechaRegistro?.atStartOfDay()!!.toInstant(ZoneOffset.UTC)).time)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Usuario> {
        override fun createFromParcel(parcel: Parcel): Usuario {
            return Usuario(parcel)
        }

        override fun newArray(size: Int): Array<Usuario?> {
            return arrayOfNulls(size)
        }
    }


}