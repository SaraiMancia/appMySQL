package com.itca.appmysql.ui.qcategorias;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.itca.appmysql.MySingleton;
import com.itca.appmysql.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class QCategorias extends Fragment {
    private Button btnconsulta, btneliminar, btnactu;
    private EditText etid,etestadoc, etnombrec;
    //private TextView ;
    Boolean inputC=false;
    RequestQueue requestQueue;



    public QCategorias() {

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root =  inflater.inflate(R.layout.fragment_q_categorias, container, false);
        btneliminar = root.findViewById(R.id.btneliminar);
        etestadoc = root.findViewById(R.id.etestadoc);
        etnombrec = root.findViewById(R.id.etnombrec);
        etid = root.findViewById(R.id.etid);
        btnconsulta = root.findViewById(R.id.btnconsulta);
        btnactu = root.findViewById(R.id.btnactua);

        btnactu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(getContext(), "Clic en botón Guardar", Toast.LENGTH_SHORT).show();
                //recibirJson(getContext());
                String idcategoria = etid.getText().toString();
                String nombrecat = etnombrec.getText().toString();
                String estado = etestadoc.getText().toString();

                String dato = "";

                if(idcategoria.length() == 0){
                    etid.setError("Campo obligatorio");
                }else if(nombrecat.length()==0){
                    etnombrec.setError("Campo obligatorio");
                }else if(estado.length()==0){
                    dato = "Debe seleccionar una ópcion";
                    Toast.makeText(getContext(), ""+dato, Toast.LENGTH_SHORT).show();
                }else{
                    Actucategoria(getContext(), Integer.parseInt(idcategoria), nombrecat, Integer.parseInt(estado));
                }

            }
        });

        btneliminar.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = etid.getText().toString();
                String dato = "";
                if(id.length() == 0){
                    etid.setError("Campo obligatorio");
                    dato = "Debe seleccionar una ópcion";
                    Toast.makeText(getContext(), ""+dato, Toast.LENGTH_SHORT).show();
                }else{
                    Eliminarcat(getContext(), Integer.parseInt(id));
                }
            }
        }));
        btnconsulta.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //String idcategoria = etid.getText().toString();
                buscarCategoria("https://carlosminerosis11a.000webhostapp.com/ws/buscarCategoriaCodigo.php?id="+etid.getText() + "");

            }
        }));
        return root;
    }

    private void buscarCategoria(String url) {

        JsonArrayRequest jsonArrayRequest=new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null;

                for (int i = 0; i < response.length(); i++) {
                    try {

                        jsonObject = response.getJSONObject(0);
                        etnombrec.setText(jsonObject.getString("nom_categoria"));
                        etestadoc.setText(jsonObject.getString("estado_categoria"));
                    } catch (JSONException e) {
                        Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }) {
            protected Map<String, String> getParams() throws AuthFailureError {
                //En este método se colocan o se setean los valores a recibir por el fichero *.php
                Map<String, String> map = new HashMap<>();
                map.put("Content-Type", "application/json; charset=utf-8");
                map.put("Accept", "application/json");
                // map.put("id", String.valueOf(id_categoria));

                return map;
            }

        };
        // MySingleton.getInstance(context).addToRequestQueue(request);
        requestQueue= Volley.newRequestQueue(getContext());
        requestQueue.add(jsonArrayRequest);
    }





    private void Eliminarcat(final Context context, final int id_categoria) {

        String url = "https://carlosminerosis11a.000webhostapp.com/ws/eliminar.php";
        StringRequest request = new StringRequest(Request.Method.POST,url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(context, response, Toast.LENGTH_SHORT).show();
                JSONObject requestJSON = null;
                try {

                    JSONObject respuestaJSON = new JSONObject(response.toString());
                    String estado = requestJSON.getString("estado");
                    String mensaje = requestJSON.getString("mensaje");






                    if(inputC){
                        Toast.makeText(context, mensaje, Toast.LENGTH_SHORT).show();
                        //Toast.makeText(context, "Registro almacenado en MySQL.", Toast.LENGTH_SHORT).show();
                    }else if(estado.equals("2")){
                        Toast.makeText(context, ""+mensaje, Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(context, "No se puede guardar. \n" +"Intentelo más tarde.", Toast.LENGTH_SHORT).show();
            }
        }) {
            protected Map<String, String> getParams() throws AuthFailureError {
                //En este método se colocan o se setean los valores a recibir por el fichero *.php
                Map<String, String> map = new HashMap<>();
                map.put("Content-Type", "application/json; charset=utf-8");
                map.put("Accept", "application/json");
                map.put("id", String.valueOf(id_categoria));

                return map;
            }
        };
        MySingleton.getInstance(context).addToRequestQueue(request);

    }


    private void Actucategoria(final Context context, final int id_categoria, final String nom_categoria, final int estado_categoria) {
        String url = "https://carlosminerosis11a.000webhostapp.com/ws/actualizar.php";
        StringRequest request = new StringRequest(Request.Method.POST,url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject requestJSON = null;
                try {
                    requestJSON = new JSONObject(response.toString());
                    String estado = requestJSON.getString("estado");
                    String mensaje = requestJSON.getString("mensaje");
                    if(estado.equals("1")){
                        Toast.makeText(context, mensaje, Toast.LENGTH_SHORT).show();
                        //Toast.makeText(context, "Registro almacenado en MySQL.", Toast.LENGTH_SHORT).show();
                    }else if(estado.equals("2")){
                        Toast.makeText(context, ""+mensaje, Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(context, "No se puedo guardar. \n" +"Intentelo más tarde.", Toast.LENGTH_SHORT).show();
            }
        }) {
            protected Map<String, String> getParams() throws AuthFailureError {
                //En este método se colocan o se setean los valores a recibir por el fichero *.php
                Map<String, String> map = new HashMap<>();
                map.put("Content-Type", "application/json; charset=utf-8");
                map.put("Accept", "application/json");
                map.put("id", etid.getText().toString());
                map.put("nombre", etnombrec.getText().toString());
                map.put("estado", etestadoc.getText().toString());
                return map;
            }
        };
        MySingleton.getInstance(context).addToRequestQueue(request);
    }
}