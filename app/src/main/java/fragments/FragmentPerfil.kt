package fragments
import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.TypedValue
import android.view.*
import android.webkit.MimeTypeMap
import androidx.fragment.app.Fragment
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import clases.DAOUsuarioLogado
import clases.UsuarioLogado
import com.example.proyecto_android.PantallaLogin
import com.example.proyecto_android.PantallaPrincipal
import com.example.proyecto_android.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.time.format.DateTimeFormatter

class FragmentPerfil(user:UsuarioLogado) : Fragment() {
    //TODO poder seleccionar imagen desde galeria; Implementar permisos de galeria.

    var usuario:UsuarioLogado=user
    lateinit var formatoFecha :DateTimeFormatter
    lateinit var c:Context
    lateinit var a: PantallaPrincipal
    lateinit var imagenUsuario: ImageView //TODO inicializa la imagen
    lateinit var tvNombreUsuario:TextView
    lateinit var tvEmail:TextView
    lateinit var tvPuntosActuales:TextView
    lateinit var tvPuntosTotales:TextView
    lateinit var tvFechaNacimiento:TextView
    lateinit var tvFechaRegistro:TextView
    lateinit var btnCerrarSesion:Button
    lateinit var btnCambiarImagen:Button
    lateinit var btnCambiarContrasena:Button

    override fun onAttach(context: Context) {
        super.onAttach(context)
        a=activity as PantallaPrincipal
        c=context


    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_perfil, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        imagenUsuario=view.findViewById(R.id.ivPerfil)
        btnCerrarSesion=view.findViewById(R.id.btnPerfilDesconectar)
        formatoFecha=DateTimeFormatter.ofPattern(c.getString(R.string.formato_fecha))
        tvNombreUsuario=view.findViewById(R.id.tvPerfilNombreUsuario)
        tvEmail=view.findViewById(R.id.tvPerfilEmail)
        tvPuntosActuales=view.findViewById(R.id.tvPerfilPuntosActuales)
        tvPuntosTotales=view.findViewById(R.id.tvPerfilPuntosTotales)
        tvFechaNacimiento=view.findViewById(R.id.tvPerfilFechaNacimiento)
        tvFechaRegistro=view.findViewById(R.id.tvPerfilFechaRegistro)
        btnCambiarImagen=view.findViewById(R.id.btnPerfilCambiarImagen)
        btnCambiarContrasena=view.findViewById(R.id.btnPerfilCambiarContrasena)

        dimensionarImagen()

        tvNombreUsuario.text=usuario.nombreUsuario
        tvEmail.text=usuario.email
        tvPuntosActuales.text=usuario.puntosActuales.toString()
        tvPuntosTotales.text=usuario.totalPuntosRegistrados.toString()
        tvFechaNacimiento.text=usuario.fechaNacimiento?.format(formatoFecha).toString()
        tvFechaRegistro.text=usuario.fechaRegistro?.format(formatoFecha).toString()

        btnCerrarSesion.setOnClickListener(){
            DAOUsuarioLogado(c).desconectarUsuario()
            a.cambiarPantalla(PantallaLogin::class.java,null)
            a.finish()
        }

        btnCambiarImagen.setOnClickListener(){
            preguntarPermisosGaleria()
        }
        btnCambiarContrasena.setOnClickListener(){
            enviarPeticionResetPass()
        }



    }

    fun enviarPeticionResetPass(){
        val auth: FirebaseAuth = FirebaseAuth.getInstance()
        if(auth.currentUser!=null){
            try{
                auth.sendPasswordResetEmail(auth.currentUser!!.email!!)
                Toast.makeText(c,c.getString(R.string.peticion_enviada),Toast.LENGTH_LONG).show()
            }catch (e:Exception){
                Toast.makeText(c,c.getString(R.string.algo_ha_ido_mal),Toast.LENGTH_LONG).show()
            }
        }
    }


    fun dimensionarImagen(){
        val width=calcularDimensionesImagen()
        val widthInPx = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            width.toFloat(),
            resources.displayMetrics
        ).toInt()
        imagenUsuario.layoutParams.width=widthInPx
        imagenUsuario.layoutParams.height=widthInPx
        imagenUsuario.setImageBitmap(BitmapFactory.decodeFile(DAOUsuarioLogado(c).archivoImagenAlmacenamientoInterno(usuario).absolutePath))
        imagenUsuario.requestLayout()

    }
    fun calcularDimensionesImagen():Float{
        val displayMetrics = resources.displayMetrics
        return (displayMetrics.widthPixels / displayMetrics.density * 0.63).toFloat()
    }
    fun preguntarPermisosGaleria(){
        if(ContextCompat.checkSelfPermission(c, android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED &&
            ContextCompat.checkSelfPermission(c, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED){
            cogerFotoGaleria()
        }else{
            Toast.makeText(c,"No tienes todos los permisos, vete a ajustes",Toast.LENGTH_LONG).show()
        }

    }


    private val startForActivityGallery = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        if(it.resultCode == Activity.RESULT_OK){
            val data=it.data?.data!!
            var dao=DAOUsuarioLogado(c)
            GlobalScope.launch(Dispatchers.Main) {

                var bool:Boolean
                try{
                    bool=dao.subirImagen(data,usuario)
                    if(bool){
                        usuario.ruta=dao.rutaImagenAlmacenamientoInterno(usuario)
                        dao.cargarImagenAlmacenamientoInterno(usuario)
                    }else{
                        throw Exception()
                    }

                }catch (e:Exception){bool=false}
                if(bool){
                    imagenUsuario.setImageURI(data)
                    Toast.makeText(c,"OK",Toast.LENGTH_SHORT).show()

                }else{
                    Toast.makeText(c,"No se actualizó",Toast.LENGTH_SHORT).show()
                }

            }





        }
        else{
            Toast.makeText(c,"No has seleccionado foto",Toast.LENGTH_LONG).show()
        }
    }


    fun cogerFotoGaleria(){
        Toast.makeText(c,"COGIENDO FOTO",Toast.LENGTH_LONG).show()
        val intent=Intent(Intent.ACTION_GET_CONTENT)
        intent.type= "image/*"
        startForActivityGallery.launch(intent)
    }
}