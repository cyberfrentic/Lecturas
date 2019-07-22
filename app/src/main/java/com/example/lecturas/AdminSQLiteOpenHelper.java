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
        BaseDeDatos.execSQL("create table usuarios(user text primary key unique, pass text, nombre text)");
        BaseDeDatos.execSQL("create table padron(id int primary key unique, numloc text, contrato text, nombre text, direccion text, nummed text, tarifa text, lecant text, modif text)");
        BaseDeDatos.execSQL("create table lectura(contrato text primary key unique, lectura text, anomalia text)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase BaseDeDatos, int oldVersion, int newVersion) {
        BaseDeDatos.execSQL("DROP TABLE IF EXISTS usuarios");
        BaseDeDatos.execSQL("DROP TABLE IF EXISTS padron");
        BaseDeDatos.execSQL("DROP TABLE IF EXISTS lectura");
        BaseDeDatos.execSQL("CREATE TABLE IF NOT EXISTS usuarios(user text primary key unique, pass text, nombre text)");
        BaseDeDatos.execSQL("CREATE TABLE IF NOT EXISTS padron(id int primary key unique, numloc text, contrato text, nombre text, direccion text, nummed text, tarifa text, lecant text, modif text)");
        BaseDeDatos.execSQL("CREATE TABLE IF NOT EXISTS lectura(contrato text primary key unique, lectura text, anomalia text)");
    }
}
