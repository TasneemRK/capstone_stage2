package tasneem.kurraz.com.capstone_stage2.Adapters;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.List;

import tasneem.kurraz.com.capstone_stage2.Helper.Constants;
import tasneem.kurraz.com.capstone_stage2.Models.Product;
import tasneem.kurraz.com.capstone_stage2.R;

public class ShoppingAdapter extends RecyclerView.Adapter<ShoppingAdapter.ShppingViewHolder>{

    private final Context context;
    private List<Product>products;

    private FirebaseUser firebaseUser;
    private DatabaseReference reference;

    public ShoppingAdapter(Context context) {
        this.context = context;
    }

    public void addProductsList(List<Product>products){
        this.products = products;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ShppingViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ShppingViewHolder(LayoutInflater.from(context).inflate(R.layout.shopping_cart_item,viewGroup,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ShppingViewHolder holder, int position) {
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (firebaseUser != null){
            reference = FirebaseDatabase.getInstance().getReference().child(Constants.USERS).child(firebaseUser.getUid())
                    .child(Constants.SHOPPING_CART);

        }

        Picasso.get().load(Uri.parse(products.get(position).getProduct_image())).fit().into(holder.shop_image);
        holder.shop_name.setText(products.get(position).getProduct_name());
        holder.shop_price.setText(products.get(position).getProduct_price()+ context.getResources().getString(R.string.dollar));
        holder.shop_quantity.setText(context.getResources().getString(R.string.quantity) + String.valueOf(products.get(position).getQuantity())+ context.getResources().getString(R.string.peice));
        holder.remove.setOnClickListener(view ->
                reference.child(products.get(position).getId()).removeValue((databaseError, databaseReference) ->
                        Toast.makeText(context, context.getResources().getString(R.string.remove), Toast.LENGTH_SHORT).show()));
    }


    @Override
    public int getItemCount() {
        if (products == null) return 0;
        return products.size();
    }

    public class ShppingViewHolder extends RecyclerView.ViewHolder {

        private final ImageView shop_image;
        private final TextView shop_name;
        private final TextView shop_price;
        private final TextView shop_quantity;
        private final Button remove;

        ShppingViewHolder(@NonNull View itemView) {
            super(itemView);
            shop_image = itemView.findViewById(R.id.shop_image);
            shop_name = itemView.findViewById(R.id.shop_name);
            shop_price = itemView.findViewById(R.id.shop_price);
            shop_quantity = itemView.findViewById(R.id.shop_quantity);
            remove = itemView.findViewById(R.id.shop_remove_btn);
        }
    }
}
