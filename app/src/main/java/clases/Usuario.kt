package clases

import android.graphics.Bitmap
import android.os.Parcel
import android.os.Parcelable
import java.time.LocalDate

open class Usuario :  {
     var nombreUsuario : String?
     var email : String?
     var imagenUsuario : Bitmap?
     var puntosActuales : Int?
     var totalPuntosRegistrados : Int?
     var fechaNacimiento : LocalDate?
     var fechaRegistro:LocalDate?



    /**
     * Constructor base que inicializa todas las variables internas a null.
     */
    constructor(){
        this.nombreUsuario=null
        this.email=null
        this.imagenUsuario=null
        this.puntosActuales=null
        this.totalPuntosRegistrados=null
        this.fechaRegistro=null
        this.fechaNacimiento=null

    }

    /**
     * Constructor que recibe todos los datos necesarios para un objeto Usuario.
     * Todas las variables pueden tener valor null.
     */
    constructor(nombreUsuario: String?,email:String?,imagenUsuario:Bitmap?,puntosActuales:Int?,totalPuntosRegistrados:Int?,fechaNacimiento:LocalDate?,fechaRegistro:LocalDate?) : this(){
        this.nombreUsuario=nombreUsuario
        this.email=email
        this.imagenUsuario=imagenUsuario
        this.puntosActuales=puntosActuales
        this.totalPuntosRegistrados=totalPuntosRegistrados
        this.fechaNacimiento=fechaNacimiento
        this.fechaRegistro=fechaRegistro

    }

    constructor(parcel: Parcel) : this() {
        nombreUsuario = parcel.readString()
        email = parcel.readString()
        imagenUsuario = parcel.readParcelable(Bitmap::class.java.classLoader)
        puntosActuales = parcel.readValue(Int::class.java.classLoader) as? Int
        totalPuntosRegistrados = parcel.readValue(Int::class.java.classLoader) as? Int
        fechaNacimiento = parcel.readValue(LocalDate::class.java.classLoader) as? LocalDate
        fechaRegistro = parcel.readValue(LocalDate::class.java.classLoader) as? LocalDate
    }
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(nombreUsuario)
        parcel.writeString(email)
        parcel.writeParcelable(imagenUsuario, flags)
        parcel.writeValue(puntosActuales)
        parcel.writeValue(totalPuntosRegistrados)
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