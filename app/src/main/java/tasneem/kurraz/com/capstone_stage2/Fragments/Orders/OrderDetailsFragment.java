package tasneem.kurraz.com.capstone_stage2.Fragments.Orders;


import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import tasneem.kurraz.com.capstone_stage2.Adapters.OrderProductAdapter;
import tasneem.kurraz.com.capstone_stage2.Helper.Constants;
import tasneem.kurraz.com.capstone_stage2.Models.Order;
import tasneem.kurraz.com.capstone_stage2.R;


public class OrderDetailsFragment extends Fragment {

    @BindView(R.id.order_details_date)
    TextView order_date;

    @BindView(R.id.order_details_deliveryDate)
    TextView delivery_date;

    @BindView(R.id.order_details_deliveryTime)
    TextView delivery_time;

    @BindView(R.id.order_details_deliveryAddress)
    TextView delivery_address;

    @BindView(R.id.order_details_price)
    TextView price;

    @BindView(R.id.order_details_productRecycle)
    RecyclerView recyclerView;

    private LinearLayoutManager layoutManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_order_details, container, false);
        ButterKnife.bind(this,view);

        Bundle bundle = getArguments();
        Order order =  bundle.getParcelable(Constants.ORDERS_DETAILS);
        order_date.setText(order.getOrder_date());
        delivery_date.setText(order.getDelivery_date());
        delivery_time.setText(order.getDelivery_time());
        delivery_address.setText(order.getDelivery_address());
        price.setText(order.getTotalPrice());

        OrderProductAdapter adapter = new OrderProductAdapter(getContext());
        adapter.addProductsList(order.getProducts());

        layoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);

        if (savedInstanceState != null){
            Parcelable parcelable = savedInstanceState.getParcelable(Constants.PARCABLE_KEY);
            recyclerView.getLayoutManager().onRestoreInstanceState(parcelable);
            recyclerView.getLayoutManager().scrollToPosition(savedInstanceState.getInt(Constants.POSITION));


        }

        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(Constants.POSITION,layoutManager.findFirstVisibleItemPosition());
        outState.putParcelable(Constants.PARCABLE_KEY,layoutManager.onSaveInstanceState());
    }

}
