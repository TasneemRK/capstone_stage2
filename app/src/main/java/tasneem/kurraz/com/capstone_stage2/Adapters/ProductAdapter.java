package tasneem.kurraz.com.capstone_stage2.Adapters;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;

import tasneem.kurraz.com.capstone_stage2.Fragments.Store.ProductsDetails;
import tasneem.kurraz.com.capstone_stage2.Helper.Constants;
import tasneem.kurraz.com.capstone_stage2.Helper.FragmentUtils;
import tasneem.kurraz.com.capstone_stage2.Models.Product;
import tasneem.kurraz.com.capstone_stage2.R;
import tasneem.kurraz.com.capstone_stage2.RoomDatabase.ProductViewModel;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    private final Context context;
    private List<Product> mproducts;

    public ProductAdapter(Context context) {
        this.context = context;
    }

    public void addProductsList(List<Product> products) {
        this.mproducts = products;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ProductViewHolder(LayoutInflater.from(context).inflate(R.layout.product_item, viewGroup,false));
    }

    @Override
    public void onBindViewHolder(@NonNull final ProductViewHolder holder, int position) {
        if (mproducts.get(position).isFav()){
            holder.addToFav.setImageDrawable(context.getResources().getDrawable(R.drawable.fav_after));
        }else {
            holder.addToFav.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_favorite_black_24dp));
        }
        Picasso.get().load(mproducts.get(position).getProduct_image()).error(R.drawable.welcome_page).into(holder.product_image);
        holder.product_name.setText(mproducts.get(position).getProduct_name());
        holder.product_price.setText(mproducts.get(position).getProduct_price());
        holder.addToFav.setOnClickListener(view -> {
            Toast.makeText(context, context.getResources().getString(R.string.addToFav), Toast.LENGTH_SHORT).show();
            ProductViewModel model = ViewModelProviders.of((FragmentActivity) context).get(ProductViewModel.class);
            if (mproducts.get(position).isFav()){
                mproducts.get(position).setFav(false);
                model.removeFromFav(mproducts.get(position));
                holder.addToFav.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_favorite_black_24dp));
            }else {
                model.insertProduct(mproducts.get(position));
                mproducts.get(position).setFav(true);
                holder.addToFav.setImageDrawable(context.getResources().getDrawable(R.drawable.fav_after));
            }
        });
    }

    @Override
    public int getItemCount() {
        if (mproducts == null)  return 0;
            return mproducts.size();
    }

    class ProductViewHolder extends RecyclerView.ViewHolder {

        private final ImageView product_image;
        private final TextView product_name;
        private final TextView product_price;
        private final ImageButton addToFav;

        ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            product_image = itemView.findViewById(R.id.product_image);
            product_name = itemView.findViewById(R.id.product_name);
            product_price = itemView.findViewById(R.id.product_price);
            addToFav = itemView.findViewById(R.id.addToFavBtn);
            itemView.setOnClickListener(view -> {
                Fragment fragment  = new ProductsDetails();
                Bundle bundle = new Bundle();
                bundle.putParcelable(Constants.PRODUCT_ID,mproducts.get(getAdapterPosition()));
                fragment.setArguments(bundle);
                FragmentUtils.replaceFragment((FragmentActivity) context, R.id.container,fragment, true);
            });
        }

    }
}
