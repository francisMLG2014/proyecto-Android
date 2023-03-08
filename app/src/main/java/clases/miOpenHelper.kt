package clases

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class miOpenHelper(contexto:Context) : SQLiteOpenHelper(contexto,"miBD",null,1) {
    override fun onCreate(db: SQLiteDatabase?) {
        TODO("Not yet implemented")
        //AQUI CREAS TODO EL CODIGO QUE DEBE CREAR LA BBDD, INCLUIDO LOS INSERTS
        db!!.execSQL("")//create table ...
        db.execSQL("")//insert into table...
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        if(oldVersion<2){
            //Codigo de la version 2
            // db.execSQL("")//alter table...
            //  db.execSQL("") //modify...
        }
        if(oldVersion<3){
            //Codigo de la version 2
        }
        TODO("Not yet implemented")
    }




    //var bd=miOpenHelper(this:Context).writtableDatabase()
}