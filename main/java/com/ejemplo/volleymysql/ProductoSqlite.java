package com.ejemplo.volleymysql;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class ProductoSqlite extends AppCompatActivity {
    Cursor consultaRegistros;
    RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    ProductsAdapter adaptador = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_producto_sqlite);
        recyclerView = findViewById(R.id.recycler);
        recyclerView.setHasFixedSize(true);
        //recyclerView.setLayoutManager(new LinearLayoutManager(this));
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adaptador = new ProductsAdapter(this, listaProductos());
        //mAdapter = new ProductsAdapter(this,getData());
        recyclerView.setAdapter(adaptador);

    }

    public List<Product> listaProductos(){
        ConexionSQliteHelper conn = new ConexionSQliteHelper(this, "productos_db", null, 1);
        SQLiteDatabase db = conn.getReadableDatabase();
        consultaRegistros = db.rawQuery("SELECT * FROM productos", null);
        List<Product> productos  = new ArrayList<>();
        if(consultaRegistros.moveToFirst()){
            do{
                productos.add(new Product(consultaRegistros.getInt(0), consultaRegistros.getString(1),
                        consultaRegistros.getString(2), consultaRegistros.getDouble(3), consultaRegistros.getDouble(4),
                        consultaRegistros.getString(5)));
            }while (consultaRegistros.moveToNext());
        }
        return productos;
    }
    public List<Product> getData() {
        List<Product> productos = new ArrayList<>();
        ConexionSQliteHelper admin = new ConexionSQliteHelper(this,
                "productos_db", null, 1);
        SQLiteDatabase db = admin.getReadableDatabase(); // la conexion se establece de solo lectura
        consultaRegistros = db.rawQuery("SELECT * FROM  articulos",null);
        //array para guardar la info que traemos de la bd en el cursor
        StringBuffer stringBuffer = new StringBuffer();
        Product dataModel = null;

        if (consultaRegistros!= null) {
            while (consultaRegistros.moveToNext()){
                //dataModel= new Product();
                int id = consultaRegistros.getInt(consultaRegistros.
                        getColumnIndex("id_producto"));
                String descripcion = consultaRegistros.getString(consultaRegistros.
                        getColumnIndex("descripcion"));
                String barcode = consultaRegistros.getString(consultaRegistros.
                        getColumnIndex("barcode"));
                double precio= consultaRegistros.getDouble(consultaRegistros.
                        getColumnIndex("porcentaje_utilidad1"));
                double costo= consultaRegistros.getDouble(consultaRegistros.
                        getColumnIndex("costo_promedio"));
                String imagen = consultaRegistros.getString(consultaRegistros.
                        getColumnIndex("image"));

                dataModel.setId(id);
                dataModel.setDescripcion(descripcion);
                dataModel.setPrecio(precio);
                stringBuffer.append(dataModel);
                productos.add(dataModel);
                ProductsAdapter adapter = new ProductsAdapter(this, productos);
                recyclerView.setAdapter(adapter);
            }
            for (Product prod:productos) {
                Log.i("Producto: ",prod.getDescripcion());
            }
        }
        consultaRegistros.close();
        db.close();
        return productos;
    }
}
