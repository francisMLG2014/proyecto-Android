package clases

import android.graphics.Bitmap
import android.os.Parcelable
import java.time.LocalDate

class UsuarioLogado : Usuario,  {
    private var listaAmigos : ArrayList<Usuario>?

    /**
     * Constructor que inicializa las variables del usuario, a excepcion de su lista de amigos.
     */
     constructor(nombreUsuario: String?, email:String?, imagenUsuario: Bitmap?, puntosActuales:Int?, totalPuntosRegistrados:Int?, fechaNacimiento: LocalDate?, fechaRegistro: LocalDate?)
             : super(nombreUsuario,email,imagenUsuario,puntosActuales,totalPuntosRegistrados,fechaNacimiento,fechaRegistro){
         this.listaAmigos=null
    }

    /**
     * Constructor que inicializa todas las variables del usuario
     */
    constructor(nombreUsuario: String?, email:String?, imagenUsuario: Bitmap?, puntosActuales:Int?, totalPuntosRegistrados:Int?, fechaNacimiento: LocalDate?, fechaRegistro: LocalDate?,listaAmigos:ArrayList<Usuario>)
            : super(nombreUsuario,email,imagenUsuario,puntosActuales,totalPuntosRegistrados,fechaNacimiento,fechaRegistro){
        this.listaAmigos=listaAmigos
    }



}