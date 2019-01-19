package tasneem.kurraz.com.capstone_stage2.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import tasneem.kurraz.com.capstone_stage2.Models.Catagory;
import tasneem.kurraz.com.capstone_stage2.R;

public class CatagoryAdapter extends RecyclerView.Adapter<CatagoryAdapter.CatagoryViewHolder> {

    private final Context context;
    private List<Catagory> catagories;

    public CatagoryAdapter(Context context) {
        this.context = context;
    }

    public void addCatagoriesList(List<Catagory> catagories) {
        this.catagories = catagories;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CatagoryViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new CatagoryViewHolder( LayoutInflater.from(context).inflate(R.layout.store_item, viewGroup,false));
    }

    @Override
    public void onBindViewHolder(@NonNull CatagoryViewHolder holder,int postion) {
        holder.catagory_name.setText(catagories.get(postion).getCatagory_name());
        holder.catagory_recycle.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        ProductAdapter productAdapter = new ProductAdapter(context);
        productAdapter.addProductsList(catagories.get(postion).getProducts());
        holder.catagory_recycle.setAdapter(productAdapter);

    }

    @Override
    public int getItemCount() {
        if (catagories == null) return 0;
        return catagories.size();
    }

    class CatagoryViewHolder extends RecyclerView.ViewHolder {

        private final TextView catagory_name;
        private final RecyclerView catagory_recycle;

        CatagoryViewHolder(@NonNull View itemView) {
            super(itemView);
            catagory_name = itemView.findViewById(R.id.catagory_name);
            catagory_recycle = itemView.findViewById(R.id.products_recycle);
        }
    }
}
