package clases

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.os.Build
import android.os.Parcel
import android.os.Parcelable
import androidx.annotation.RequiresApi
import androidx.core.os.ParcelCompat.readParcelable
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId

class UsuarioLogado(nombreUsuario: String?, email:String?, imagenUsuario: Bitmap?, puntosActuales:Int?, totalPuntosRegistrados:Int?, fechaNacimiento: LocalDate?, fechaRegistro: LocalDate?,listaAmigos : ArrayList<Usuario>?)
    : Usuario(nombreUsuario,email,imagenUsuario,puntosActuales,totalPuntosRegistrados,fechaNacimiento,fechaRegistro), Parcelable {
     var listaAmigos : ArrayList<Usuario>?=listaAmigos



    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    constructor(parcel: Parcel, bitmap: Bitmap?) : this(
        parcel.readString()!!,
        parcel.readString(),
        parcel.readParcelable<Bitmap>(Bitmap::class.java.classLoader,Bitmap::class.java),
        parcel.readInt(),
        parcel.readInt(),
        Instant.ofEpochMilli(parcel.readLong())
            .atZone(ZoneId.systemDefault())
            .toLocalDate(),
        Instant.ofEpochMilli(parcel.readLong())
            .atZone(ZoneId.systemDefault())
            .toLocalDate(),
        parcel.createTypedArrayList(Usuario.CREATOR)!!
    )
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString(),
        parcel.readParcelable<Bitmap>(Bitmap::class.java.classLoader),
        parcel.readInt(),
        parcel.readInt(),
        Instant.ofEpochMilli(parcel.readLong())
            .atZone(ZoneId.systemDefault())
            .toLocalDate(),
        Instant.ofEpochMilli(parcel.readLong())
            .atZone(ZoneId.systemDefault())
            .toLocalDate(),
        parcel.createTypedArrayList(Usuario.CREATOR)!!
    )


    override fun writeToParcel(parcel: Parcel, flags: Int) {
        super.writeToParcel(parcel, flags)
        parcel.writeTypedList(listaAmigos)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<UsuarioLogado> {

        override fun createFromParcel(parcel: Parcel): UsuarioLogado {
            var bitmap:Bitmap?=null
            return if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
                UsuarioLogado(parcel, bitmap)
            }else{
                UsuarioLogado(parcel)
            }




        }

        override fun newArray(size: Int): Array<UsuarioLogado?> {
            return arrayOfNulls(size)
        }
    }




}