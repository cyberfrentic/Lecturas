package com.example.lecturas;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    private EditText tv_usuario, tv_contrasenia;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administracion", null, 1);
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();
        String usua = "admin";
        Cursor fila = BaseDeDatos.rawQuery("select user from usuarios where user =?", new String[] {usua});

        if (!fila.moveToFirst()){
            ContentValues registro = new ContentValues();
            registro.put("user", "admin");
            registro.put("pass", "260182");
            registro.put("nombre", "Hugo Paulino Canul Echazarreta");
            BaseDeDatos.insert("usuarios", null, registro);
            BaseDeDatos.close();
        }

        setContentView(R.layout.activity_login);
        tv_usuario = (EditText)findViewById(R.id.edUser);
        tv_contrasenia = (EditText)findViewById(R.id.et_password);
    }

    //metodo para verificar la existencia de un usuario y hacer loggin
    public void acceder(View view){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administracion", null, 1);
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();

        String usuario = tv_usuario.getText().toString();
        String contra = tv_contrasenia.getText().toString();

        if (!usuario.isEmpty() && !contra.isEmpty()){
            Toast.makeText(this, "Verificando usuario" + usuario, Toast.LENGTH_SHORT).show();
            Cursor fila = BaseDeDatos.rawQuery("select user, pass from usuarios where user=?",new String[] {usuario});

            String u = fila.getString(0);
            String p = fila.getString(1);
            if (usuario.equals(u) && contra.equals(p)){
                Toast.makeText(this, "El usuario "+u+"ha hecho login", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, MainActivity.class);
                BaseDeDatos.close();
                startActivity(intent);
            }

        }else{
            Toast.makeText(this, "Debe llear todos los campos", Toast.LENGTH_LONG).show();
        }
    }

    // metodo para abrir el registro de un usuario
    public void registro(View view){
        Intent intento = new Intent(this, RegisterActivity.class);
        startActivity(intento);
    }
}
