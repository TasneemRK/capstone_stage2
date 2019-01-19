package tasneem.kurraz.com.capstone_stage2.Fragments.Store;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import tasneem.kurraz.com.capstone_stage2.Activites.LoginActivity;
import tasneem.kurraz.com.capstone_stage2.Helper.CheckInternetConnect;
import tasneem.kurraz.com.capstone_stage2.Helper.Constants;
import tasneem.kurraz.com.capstone_stage2.Models.Product;
import tasneem.kurraz.com.capstone_stage2.R;
import tasneem.kurraz.com.capstone_stage2.RoomDatabase.ProductViewModel;

public class ProductsDetails extends Fragment {

    @BindView(R.id.pro_image)
    ImageView imageView;

    @BindView(R.id.pro_name)
    TextView name;

    @BindView(R.id.pro_price)
    TextView price;

    @BindView(R.id.pro_desc)
    TextView descrip;

    @BindView(R.id.pro_quantity)
    ElegantNumberButton numberButton;

    private Product product;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private FirebaseDatabase database;
    private DatabaseReference reference;
    private String id;
    private long num;

    private CheckInternetConnect connect;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.product_details, container,false);
        ButterKnife.bind(this, view);

        connect = new CheckInternetConnect(getContext());

        firebaseAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        firebaseUser = firebaseAuth.getCurrentUser();
        if (firebaseUser != null) {
            id = firebaseUser.getUid();
        }

        Bundle bundle = getArguments();
        if (bundle != null) {
            product = bundle.getParcelable(Constants.PRODUCT_ID);
        }
        Picasso.get().load(Uri.parse(product.getProduct_image())).into(imageView);
        name.setText(product.getProduct_name());
        price.setText(product.getProduct_price());
        descrip.setText(product.getProduct_description());

        if (firebaseUser != null) {
            reference = database.getReference().child(Constants.USERS).child(id).child(Constants.SHOPPING_CART);
            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    num = dataSnapshot.getChildrenCount();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
        return view;
    }

    @OnClick(R.id.addTofavDetails)
    void clickAddToFav() {
        ProductViewModel model = ViewModelProviders.of(this).get(ProductViewModel.class);
        if (product.isFav()) {
            product.setFav(false);
            model.removeFromFav(product);
        } else {
            model.insertProduct(product);
            product.setFav(true);
        }
    }

    @OnClick(R.id.addToCartDetails)
    void AddToCartDetails() {
        if (connect.isOnline()) {
            if (firebaseUser != null) {
                product.setQuantity(Integer.parseInt(numberButton.getNumber()));
                reference.child(product.getId()).setValue(product).addOnSuccessListener(getActivity(), new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(getContext(), getResources().getString(R.string.addToCartSuccess), Toast.LENGTH_SHORT).show();
                    }
                });
            } else {
                Intent intent = new Intent(getContext(), LoginActivity.class);
                startActivity(intent);
            }
        } else {
            Toast.makeText(getContext(), getResources().getString(R.string.fail_internet), Toast.LENGTH_SHORT).show();
        }

    }
}
