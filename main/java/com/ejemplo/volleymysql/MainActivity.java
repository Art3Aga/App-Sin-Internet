package com.ejemplo.volleymysql;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
   //private static final String IP_SERVER="http://192.168.2.110/gestion/";
   private static  String IP_SERVER="http://apps.fmoues.edu.sv/gestion/";
   //private static final String IP_SERVER="http://192.168.1.7/gestionbd/";
   private static final String ROUTE_SERVER="consultarProd.php";
    // direccion del servidor que da la URL, DESDE DONDE TRAEMOS LA INFORMACION
    private static final String URL_PRODUCTS = IP_SERVER + ROUTE_SERVER;

    List<Product> productList;
    List<ListaProductos> productListLite;
    // recyclerview
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //obtener el recyclerview de xml
        recyclerView = findViewById(R.id.recylcerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                //Snackbar.make(v, "Pasando Datos a Sqlite ...", Snackbar.LENGTH_LONG).show();
                abrirActivity();
                Toast.makeText(getBaseContext(), "Modo Sin Internet Activado ...", Toast.LENGTH_SHORT).show();
                //guardarSqlite();
            }
        });

        //inicializar el array para los productos a traer
        productList = new ArrayList<>();

        //Este metodo  trae el JSON y lo pasa al recyclerview
        loadProducts();
        //Con este metodo guardamos todo el JSON a la tabla SQLITE
        guardarSqlite();
        //guardarSqlite();
        //crearBDSqlite();
    }
    public void abrirActivity(){
        Intent intent = new Intent(this, ProductoSqlite.class);
        startActivity(intent);
        //intent.putExtra("productosLista", productList);
        //intent.putIntegerArrayListExtra("listaProductos", );
    }

    private void crearBDSqlite(){
        //ConexionSQliteHelper conn = new ConexionSQliteHelper(this, "productos_db", null, 1);
        //se abre la base de datos para editarla
        //SQLiteDatabase db = conn.getWritableDatabase();
        //hacer el registro
        //ContentValues values = new ContentValues();
        //values.put("id_producto", "");
    }

    private void loadProducts() {
        /*
         * Crear  un String Request
         * El tipo de peticion es GET definido como primer parametro
         * La URL  es definida como primer parametro
         * Entonces tenemos  un Response Listener y un Error Listener
         * En el response listener obtenemos el  JSON como un String
         * */
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_PRODUCTS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            //convertir  el string a json array object
                            JSONArray array = new JSONArray(response);
                            //iterando el array para cargar
                            for (int i = 0; i < array.length(); i++) {
                                //obtener objeto producto de json array
                                JSONObject product = array.getJSONObject(i);

                                //agregar a la lista
                                productList.add(new Product(
                                        product.getInt("id"),
                                        product.getString("descripcion"),
                                        product.getString("barcode"),
                                        product.getDouble("costo"),
                                        product.getDouble("precio"),
                                        product.getString("image")
                                ));
                            }
                            //crear el  adaptador y asignarlo al  recyclerview
                            ProductsAdapter adapter = new ProductsAdapter(MainActivity.this, productList);
                            recyclerView.setAdapter(adapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getBaseContext(),"No se ha podido conectar",Toast.LENGTH_SHORT).show();
                    }
                });

        //adding our stringrequest to queue
        Volley.newRequestQueue(this).add(stringRequest);
    }

    public void guardarSqlite(){
        ConexionSQliteHelper conn = new ConexionSQliteHelper(this, "productos_db", null, 1);

        for(int i = 0; i < productList.size(); i++){
            //Log.i("Descripcion:  ", productList.get(i).getDescripcion().toString());
            SQLiteDatabase db = conn.getWritableDatabase();
            //hacer el registro
            ContentValues values = new ContentValues();
            values.put("id_producto", productList.get(i).getId());
            values.put("descripcion", productList.get(i).getDescripcion());
            values.put("barcode", productList.get(i).getBarcode());
            values.put("costo_promedio", productList.get(i).getCosto());
            values.put("porcentaje_utilidad1", productList.get(i).getPrecio());
            values.put("image", productList.get(i).getImage());
            db.insert("productos", null, values);
            db.close();
        }
    }
}