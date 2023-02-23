package clases

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.os.Build
import android.os.Parcel
import android.os.Parcelable
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId

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


    constructor(parcel: Parcel) : this() {
        nombreUsuario = parcel.readString()
        email = parcel.readString()

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                imagenUsuario = parcel.readParcelable(Bitmap::class.java.classLoader,Bitmap::class.java)
            }else{
                imagenUsuario = parcel.readParcelable(Bitmap::class.java.classLoader)
            }

        puntosActuales = parcel.readValue(Int::class.java.classLoader) as? Int
        totalPuntosRegistrados = parcel.readValue(Int::class.java.classLoader) as? Int
        fechaNacimiento=Instant.ofEpochMilli(parcel.readLong())
            .atZone(ZoneId.systemDefault())
            .toLocalDate()
        fechaRegistro=Instant.ofEpochMilli(parcel.readLong())
            .atZone(ZoneId.systemDefault())
            .toLocalDate()
    }
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(nombreUsuario)
        parcel.writeString(email)
        parcel.writeParcelable(imagenUsuario, flags)
        parcel.writeValue(puntosActuales)
        parcel.writeValue(totalPuntosRegistrados)
        parcel.writeLong(fechaNacimiento!!.toEpochDay())
        parcel.writeLong(fechaRegistro!!.toEpochDay())
    /*Fechas*/
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