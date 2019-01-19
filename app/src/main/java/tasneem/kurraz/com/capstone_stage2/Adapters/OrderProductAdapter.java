package tasneem.kurraz.com.capstone_stage2.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import tasneem.kurraz.com.capstone_stage2.Models.Product;
import tasneem.kurraz.com.capstone_stage2.R;

public class OrderProductAdapter extends RecyclerView.Adapter<OrderProductAdapter.OrderProductViewHolder>{

    private final Context context;
    private List<Product>products;

    public OrderProductAdapter(Context context) {
        this.context = context;
    }

    public void addProductsList(List<Product>products){
        this.products = products;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public OrderProductViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new OrderProductViewHolder(LayoutInflater.from(context).inflate(R.layout.order_produvt_item,viewGroup,false));
    }

    @SuppressLint("StringFormatInvalid")
    @Override
    public void onBindViewHolder(@NonNull OrderProductViewHolder holder, int position) {
        Picasso.get().load(Uri.parse(products.get(position).getProduct_image())).fit().into(holder.order_product_image);
        holder.order_product_name.setText(products.get(position).getProduct_name());
        holder.order_product_price.setText(String.format(products.get(position).getProduct_price(),context.getResources().getString(R.string.dollar)));
        holder.order_product_quantity.setText(String.format(context.getResources().getString(R.string.quantity),String.valueOf(products.get(position).getQuantity()),context.getResources().getString(R.string.peice)));
    }



    @Override
    public int getItemCount() {
        if (products == null) return 0;
        return products.size();
    }

    public class OrderProductViewHolder extends RecyclerView.ViewHolder {

        private final ImageView order_product_image;
        private final TextView order_product_name;
        private final TextView order_product_price;
        private final TextView order_product_quantity;

        OrderProductViewHolder(@NonNull View itemView) {
            super(itemView);
            order_product_image = itemView.findViewById(R.id.order_product_image);
            order_product_name = itemView.findViewById(R.id.order_product_name);
            order_product_price = itemView.findViewById(R.id.order_product_price);
            order_product_quantity = itemView.findViewById(R.id.order_product_quantity);
        }
    }
}
