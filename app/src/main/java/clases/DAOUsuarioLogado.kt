package clases
import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import com.example.proyecto_android.R
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import java.io.ByteArrayOutputStream
import java.time.format.DateTimeFormatter
import kotlin.collections.HashMap
class DAOUsuarioLogado (c: Context ){
    var c=c
    val formatoFecha = DateTimeFormatter.ofPattern(c.getString(R.string.formato_fecha))
    /*public fun recuperarDatosUsuarioLogado(email: String):UsuarioLogado{
//TODO implementar metodo que recupera datos de un usuarioLogado
    }

    public fun recuperarDatosUsuario(email:String):Usuario{
//TODO implementar metodo que recupera datos de un usuario

    }*/




    public fun registrarNuevoUsuario(user:UsuarioLogado?):Unit{

        //TODO
            var URLImagen = ""

            var db = FirebaseFirestore.getInstance()
            val datosUsuario = HashMap<String, Any>()

            datosUsuario.put(
                c.getString(R.string.campo_nombreUsuario),
                user?.nombreUsuario.toString()
            )
            datosUsuario.put(c.getString(R.string.campo_email), user?.email.toString())
            datosUsuario.put(c.getString(R.string.campo_imagen), URLImagen)
            datosUsuario.put(c.getString(R.string.campo_puntos_actuales), user?.puntosActuales!!)
            datosUsuario.put(c.getString(R.string.campo_puntos_totales), user.totalPuntosRegistrados!!
            )
            datosUsuario.put(
                c.getString(R.string.campo_fecha_nacimiento),
                user.fechaNacimiento!!.format(this.formatoFecha).toString()
            )
            datosUsuario.put(
                c.getString(R.string.campo_fecha_registro),
                user.fechaRegistro!!.format(this.formatoFecha).toString()
            )

            //Creamos un documento nuevo con id el email del usuario y lo metemos en la coleccion usuarios
            db.collection(c.getString(R.string.coleccion_usuarios)).document(user.email.toString())
                .set(datosUsuario).addOnSuccessListener {
                    //Subimos la imagen al storage con una referencia al nombre del usuario
                val storageRef = Firebase.storage
                val imagenesRef =
                    storageRef.reference.child(c.getString(R.string.ruta_imagenes_usuarios) + "/" + user.email.toString() + c.getString(R.string.formato_imagen_png))
                val stream = ByteArrayOutputStream()
                user.imagenUsuario?.compress(Bitmap.CompressFormat.JPEG, 100, stream)
                val datos = stream.toByteArray()
                imagenesRef.putBytes(datos).addOnCompleteListener() {
                    //Trás subirla, actualizamos los datos del usuario y le añadimos la url a su imagen
                    imagenesRef.downloadUrl.addOnSuccessListener {
                        URLImagen = it.toString()
                        //db = FirebaseFirestore.getInstance()
                        db.collection(c.getString(R.string.coleccion_usuarios)).document(user.email.toString())
                            .update(c.getString(R.string.campo_imagen), URLImagen)
                    }.addOnFailureListener(){
                        throw it
                    }
                }.addOnFailureListener(){
                    throw it
                }
            }.addOnFailureListener(){
                throw it
                }
    }
}