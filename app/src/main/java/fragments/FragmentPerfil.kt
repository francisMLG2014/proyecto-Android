package fragments

import android.app.Activity
import android.content.Context
import android.media.Image
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
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

    override fun onAttach(context: Context) {
        super.onAttach(context)
        a=context as ActividadMadre
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

        



    }


}