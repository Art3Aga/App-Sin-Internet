package  com.ejemplo.volleymysql;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Adaptador
 */

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.ProductViewHolder> {


    private Context mCtx;
    private List<Product> productList;
    //private  String IP_SERVER="http://192.168.2.110/gestion/";
    private  String IP_SERVER="http://apps.fmoues.edu.sv/gestion/";
    //private static final String IP_SERVER="http://192.168.1.7/gestionbd/";

    public ProductsAdapter(Context mCtx, List<Product> productList) {
        this.mCtx = mCtx;
        this.productList = productList;
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.product_list, null);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ProductViewHolder holder, int position) {
        Product product = productList.get(position);

        //cargar imagen
        String string_imagen=product.getImage();
        if (TextUtils.isEmpty( string_imagen)) {
            string_imagen="img/productos/nodisponible.jpg";
        }
        String txt_precio="$ "+String.valueOf(product.getPrecio());
        String ruta_imagen=IP_SERVER+string_imagen;

        Glide.with(mCtx)
                .load(ruta_imagen)
                .into(holder.imageView);

        holder.textViewTitle.setText(product.getDescripcion());
        holder.textViewShortDesc.setText(product.getBarcode());
        holder.textViewRating.setText(String.valueOf(product.getCosto()));
        holder.textViewPrice.setText(txt_precio);
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    class ProductViewHolder extends RecyclerView.ViewHolder {

        TextView textViewTitle, textViewShortDesc, textViewRating, textViewPrice;
        ImageView imageView;

        public ProductViewHolder(View itemView) {
            super(itemView);

            textViewTitle = itemView.findViewById(R.id.textViewTitle);
            textViewShortDesc = itemView.findViewById(R.id.textViewShortDesc);
            textViewRating = itemView.findViewById(R.id.textViewRating);
            textViewPrice = itemView.findViewById(R.id.textViewPrice);
            imageView = itemView.findViewById(R.id.imageView);
        }
    }
}
