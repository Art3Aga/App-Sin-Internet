package com.ejemplo.volleymysql;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ConexionSQliteHelper extends SQLiteOpenHelper {
    //String ID_PK=" INTEGER PRIMARY KEY AUTOINCREMENT DEFAULT 1 ";
    final String TABLA_PRODUCTOS = "create table productos (id_producto INTEGER, descripcion TEXT, barcode TEXT, costo_promedio TEXT, porcentaje_utilidad1 TEXT, image TEXT)";
    //al llamar este constructor automaticamente se crea la BD
    public ConexionSQliteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }
    //este metodo genera las tablas de la BD
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLA_PRODUCTOS);

    }
    //cada vez que se ejecuta la app o la instalamos denuevo, verifica si existe una version antigua de la BD para refrescar alguna funcion
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists productos");
        onCreate(db);
    }
}
