package fragments

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.Toast
import clases.PreferenciasAplicacion
import com.example.proyecto_android.R


class FragmentAjustes : Fragment() {
    lateinit var c:Context
    lateinit var a:Activity
    lateinit var p:PreferenciasAplicacion
    lateinit var spinnerIdioma:Spinner
    lateinit var btnAplicar:Button
    lateinit var pref:SharedPreferences
    var pos=-1

    /**
     * Metodo en el cual inicializamos las variables internas
     */
    override fun onAttach(context: Context) {
        super.onAttach(context)
        c=context
        a=context as Activity
        pref=c.getSharedPreferences(c.getString(R.string.variable_shared_preferences),
            Context.MODE_PRIVATE)
        p= PreferenciasAplicacion(pref,c)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        /*if(pos>=0){
            spinnerIdioma.setSelection(pos)
        }*/
        return inflater.inflate(R.layout.fragment_ajustes, container, false)
    }

    /**
     * Inicializamos las variables de preferencia y el boton que aplica los cambios.
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
            pos = resources.getStringArray(R.array.array_idiomas_variable).indexOf(
                pref.getString(
                    c.getString(R.string.shared_preferences_idioma),
                    c.getString(R.string.shared_preferences_idioma_defval)
                )
            )

        //La posicion de los dos arrays definidos en el arrays.xml referente a los idiomas deberia ser la misma, por lo que no es necesario que busque el otro array
        spinnerIdioma=view.findViewById(R.id.spnAjustes)
        btnAplicar=view.findViewById(R.id.btnAjustesAplicar)
        spinnerIdioma.adapter=
            ArrayAdapter(c,R.layout.tv_spinner,
                resources.getStringArray(R.array.array_idiomas_soportados))
        spinnerIdioma.setSelection(pos)


        btnAplicar.setOnClickListener(){
            val idioma=resources.getStringArray(R.array.array_idiomas_variable)
            p.setIdioma(idioma[spinnerIdioma.selectedItemPosition])

            a.recreate()
        }



    }


}