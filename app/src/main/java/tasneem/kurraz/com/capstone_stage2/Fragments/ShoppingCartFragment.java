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
import android.widget.LinearLayout;
import android.widget.TextView;
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
import butterknife.OnClick;
import tasneem.kurraz.com.capstone_stage2.Adapters.ShoppingAdapter;
import tasneem.kurraz.com.capstone_stage2.Fragments.Shopping.MapFragment;
import tasneem.kurraz.com.capstone_stage2.Helper.CheckInternetConnect;
import tasneem.kurraz.com.capstone_stage2.Helper.Constants;
import tasneem.kurraz.com.capstone_stage2.Helper.FragmentUtils;
import tasneem.kurraz.com.capstone_stage2.Models.Product;
import tasneem.kurraz.com.capstone_stage2.R;

public class ShoppingCartFragment extends Fragment {

    @BindView(R.id.emptyShoppingCart)
    TextView emptyText;

    @BindView(R.id.lin_layout)
    LinearLayout linearLayout;

    @BindView(R.id.shopingCartRecycle)
    RecyclerView shoppingCartRecycle;

    @BindView(R.id.totalPrice)
    TextView tprice;

    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;

    private FirebaseDatabase database;
    private DatabaseReference reference;

    private List<Product> products;

    private CheckInternetConnect connect;

    private double totalPrice = 0.0;
    private LinearLayoutManager layoutManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_shopping_cart, container, false);
        ButterKnife.bind(this, view);

        connect = new CheckInternetConnect(getContext());

        firebaseAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        firebaseUser = firebaseAuth.getCurrentUser();

        products = new ArrayList<>();
        layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        shoppingCartRecycle.setLayoutManager(layoutManager);
        final ShoppingAdapter adapter = new ShoppingAdapter(getContext());
        shoppingCartRecycle.setAdapter(adapter);
        shoppingCartRecycle.setHasFixedSize(true);

        if (connect.isOnline()) {
            if (firebaseUser != null) {
                String id = firebaseUser.getUid();
                reference = database.getReference().child(Constants.USERS).child(id).child(Constants.SHOPPING_CART);
                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            emptyText.setVisibility(View.GONE);
                            linearLayout.setVisibility(View.VISIBLE);
                            products.clear();
                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                String id = snapshot.getKey();
                                Product product = new Product();
                                product.setId(dataSnapshot.child(id).child(Constants.ID).getValue().toString());
                                product.setProduct_name(dataSnapshot.child(id).child(Constants.PRODUCT_NAME).getValue().toString());
                                product.setProduct_image(dataSnapshot.child(id).child(Constants.PRODUCT_IMAGE).getValue().toString());
                                product.setProduct_description(dataSnapshot.child(id).child(Constants.PRODUCT_DESCRIP).getValue().toString());
                                product.setProduct_price(dataSnapshot.child(id).child(Constants.PRODUCT_PRICE).getValue().toString());
                                product.setQuantity(Integer.parseInt(dataSnapshot.child(id).child(Constants.PRODUCT_QUANTITY).getValue().toString()));
                                products.add(product);
                                Double price = Double.parseDouble(product.getProduct_price());
                                int quant = product.getQuantity();
                                totalPrice += price * quant;
                                tprice.setText(totalPrice + getResources().getString(R.string.dollar));
                            }
                            adapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Toast.makeText(getContext(), databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

                adapter.addProductsList(products);
            }
        }else {
            Toast.makeText(getContext(), getResources().getString(R.string.fail_internet), Toast.LENGTH_SHORT).show();
        }


        if (savedInstanceState != null){
            Parcelable parcelable = savedInstanceState.getParcelable(Constants.PARCABLE_KEY);
            shoppingCartRecycle.getLayoutManager().onRestoreInstanceState(parcelable);
            shoppingCartRecycle.getLayoutManager().scrollToPosition(savedInstanceState.getInt(Constants.POSITION));


        }

        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(Constants.POSITION,layoutManager.findFirstVisibleItemPosition());
        outState.putParcelable(Constants.PARCABLE_KEY,layoutManager.onSaveInstanceState());
    }

    @OnClick(R.id.shop_next)
    void buynow() {
        Fragment fragment = new MapFragment();
        Bundle bundle = new Bundle();
        bundle.putDouble(Constants.TOTAL_PRICE,totalPrice);
        bundle.putParcelableArrayList(Constants.SHOP_LIST, (ArrayList<? extends Parcelable>) products);
        fragment.setArguments(bundle);
        FragmentUtils.replaceFragment(getActivity(), R.id.container, fragment, true);
    }

}
