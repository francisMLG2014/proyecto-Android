package clases

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.os.Build
import android.os.Parcel
import android.os.Parcelable
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.os.ParcelCompat.readParcelable
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId

/**
 * Clase que contiene los datos necesarios para crear al usuario logado de nuestra aplicacion
 */
class UsuarioLogado(nombreUsuario: String?, email:String?, imagenUsuario: String?, puntosActuales:Int?, totalPuntosRegistrados:Int?, fechaNacimiento: LocalDate?, fechaRegistro: LocalDate?,listaDeAmigos : ArrayList<Usuario>?,ruta:String?)
    : Usuario(nombreUsuario,email,imagenUsuario,puntosActuales,totalPuntosRegistrados,fechaNacimiento,fechaRegistro), Parcelable {
    var listaAmigos : ArrayList<Usuario>?= listaDeAmigos //TODO esta variable en realidad deberia ser un hashmap. Revisarlo en un futuro
    var ruta:String?=ruta

    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readValue(Int::class.java.classLoader) as? Int,
        LocalDate.ofEpochDay(parcel.readValue(Long::class.java.classLoader) as Long),
        LocalDate.ofEpochDay(parcel.readValue(Long::class.java.classLoader) as Long),
        parcel.createTypedArrayList(Usuario.CREATOR),
        parcel.readString()
    )


    override fun writeToParcel(parcel: Parcel, flags: Int) {
        super.writeToParcel(parcel, flags)
        parcel.writeTypedList(listaAmigos)
        parcel.writeString(ruta)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<UsuarioLogado> {

        override fun createFromParcel(parcel: Parcel): UsuarioLogado {


            return UsuarioLogado(parcel)

        }

        override fun newArray(size: Int): Array<UsuarioLogado?> {
            return arrayOfNulls(size)
        }
    }




}