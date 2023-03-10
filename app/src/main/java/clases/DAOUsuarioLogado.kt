package clases
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import com.example.proyecto_android.R
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream
import java.io.File
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import kotlin.collections.HashMap
class DAOUsuarioLogado (c: Context){
    var c=c
    val formatoFecha = DateTimeFormatter.ofPattern(c.getString(R.string.formato_fecha))
    public suspend fun recuperarDatosUsuarioLogado(email: String): UsuarioLogado? = withContext(
        Dispatchers.IO) {
        var user: UsuarioLogado? = null

        val db = FirebaseFirestore.getInstance()
        val docRef = db.collection(c.getString(R.string.coleccion_usuarios)).document(email)

        val docSnapshot = docRef.get().await()
        if (docSnapshot != null) {
            val nombre = docSnapshot.get(c.getString(R.string.campo_nombreUsuario)) as String
            val email = docSnapshot.get(c.getString(R.string.campo_email)) as String
            val url = docSnapshot.get(c.getString(R.string.campo_imagen)) as String
            val puntosActuales = docSnapshot.getLong(c.getString(R.string.campo_puntos_actuales))?.toInt()
            val puntosTotales = docSnapshot.getLong(c.getString(R.string.campo_puntos_totales))?.toInt()
            val fechaNacimiento = LocalDate.parse(docSnapshot.get(c.getString(R.string.campo_fecha_nacimiento)).toString(), formatoFecha) as LocalDate
            val fechaRegistro = LocalDate.parse(docSnapshot.get(c.getString(R.string.campo_fecha_registro)).toString(), formatoFecha) as LocalDate
            val ruta:String=email+obtenerExtension(url)
            user = UsuarioLogado(nombre, email, url, puntosActuales, puntosTotales, fechaNacimiento, fechaRegistro, ArrayList<Usuario>(), ruta)
            //TODO cuando puedas agregar amigos, aqui deberás recoger los nombres de cada uno y agregarlos como usuarios al arrayList
            val docAmigos = docSnapshot.get(c.getString(R.string.campo_amigos))
            user.ruta =cargarImagenAlmacenamientoInterno(user)
        }
        return@withContext user
    }

    @SuppressLint("SuspiciousIndentation")
    public suspend fun recuperarDatosUsuario(email:String):Usuario? = withContext(
        Dispatchers.IO){
        var usuario:Usuario?=null
        var db = FirebaseFirestore.getInstance()
        val docRef=db.collection(c.getString(R.string.coleccion_usuarios)).document(email)
            val doc=docRef.get().await()
                if(doc!=null){
                    val nombre=doc.get(c.getString(R.string.campo_nombreUsuario)) as String
                    val email=doc.get(c.getString(R.string.campo_email)) as String
                    val url=doc.get(c.getString(R.string.campo_imagen)) as String
                    val puntosActuales=doc.get(c.getString(R.string.campo_puntos_actuales)) as Int
                    val puntosTotales=doc.get(c.getString(R.string.campo_puntos_totales)) as Int
                    val fechaNacimiento= LocalDate.parse(doc.get(c.getString(R.string.fecha_nacimiento)).toString(),formatoFecha)
                    val fechaRegistro=LocalDate.parse(doc.get(c.getString(R.string.campo_fecha_registro)).toString(),formatoFecha)
                    usuario=Usuario(nombre,email,url,puntosActuales,puntosTotales,fechaNacimiento,fechaRegistro)

                    cargarImagenAlmacenamientoInterno(usuario)
            }

return@withContext usuario
    }




    public suspend fun registrarNuevoUsuario(user:UsuarioLogado?):Unit = withContext(
        Dispatchers.IO){

            var db = FirebaseFirestore.getInstance()
            val datosUsuario = HashMap<String, Any>()

            datosUsuario.put(
                c.getString(R.string.campo_nombreUsuario),
                user?.nombreUsuario.toString()
            )
            datosUsuario.put(c.getString(R.string.campo_email), user?.email.toString())
            datosUsuario.put(c.getString(R.string.campo_imagen), user?.imagenUsuario.toString())
            datosUsuario.put(c.getString(R.string.campo_puntos_actuales), user?.puntosActuales!!)
            datosUsuario.put(c.getString(R.string.campo_puntos_totales), user.totalPuntosRegistrados!!
            )
            datosUsuario.put(
                c.getString(R.string.campo_fecha_nacimiento),
                user.fechaNacimiento!!.format(formatoFecha).toString()
            )
            datosUsuario.put(
                c.getString(R.string.campo_fecha_registro),
                user.fechaRegistro!!.format(formatoFecha).toString()
            )

            //Creamos un documento nuevo con id el email del usuario y lo metemos en la coleccion usuarios
            db.collection(c.getString(R.string.coleccion_usuarios)).document(user.email.toString())
                .set(datosUsuario).await()

            user.ruta=cargarImagenAlmacenamientoInterno(user)

                /*ESTE CODIGO ME SERVIRÁ PARA ACTUALIZAR LAS IMAGENES Y URL DE ELLAS
                val storageRef = Firebase.storage
                val imagenesRef =
                    storageRef.reference.child(c.getString(R.string.ruta_imagenes_usuarios) + "/" + user.email.toString() + c.getString(R.string.formato_imagen_jpg))
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
                }*/

    }
      fun obtenerExtension(ruta:String?):String{
         var extension=""
         if(ruta!=null) {
             if (ruta.contains(".")) {
                 extension = ruta.substring(ruta.lastIndexOf('.', ruta.length-1, true))
             }
         }
        return extension

    }

    private suspend fun cargarImagenAlmacenamientoInterno(user: Usuario):String=withContext(
        Dispatchers.IO){
        var ruta:String=""
        ruta=user?.email+obtenerExtension(user.imagenUsuario)
        val file= File(c.filesDir, ruta)
        val storageRef = Firebase.storage.reference.child(user.imagenUsuario.toString())
        storageRef.getFile(file).await()
        return@withContext ruta
    }

    public fun rutaImagenAlmacenamientoInterno(user:Usuario):String{
        return user?.email+obtenerExtension(user.imagenUsuario)
    }
    public fun archivoImagenAlmacenamientoInterno(user:Usuario):File{
        var ruta=user?.email+obtenerExtension(user.imagenUsuario)
         return File(c.filesDir, ruta)
    }
}