package tasneem.kurraz.com.capstone_stage2.Fragments;


import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import tasneem.kurraz.com.capstone_stage2.Adapters.OrderAdapter;
import tasneem.kurraz.com.capstone_stage2.Helper.CheckInternetConnect;
import tasneem.kurraz.com.capstone_stage2.Helper.Constants;
import tasneem.kurraz.com.capstone_stage2.Models.Order;
import tasneem.kurraz.com.capstone_stage2.Models.Product;
import tasneem.kurraz.com.capstone_stage2.R;


public class OrdersFragment extends Fragment {

    @BindView(R.id.orderRecycle)
    RecyclerView orderRecycle;

    private FirebaseDatabase database;
    private DatabaseReference reference;
    private FirebaseAuth auth;
    private FirebaseUser user;

    private List<Order> orders;
    private OrderAdapter adapter;

    private CheckInternetConnect connect;

    private LinearLayoutManager layoutManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_orders, container, false);
        ButterKnife.bind(this,view);

        connect = new CheckInternetConnect(getContext());
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        database = FirebaseDatabase.getInstance();

        layoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);

        adapter = new OrderAdapter(getContext());
        orders = new ArrayList<>();
        orderRecycle.setAdapter(adapter);
        orderRecycle.setLayoutManager(layoutManager);
        orderRecycle.setHasFixedSize(true);

        if (connect.isOnline()) {
            if (user != null) {
                reference = database.getReference().child(Constants.USERS).child(user.getUid()).child(Constants.ORDERS);
                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                            final String id = postSnapshot.getKey();
                            Order order = new Order();
                            order.setId(id);
                            order.setOrder_date(dataSnapshot.child(id).child(Constants.ORDER_DATE).getValue().toString());
                            order.setDelivery_date(dataSnapshot.child(id).child(Constants.DELIVERY_DATE).getValue().toString());
                            order.setDelivery_time(dataSnapshot.child(id).child(Constants.DELIVERY_TIME).getValue().toString());
                            order.setDelivery_address(dataSnapshot.child(id).child(Constants.DELIVERY_ADDRESS).getValue().toString());
                            order.setTotalPrice(dataSnapshot.child(id).child(Constants.TOTAL_PRICE).getValue().toString());
                            List<Product> products = new ArrayList<>();
                            long prod_num = dataSnapshot.child(id).child(Constants.PRODUCTS).getChildrenCount();
                            for (long i = 0; i < prod_num; i++) {
                                Product product = new Product();
                                product.setProduct_description(dataSnapshot.child(id).child(Constants.PRODUCTS).child(i + "").child(Constants.PRODUCT_DESCRIP).getValue().toString());
                                product.setProduct_image(dataSnapshot.child(id).child(Constants.PRODUCTS).child(i + "").child(Constants.PRODUCT_IMAGE).getValue().toString());
                                product.setProduct_name(dataSnapshot.child(id).child(Constants.PRODUCTS).child(i + "").child(Constants.PRODUCT_NAME).getValue().toString());
                                product.setProduct_price(dataSnapshot.child(id).child(Constants.PRODUCTS).child(i + "").child(Constants.PRODUCT_PRICE).getValue().toString());
                                product.setQuantity(Integer.parseInt(dataSnapshot.child(id).child(Constants.PRODUCTS).child(i + "").child(Constants.PRODUCT_QUANTITY).getValue().toString()));
                                products.add(product);
                            }
                            order.setProducts(products);
                            orders.add(order);
                        }
                        adapter.addOrdersList(orders);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Toast.makeText(getContext(), databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }else {
            Toast.makeText(getContext(), getResources().getString(R.string.fail_internet), Toast.LENGTH_SHORT).show();
        }

        if (savedInstanceState != null){
            Parcelable parcelable = savedInstanceState.getParcelable(Constants.PARCABLE_KEY);
            orderRecycle.getLayoutManager().onRestoreInstanceState(parcelable);
            orderRecycle.getLayoutManager().scrollToPosition(savedInstanceState.getInt(Constants.POSITION));


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
