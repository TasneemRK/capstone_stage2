package tasneem.kurraz.com.capstone_stage2.Fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.smarteist.autoimageslider.SliderLayout;
import com.smarteist.autoimageslider.SliderView;

import butterknife.BindView;
import butterknife.ButterKnife;
import tasneem.kurraz.com.capstone_stage2.Helper.CheckInternetConnect;
import tasneem.kurraz.com.capstone_stage2.Helper.Constants;
import tasneem.kurraz.com.capstone_stage2.R;

public class HomeFragment extends Fragment {


    @BindView(R.id.imageSlider)
    SliderLayout sliderLayout;

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference reference;

    private CheckInternetConnect connect;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, view);

        connect = new CheckInternetConnect(getContext());

        sliderLayout.setIndicatorAnimation(SliderLayout.Animations.FILL); //set indicator animation by using SliderLayout.Animations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        sliderLayout.setScrollTimeInSec(3); //set scroll delay in seconds :

        setSliderViews();

        return view;
    }

    private void setSliderViews() {
        if (connect.isOnline()) {
            firebaseDatabase = FirebaseDatabase.getInstance();
            reference = firebaseDatabase.getReference().child(Constants.ADVERTISMENTS);
            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (int i = 0; i < dataSnapshot.getChildrenCount(); i++) {

                        SliderView sliderView = new SliderView(getContext());
                        sliderView.setImageUrl(dataSnapshot.child(Constants.ADVERTISMENT + i).child(Constants.PHOTO).getValue().toString());
                        sliderView.setImageScaleType(ImageView.ScaleType.FIT_XY);
                        sliderView.setDescription(dataSnapshot.child(Constants.ADVERTISMENT + i).child(Constants.TEXT).getValue().toString());


                        //at last add this view in your layout :
                        sliderLayout.addSliderView(sliderView);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(getContext(), databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(getContext(), getResources().getString(R.string.fail_internet), Toast.LENGTH_SHORT).show();
        }
    }
}
