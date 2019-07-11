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

public class RegisterActivity extends AppCompatActivity {

    private EditText ed_user, ed_password, ed_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        ed_user = (EditText)findViewById(R.id.et_user);
        ed_password = (EditText)findViewById(R.id.et_password);
        ed_name = (EditText)findViewById(R.id.et_nombre);
    }

    public void registrar(View view){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administracion", null, 1);
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();

        String usuario = ed_user.getText().toString();
        String contra = ed_password.getText().toString();
        String nombre = ed_name.getText().toString();

        if (!usuario.isEmpty() && !contra.isEmpty() && !nombre.isEmpty()){
            Toast.makeText(this, "Verificando usuario" + usuario, Toast.LENGTH_SHORT).show();
            Cursor fila = BaseDeDatos.rawQuery("select user from usuarios where user=?",new String[] {usuario});
            if (fila.moveToFirst()){
                Toast.makeText(this, "El usuario "+usuario+"Ya existe cambie los datos", Toast.LENGTH_SHORT).show();
                ed_user.setText("");
                ed_password.setText("");
                ed_name.setText("");
            }else {
                ContentValues registro = new ContentValues();
                registro.put("user", usuario);
                registro.put("pass", contra);
                registro.put("nombre", nombre);
                BaseDeDatos.insert("usuarios", null, registro);
                BaseDeDatos.close();
                Intent intent = new Intent(this, LoginActivity.class);
                BaseDeDatos.close();
                startActivity(intent);
            }
        }else{
            Toast.makeText(this, "Debe llear todos los campos", Toast.LENGTH_LONG).show();
        }
    }
}
