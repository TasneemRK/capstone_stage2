package tasneem.kurraz.com.capstone_stage2.Adapters;

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
import android.widget.TextView;

import java.util.List;

import tasneem.kurraz.com.capstone_stage2.Fragments.Orders.OrderDetailsFragment;
import tasneem.kurraz.com.capstone_stage2.Helper.Constants;
import tasneem.kurraz.com.capstone_stage2.Helper.FragmentUtils;
import tasneem.kurraz.com.capstone_stage2.Models.Order;
import tasneem.kurraz.com.capstone_stage2.R;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder>{

    private final Context context;
    private List<Order> orders;

    public OrderAdapter(Context context) {
        this.context = context;
    }

    public void addOrdersList(List<Order>orders){
        this.orders = orders;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new OrderViewHolder(LayoutInflater.from(context).inflate(R.layout.order_item,viewGroup,false));
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int i) {
        holder.order_date.setText(orders.get(i).getOrder_date());
        holder.order_delivery_date.setText(orders.get(i).getDelivery_date());
        holder.order_price.setText(orders.get(i).getTotalPrice());
        holder.more.setOnClickListener(view -> {
            Fragment fragment = new OrderDetailsFragment();
            Bundle bundle = new Bundle();
            bundle.putParcelable(Constants.ORDERS_DETAILS,orders.get(holder.getAdapterPosition()));
            fragment.setArguments(bundle);
            FragmentUtils.replaceFragment((FragmentActivity) context,R.id.container,fragment,true);
        });
    }

    @Override
    public int getItemCount() {
        if (orders == null) return 0;
        return orders.size();
    }

    public class OrderViewHolder extends RecyclerView.ViewHolder {

        private final TextView order_date;
        private final TextView order_delivery_date;
        private final TextView order_price;
        private final ImageButton more;

        OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            order_date = itemView.findViewById(R.id.order_date);
            order_delivery_date = itemView.findViewById(R.id.order_deliveryDate);
            order_price = itemView.findViewById(R.id.order_price);
            more = itemView.findViewById(R.id.order_more_btn);
        }
    }
}
