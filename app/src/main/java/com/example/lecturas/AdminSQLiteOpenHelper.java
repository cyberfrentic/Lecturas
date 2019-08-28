package com.example.lecturas;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class AdminSQLiteOpenHelper extends SQLiteOpenHelper{
    public AdminSQLiteOpenHelper( Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase BaseDeDatos) {
        // usuarios de la app
        BaseDeDatos.execSQL("CREATE TABLE usuarios(user text primary key unique, pass text, nombre text)");
        //Lista de medidores
        BaseDeDatos.execSQL("CREATE TABLE padron(id int primary key unique, numloc text, contrato text, nombre text, direccion text, nummed text, tarifa text, lecant text, modif text)");
        //almaceno las lecturas tomadas
        BaseDeDatos.execSQL("CREATE TABLE lectura(contrato text primary key unique, lectura text, anomalia text)");
        //tabla del padron para las cedulas
        BaseDeDatos.execSQL("CREATE TABLE padronTotal (id int primary key unique, cuenta text, nombre text, dicreccion text, manzana text, lote text, giro text)");
        // cedulas capturadas
        BaseDeDatos.execSQL("CREATE TABLE cedula(contrato int primary key, gennombre text, gendireccion text, gencruzamientos text, gencolonia text, genmanzana text, genlote text, genopciones text, genotros text, tarttarifa text, tarvfopciones text, tartuso text, tomismedidor text, tommedidor text, tomisfunc text, tommisdesc text, tommisdesconectado text, tommisrob text, tommisina text, tommiscanc text, tomtisdirecta text, tomtiscancelada text, tomtisconolot text, tomtisclandes text, tomdmiscance text, tomdtiscence text, tomdiscotrlot text, tomdisclandes text, fuga text, imagefilename text, latitud real, longitud real )");


    }

    @Override
    public void onUpgrade(SQLiteDatabase BaseDeDatos, int oldVersion, int newVersion) {
        BaseDeDatos.execSQL("DROP TABLE IF EXISTS usuarios");
        BaseDeDatos.execSQL("DROP TABLE IF EXISTS padron");
        BaseDeDatos.execSQL("DROP TABLE IF EXISTS lectura");
        BaseDeDatos.execSQL("DROP TABLE IF EXISTS padronTotal");
        BaseDeDatos.execSQL("DROP TABLE IF EXISTS cedula");
        BaseDeDatos.execSQL("CREATE TABLE IF NOT EXISTS usuarios(user text primary key unique, pass text, nombre text)");
        BaseDeDatos.execSQL("CREATE TABLE IF NOT EXISTS padron(id int primary key unique, numloc text, contrato text, nombre text, direccion text, nummed text, tarifa text, lecant text, modif text)");
        BaseDeDatos.execSQL("CREATE TABLE IF NOT EXISTS lectura(contrato text primary key unique, lectura text, anomalia text)");
        BaseDeDatos.execSQL("CREATE TABLE IF NOT EXISTS padronTotal (id int primary key unique, cuenta text, nombre text, dicreccion text, manzana text, lote text, giro text)");
        BaseDeDatos.execSQL("CREATE TABLE IF NOT EXISTS cedula(contrato int primary key, gennombre text, gendireccion text, gencruzamientos text, gencolonia text, genmanzana text, genlote text, genopciones text, genotros text, tarttarifa text, tarvfopciones text, tartuso text, tomismedidor text, tommedidor text, tomisfunc text, tommisdesc text, tommisdesconectado text, tommisrob text, tommisina text, tommiscanc text, tomtisdirecta text, tomtiscancelada text, tomtisconolot text, tomtisclandes text, tomdmiscance text, tomdtiscence text, tomdiscotrlot text, tomdisclandes text, fuga text, imagefilename text, latitud real, longitud real )");

    }
}
