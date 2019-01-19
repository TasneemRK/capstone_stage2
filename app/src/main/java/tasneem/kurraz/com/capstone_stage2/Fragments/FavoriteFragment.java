package tasneem.kurraz.com.capstone_stage2.Fragments;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import tasneem.kurraz.com.capstone_stage2.Adapters.ProductAdapter;
import tasneem.kurraz.com.capstone_stage2.Helper.Constants;
import tasneem.kurraz.com.capstone_stage2.Helper.ConvertToDataSet;
import tasneem.kurraz.com.capstone_stage2.Models.Product;
import tasneem.kurraz.com.capstone_stage2.R;
import tasneem.kurraz.com.capstone_stage2.RoomDatabase.ProductViewModel;

public class FavoriteFragment extends Fragment {

    @BindView(R.id.favRecycle)
    RecyclerView recyclerView;

    private ProductAdapter adapter;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    private GridLayoutManager layoutManager;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_favorite, container, false);

        ButterKnife.bind(this,view);

        adapter = new ProductAdapter(getContext());

        sharedPreferences = getContext().getSharedPreferences(getString(R.string.SHARED_PREF), Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        ProductViewModel model = ViewModelProviders.of(this).get(ProductViewModel.class);
        model.getProducts().observe(this, new Observer<List<Product>>() {
            @Override
            public void onChanged(@Nullable List<Product> products) {
                adapter.addProductsList(products);

                if (products != null) {
                    Set<String> set = ConvertToDataSet.convertToSet(products);
                    editor.putStringSet(Constants.SHAERS_KEY,set);
                    editor.apply();
                }
            }
        });

        if (getContext().getResources().getBoolean(R.bool.isTablet))
            layoutManager = new GridLayoutManager(getContext(),3);
            layoutManager = new GridLayoutManager(getContext(),2);


        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);
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
