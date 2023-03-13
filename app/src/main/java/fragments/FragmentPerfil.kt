package fragments
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.TypedValue
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import clases.DAOUsuarioLogado
import clases.UsuarioLogado
import com.example.proyecto_android.ActividadMadre
import com.example.proyecto_android.PantallaLogin
import com.example.proyecto_android.R
import java.time.format.DateTimeFormatter

class FragmentPerfil(user:UsuarioLogado) : Fragment() {
    //TODO poder seleccionar imagen desde galeria; Implementar permisos de galeria.
    var usuario:UsuarioLogado=user
    lateinit var formatoFecha :DateTimeFormatter
    lateinit var c:Context
    lateinit var a: ActividadMadre
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
        a=context as ActividadMadre
        c=context
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //TODO meter aqui los contratos de los metodos de la imagen y subirlos a variable de clase.
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

        btnCambiarImagen.setOnClickListener(){
            preguntarPermisosGaleria()
        }
        btnCambiarContrasena.setOnClickListener(){
            //TODO hacer que puedas cambiar tu contrasena
        }

    }

    fun calcularDimensionesImagen():Float{
        val displayMetrics = resources.displayMetrics
        return (displayMetrics.widthPixels / displayMetrics.density * 0.63).toFloat()
    }
    fun preguntarPermisosGaleria(){
        val requestPermisionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()){
            if(it){
                cogerFotoGaleria()
            }else{
                Toast.makeText(c,c.getString(R.string.necesitas_conceder_permiso),Toast.LENGTH_LONG).show()
            }

        }



        if(ContextCompat.checkSelfPermission(c, android.Manifest.permission.READ_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED){
            cogerFotoGaleria()
        }else{
            requestPermisionLauncher.launch(android.Manifest.permission.READ_EXTERNAL_STORAGE)
        }

    }

    fun cogerFotoGaleria(){
        val  startForActivityGallery = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ){
            if(it.resultCode == Activity.RESULT_OK){
                val dataUri = it.data?.data
                var dao=DAOUsuarioLogado(c)
                    //TODO hacer un intento de subir la foto a firebase, y si falla, que no haga nada

                imagenUsuario.setImageURI(dataUri)
            }else{
                Toast.makeText(c,c.getString(R.string.no_seleccionaste_nada),Toast.LENGTH_LONG).show()
            }
        }

        val intent=Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        startForActivityGallery.launch(intent)

    }
}