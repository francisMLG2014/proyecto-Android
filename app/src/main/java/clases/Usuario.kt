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
open class Usuario(nombreUsuario: String?,email: String?,imagenUsuario: String?,puntosActuales: Int?,totalPuntosRegistrados: Int?,fechaNacimiento: LocalDate?,fechaRegistro: LocalDate?) :  Parcelable{

    var nombreUsuario : String? =nombreUsuario
     var email : String? =email
     var imagenUsuario : String? =imagenUsuario
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
        imagenUsuario = parcel.readString()
        puntosActuales = parcel.readValue(Int::class.java.classLoader) as? Int
        totalPuntosRegistrados = parcel.readValue(Int::class.java.classLoader) as? Int
        fechaNacimiento=LocalDate.ofEpochDay(parcel.readLong())
        fechaRegistro=LocalDate.ofEpochDay(parcel.readLong())
    }
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(nombreUsuario)
        parcel.writeString(email)
        parcel.writeString(imagenUsuario)
        parcel.writeValue(puntosActuales)
        parcel.writeValue(totalPuntosRegistrados)
        parcel.writeValue(fechaNacimiento?.toEpochDay())
        parcel.writeValue((fechaRegistro?.toEpochDay()))
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