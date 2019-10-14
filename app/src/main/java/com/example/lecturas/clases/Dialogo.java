package com.example.lecturas.clases;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.lecturas.AdminSQLiteOpenHelper;
import com.example.lecturas.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;

import static com.example.lecturas.clases.Utilidades.IpDeServer;


public class Dialogo extends AppCompatDialogFragment implements Response.Listener<JSONObject>, Response.ErrorListener {
    private TextView sector, ruta;
    private DialogoListener listener;
    private ProgressDialog progreso;
    private String sectorText, rutaText;
    RequestQueue request;
    JsonObjectRequest jsonObjectRequest;
    private Calendar fecha = Calendar.getInstance();
    private int mes = fecha.get(Calendar.MONTH) + 1;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();

        View vista = inflater.inflate(R.layout.fragment_new_import, null);

        sector = (TextView) vista.findViewById(R.id.sectorLectura);
        ruta = (TextView) vista.findViewById(R.id.rutaLectura);

        builder.setTitle("Importar rutas de Lecturas");
        builder.setView(vista);
        builder.setPositiveButton("Importar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                sectorText = sector.getText().toString();
                rutaText = ruta.getText().toString();
                request = Volley.newRequestQueue(getActivity());
                try {
                    cargarWebService2();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    listener.applyTexts(sectorText, rutaText);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                dialog.cancel();
            }
        });
        builder.setView(vista)
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        return builder.create();
    }

    private void cargarWebService2() throws JSONException {
        progreso = new ProgressDialog(getActivity());
        progreso.setTitle("App Comercial");
        progreso.setMessage("Conectando...");
        progreso.show();
        JSONObject postparams = new JSONObject();
        postparams.put("sector", sectorText);
        postparams.put("ruta", rutaText);
        String url="http://"+IpDeServer+":7550/comercial/api/listaMedidores/get";
        jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, postparams,  this,this);
        request.add(jsonObjectRequest);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (DialogoListener) context;
        } catch (ClassCastException e) {
            throw  new ClassCastException(context.toString()+"must implement ExampleDialogListener");
        }
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText((Context) listener, error.toString(), Toast.LENGTH_SHORT).show();
        Log.i("error", error.toString());
        progreso.dismiss();
    }

    @Override
    public void onResponse(JSONObject response) {
        JSONArray json = response.optJSONArray("lista");
        ContentValues contenedor = new ContentValues();
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper((Context) listener, "administracion", null, 1);
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();
        try {
            Toast.makeText((Context) listener, "Recibiendo Datos", Toast.LENGTH_SHORT).show();
            for(int i=0; i<json.length(); i++){
                JSONObject jsonObject = null;
                jsonObject = json.getJSONObject(i);
                contenedor.put("id", jsonObject.optString("id"));
                contenedor.put("numloc", jsonObject.optString("locali"));
                contenedor.put("contrato", jsonObject.optString("cuenta"));
                contenedor.put("nombre", jsonObject.optString("nombre"));
                contenedor.put("direccion", jsonObject.optString("direccion"));
                contenedor.put("nummed", jsonObject.optString("medidor"));
                contenedor.put("tarifa", jsonObject.optString("tarifa"));
                contenedor.put("modif", "0");
                BaseDeDatos.insert("padron", null, contenedor);
            }
            BaseDeDatos.close();
        } catch (Exception e) {
            e.printStackTrace();
            Log.i("error", response.toString());
            Toast.makeText((Context) listener, "Error "+response, Toast.LENGTH_SHORT).show();
        }
        progreso.dismiss();
    }

    public interface DialogoListener{
        void applyTexts(String sector, String ruta) throws JSONException;
    }

}
