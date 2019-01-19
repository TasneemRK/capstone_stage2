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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import tasneem.kurraz.com.capstone_stage2.Adapters.CatagoryAdapter;
import tasneem.kurraz.com.capstone_stage2.Helper.CheckInternetConnect;
import tasneem.kurraz.com.capstone_stage2.Helper.Constants;
import tasneem.kurraz.com.capstone_stage2.Models.Catagory;
import tasneem.kurraz.com.capstone_stage2.Models.Product;
import tasneem.kurraz.com.capstone_stage2.R;

public class StoreFragment extends Fragment {

    @BindView(R.id.catagory_recycle)
    RecyclerView recyclerView;

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference reference;

    private List<Catagory> catagories;

    private CatagoryAdapter adapter;

    private CheckInternetConnect connect;

    private LinearLayoutManager layoutManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_store, container, false);

        ButterKnife.bind(this, view);

        connect = new CheckInternetConnect(getContext());

        catagories = new ArrayList<>();
        adapter = new CatagoryAdapter(getContext());

        layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        if (connect.isOnline()) {
            firebaseDatabase = FirebaseDatabase.getInstance();
            reference = firebaseDatabase.getReference().child(Constants.CATAGORIES);

            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (int i = 0; i < dataSnapshot.getChildrenCount(); i++) {
                        String catagory_name = dataSnapshot.child(Constants.CATAGORY + i).child(Constants.NAME).getValue().toString();
                        List<Product> products = new ArrayList<>();
                        DataSnapshot snapshot = dataSnapshot.child(Constants.CATAGORY + i).child(Constants.PRODUCTS);
                        for (DataSnapshot shot : snapshot.getChildren()) {
                            String id = shot.getKey();
                            Product product = new Product();
                            product.setId(id);
                            product.setProduct_name(dataSnapshot.child(Constants.CATAGORY + i).child(Constants.PRODUCTS).child(id).child(Constants.PRODUCT_NAME).getValue().toString());
                            product.setProduct_price(dataSnapshot.child(Constants.CATAGORY + i).child(Constants.PRODUCTS).child(id).child(Constants.PRODUCT_PRICE).getValue().toString());
                            product.setProduct_description(dataSnapshot.child(Constants.CATAGORY + i).child(Constants.PRODUCTS).child(id).child(Constants.PRODUCT_DESCRIP).getValue().toString());
                            product.setProduct_image(dataSnapshot.child(Constants.CATAGORY + i).child(Constants.PRODUCTS).child(id).child(Constants.PRODUCT_IMAGE).getValue().toString());
                            products.add(product);
                        }
                        Catagory catagory = new Catagory(catagory_name, products);
                        catagories.add(catagory);
                    }
                    adapter.addCatagoriesList(catagories);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }else {
            Toast.makeText(getContext(), getResources().getString(R.string.fail_internet), Toast.LENGTH_SHORT).show();
        }

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

//    private void addNewCatagory() {
//        List<Product> products = new ArrayList<>();
//        products.add(new Product("https://firebasestorage.googleapis.com/v0/b/capstonestage2-bbf0f.appspot.com/o/Products_images%2FFCO-flowers-61.jpg?alt=media&token=492b3315-6e43-4f4e-8742-10c76707b249","Enigmatic 8 Red Roses","39.00","Red Rose is the forever sign of deep, passionate, and long-lasting love. To let your loved ones know about your emotions for him or her, this is the bets gift. We have creatively wrapped a red paper around these 8 red roses and tied with a red Ribbon to add more beauty to this floral arrangement."));
//        products.add(new Product("https://firebasestorage.googleapis.com/v0/b/capstonestage2-bbf0f.appspot.com/o/Products_images%2FFCO-flowers-149.jpg?alt=media&token=f1a788c9-5c23-417b-9731-62e120e3006b","Hold The Joy Of Love","45.00","Some people know only to contribute happiness to others. If you happen to know such a person, show your love, regards, respect, and appreciation through this bouquet which carries the most flawless expression of these feelings. It includes bouquet of 10 yellow asiatic lilies with contrast paper packing and ribbon bow."));
//        products.add(new Product("https://firebasestorage.googleapis.com/v0/b/capstonestage2-bbf0f.appspot.com/o/Products_images%2FFCO-VAL-118.jpg?alt=media&token=b6a7290e-7b42-4381-973f-1548315d30de","Lovely Blooming Bunch Standard","50.00","Your Gift Contains: Bouquet of 2 White Asiatic Lilies, 5 Red Carnations, 5 Pink Roses and Seasonal Fillers. Let gorgeous red carnations share the stage with snowy white asiatic lilies and elegant pink roses in a compact style to create an impressive visual impact. Get a beautiful bunch of 2 white asiatic lilies along with 5 red carnations and 5 pink roses delicately wrapped in red paper packing with a pink ribbon bow, and surprise your friends and family with its stunning style."));
//        products.add(new Product("https://firebasestorage.googleapis.com/v0/b/capstonestage2-bbf0f.appspot.com/o/Products_images%2FFCO-W-33.jpg?alt=media&token=84e4f173-a9be-4253-b557-434fe7f74b87","Exotic Radiance","47.00","bunch of 7 red roses and 14 lilies in red paper packing"));
//        products.add(new Product("https://firebasestorage.googleapis.com/v0/b/capstonestage2-bbf0f.appspot.com/o/Products_images%2FFCO-W-21.jpg?alt=media&token=da384cb7-da97-46ed-b9b9-63ad961e8695","Scarlet Friendship","59.00","15 Pink and White Gerberas in Cellophane packing."));
//        products.add(new Product("https://firebasestorage.googleapis.com/v0/b/capstonestage2-bbf0f.appspot.com/o/Products_images%2FEXF2.jpg?alt=media&token=12379f2f-f02f-40eb-9001-05f22a98775e","Bunch of Love","53.00","A bunch of 5 Birds of Paradise along with seasonal fillers wrapped elegantly in a paper wrapping and bow."));
//        products.add(new Product("https://firebasestorage.googleapis.com/v0/b/capstonestage2-bbf0f.appspot.com/o/Products_images%2FEX0057.jpg?alt=media&token=44f1861e-6860-45a7-9748-2c7fe99b6dfd","Faithfulness","79.00","Gifting Gladiolus is a purifying way to convey strength, faithfulness & honor, they also signifies remembrance. This arrangement includes 12 Red Glades in a classic glass vase."));
////        products.add(new Product("","","",""));
//        reference.child("catagory1").child("name").setValue("Flowers");
//        for (int i = 0 ; i <products.size(); i++){
//            reference.child("catagory1").child("products").push().setValue(products.get(i));
//        }
//    }

}
