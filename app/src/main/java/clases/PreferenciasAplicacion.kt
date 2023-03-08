package clases

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.res.Configuration
import android.content.res.Resources
import android.os.Build
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.ConfigurationCompat

import com.example.proyecto_android.R
import java.util.*

class PreferenciasAplicacion(p: SharedPreferences, c:Context) {
    var cont:Context=c
    val prefs=p

    /*public fun setIdioma(language:String):Unit{
        val locale = Locale("es")
        Locale.setDefault(locale)
        val config = Configuration()
        config.setLocale(locale)
            var editor=prefs?.edit()
            editor?.putString(cont.getString(R.string.shared_preferences_idioma),language)
            editor?.commit()
    }*/
    public fun setIdioma(language:String):Unit{
        val locale = Locale(language)
        Locale.setDefault(locale)
        val config=Configuration()

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
            config.setLocale(locale)
            cont.createConfigurationContext(config)
            //TODO buscar la manera actualizada de cambiar el idioma
            cont.resources.updateConfiguration(config,cont.resources.displayMetrics)

        }else{
            config.locale=locale
            cont.resources.updateConfiguration(config,cont.resources.displayMetrics)
        }
        var editor=prefs.edit()
        editor.putString(cont.getString(R.string.shared_preferences_idioma),language)
        editor.commit()


    }

}