package com.itca.appmysql.ui.productos;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.itca.appmysql.MySingleton;
import com.itca.appmysql.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Productos extends Fragment {

    private EditText sp_fk_categoria, etidproducto,etnombreproducto, etdesproducto, etstock, etprecioproducto, etunidad, estadoproducto, etcategoria;
    private Button btncpro;
    //private Spinner sp_fk_categoria;
    private String datoSelect;

    public Productos() {

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root =  inflater.inflate(R.layout.fragment_productos, container, false);
        etidproducto = root.findViewById(R.id.etidproducto);
        etnombreproducto = root.findViewById(R.id.etnombreproducto);
        etdesproducto = root.findViewById(R.id.etdesproducto);
        etstock = root.findViewById(R.id.etstock);
        etprecioproducto = root.findViewById(R.id.etprecioproducto);
        etunidad = root.findViewById(R.id.etunidad);
        estadoproducto = root.findViewById(R.id.estadoproducto);
        etcategoria = root.findViewById(R.id.sp_fk_categoria);
        //sp_fk_categoria = root.findViewById(R.id.sp_fk_categoria);
        btncpro = root.findViewById(R.id.btncpro);

        btncpro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(getContext(), "Clic en botón Guardar", Toast.LENGTH_SHORT).show();
                //recibirJson(getContext());
                String idproducto = etidproducto.getText().toString();
                String nombre = etnombreproducto.getText().toString();
                String desproducto = etdesproducto.getText().toString();
                String stock = etstock.getText().toString();
                String precio = etprecioproducto.getText().toString();
                String unidad = etunidad.getText().toString();
                String estado = estadoproducto.getText().toString();
                String categoria = etcategoria.getText().toString();

                //String fechapro = fecha.getText().toString();

                String dato = "";

                if(idproducto.length() == 0){
                    etidproducto.setError("Campo obligatorio");
                }else if(nombre.length()==0){
                    etnombreproducto.setError("Campo obligatorio");
                }else if(desproducto.length()==0) {
                    etdesproducto.setError("Campo obligatorio");
                }else if(stock.length()==0) {
                    etstock.setError("Campo obligatorio");
                }else if(precio.length()==0) {
                    etprecioproducto.setError("Campo obligatorio");
                }else if(unidad.length()==0) {
                    etunidad.setError("Campo obligatorio");
                }else if(estado.length()==0) {
                    etunidad.setError("Campo obligatorio");
                } else if(categoria.length()==0) {
                    estadoproducto.setError("Campo obligatorio");
                    dato = "Debe seleccionar una ópcion";
                    Toast.makeText(getContext(), ""+dato, Toast.LENGTH_SHORT).show();
                }
                else {
                    guardarproducto(getContext(), Integer.parseInt(idproducto), nombre, desproducto, Integer.parseInt(stock), Integer.parseInt(precio), unidad, Integer.parseInt(estado), Integer.parseInt(categoria));
                }
            }
        });

        return root;
    }




    private void guardarproducto(final Context context, final int id_producto, final String nom_producto, final String des_producto, final double stock, final double precio, final String unidad_medida, final int estado_producto, final int categoria) {
        String url = "https://manse910.000webhostapp.com/WS/guardarproducto.php";
        StringRequest request = new StringRequest(Request.Method.POST,url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(context, response, Toast.LENGTH_SHORT).show();
                JSONObject requestJSON = null;
                try {
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
                Toast.makeText(context, "No se puede guardar. \n" +"Intentelo más tarde.", Toast.LENGTH_SHORT).show();
            }
        }) {
            protected Map<String, String> getParams() throws AuthFailureError {
                //En este método se colocan o se setean los valores a recibir por el fichero *.php
                Map<String, String> map = new HashMap<>();
                map.put("Content-Type", "application/json; charset=utf-8");
                map.put("Accept", "application/json");
                map.put("id", String.valueOf(id_producto));
                map.put("nombrepro", nom_producto);
                map.put("despro", String.valueOf(des_producto));
                map.put("stock" , String.valueOf(stock));
                map.put("preciopro" , String.valueOf(precio));
                map.put("unidad" , String.valueOf(unidad_medida));
                map.put("estadoproducto" , String.valueOf(estado_producto));
                map.put("categoria" , String.valueOf(categoria));

                return map;
            }
        };
        MySingleton.getInstance(context).addToRequestQueue(request);
    }
    private void ConsultaId(final Context context, final int id_producto, final String nom_producto, final String des_producto, final double stock, final double precio, final String unidad_medida, final int estado_producto, final int categoria) {
        String url = "https://manse910.000webhostapp.com/WS/guardarproducto.php";
        StringRequest request = new StringRequest(Request.Method.POST,url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(context, response, Toast.LENGTH_SHORT).show();
                JSONObject requestJSON = null;
                try {
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
                Toast.makeText(context, "No se puede guardar. \n" +"Intentelo más tarde.", Toast.LENGTH_SHORT).show();
            }
        }) {
            protected Map<String, String> getParams() throws AuthFailureError {
                //En este método se colocan o se setean los valores a recibir por el fichero *.php
                Map<String, String> map = new HashMap<>();
                map.put("Content-Type", "application/json; charset=utf-8");
                map.put("Accept", "application/json");
                map.put("id", String.valueOf(id_producto));
                map.put("nombrepro", nom_producto);
                map.put("despro", String.valueOf(des_producto));
                map.put("stock" , String.valueOf(stock));
                map.put("preciopro" , String.valueOf(precio));
                map.put("unidad" , String.valueOf(unidad_medida));
                map.put("estadoproducto" , String.valueOf(estado_producto));
                map.put("categoria" , String.valueOf(categoria));

                return map;
            }
        };
        MySingleton.getInstance(context).addToRequestQueue(request);
    }
}