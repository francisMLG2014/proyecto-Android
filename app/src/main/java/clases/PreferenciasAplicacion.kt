package clases

import android.content.Context
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatDelegate
import com.example.proyecto_android.R
import java.util.*

class PreferenciasAplicacion(c:Context) {
    var c:Context=c
    val prefs=c.getSharedPreferences(c.getString(R.string.variable_shared_preferences),//TODO supuestamente esto es null y la app peta. Busca solucion
        Context.MODE_PRIVATE)

    public fun setIdioma(language:String):Unit{
        val locale = Locale(language)
        Locale.setDefault(locale)
        val config = Configuration()
        config.setLocale(locale)
            var editor=prefs.edit()
            editor.putString(c.getString(R.string.shared_preferences_idioma),language)
            editor.commit()
    }

    public fun setModoOscuro(option:Int=AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM){
        AppCompatDelegate.setDefaultNightMode(option)
    }
}