package com.example.lecturas.fragments;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.lecturas.R;

import org.json.JSONException;
import org.json.JSONObject;


public class SendServerFragment extends Fragment implements Response.Listener<JSONObject>, Response.ErrorListener{

    RequestQueue request;
    JsonObjectRequest jsonObjectRequest;
    ProgressDialog progreso;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vista = inflater.inflate(R.layout.fragment_send_server, container, false);

        request = Volley.newRequestQueue(getContext());
        try {
            cargarWebService();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return vista;
    }

    private void cargarWebService() throws JSONException {
        progreso = new ProgressDialog(getContext());
        progreso.setTitle("App Comercial");
        progreso.setMessage("Conectando...");
        progreso.show();
        JSONObject postparams = new JSONObject();
        postparams.accumulate("city", "london");
        postparams.accumulate("timestamp", "1500134255");
        String url="http://192.168.15.45:7500/comercial/api/rest";
        jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, postparams,this,this);
        request.add(jsonObjectRequest);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        progreso.dismiss();
        Toast.makeText(getContext(), "no se pudo conectar"+error.toString(), Toast.LENGTH_LONG).show();
        Log.i("ERROR", error.toString());
    }

    @Override
    public void onResponse(JSONObject response) {
        Toast.makeText(getContext(),"Se logro la conexion" +response.toString(), Toast.LENGTH_LONG).show();
        progreso.dismiss();
    }

}
