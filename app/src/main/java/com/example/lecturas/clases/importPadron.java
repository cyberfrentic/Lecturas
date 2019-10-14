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
import android.widget.EditText;
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

import static com.example.lecturas.clases.Utilidades.IpDeServer;

public class importPadron extends AppCompatDialogFragment implements Response.Listener<JSONObject>, Response.ErrorListener {
    RequestQueue request;
    private ProgressDialog progreso;
    JsonObjectRequest jsonObjectRequest;
    private String sectorText;
    private EditText etSector;
    private importPadronListener listener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.importpadron, null);

        etSector = (EditText) view.findViewById(R.id.etSector);




        builder.setView(view)
                .setTitle("App Movil Comercial")
                .setIcon(R.drawable.lecturas)
                .setPositiveButton("Importar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        sectorText = etSector.getText().toString();
                        request = Volley.newRequestQueue(getActivity());
                        try {
                            cargarWebService();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });

        return builder.create();
    }

    private void cargarWebService() throws JSONException {
        progreso = new ProgressDialog(getActivity());
        progreso.setTitle("App Comercial");
        progreso.setMessage("Conectando...");
        progreso.show();
        JSONObject postparams = new JSONObject();
        postparams.put("sector", sectorText);
        String url="http://"+IpDeServer+":7550/comercial/api/padron/get";
        jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, postparams,  this,this);
        request.add(jsonObjectRequest);
    }


    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText((Context) listener, error.toString(), Toast.LENGTH_SHORT).show();
        Log.i("error", error.toString());
    }

    @Override
    public void onResponse(JSONObject response) {
        JSONArray json = response.optJSONArray("respuesta");
        ContentValues contenedor = new ContentValues();
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper((Context) listener, "administracion", null, 1);
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();

        try {
            Toast.makeText((Context) listener, "Recibiendo Datos", Toast.LENGTH_SHORT).show();
            for(int i=0; i<json.length(); i++) {
                JSONObject jsonObject = null;
                jsonObject = json.getJSONObject(i);
                contenedor.put("cuenta", jsonObject.optString("cuenta"));
                contenedor.put("sb", jsonObject.optString("sb"));
                contenedor.put("sector", jsonObject.optString("sector"));
                contenedor.put("manzana", jsonObject.optString("manzana"));
                contenedor.put("lote", jsonObject.optString("lote"));
                contenedor.put("nombre", jsonObject.optString("nombre"));
                contenedor.put("direccion", jsonObject.optString("direccion"));
                contenedor.put("colonia", jsonObject.optString("colonia"));
                contenedor.put("tarifa", jsonObject.optString("tarifa"));
                BaseDeDatos.insert("padronTotal", null, contenedor);
            }
            BaseDeDatos.close();
            progreso.dismiss();
        } catch (Exception e) {
            e.printStackTrace();
            Log.i("error", response.toString());
            Toast.makeText((Context) listener, "Error "+response, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (importPadronListener) context;
        } catch (ClassCastException e) {
            throw  new ClassCastException(context.toString()+"must implement importPadronListener");
        }
    }

    public interface importPadronListener{
        void applyText2(String Sector);
    }
}
