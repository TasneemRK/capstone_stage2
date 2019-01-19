package tasneem.kurraz.com.capstone_stage2.Fragments.Shopping;


import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import tasneem.kurraz.com.capstone_stage2.Helper.CheckInternetConnect;
import tasneem.kurraz.com.capstone_stage2.Helper.Constants;
import tasneem.kurraz.com.capstone_stage2.Helper.Validation;
import tasneem.kurraz.com.capstone_stage2.Models.Order;
import tasneem.kurraz.com.capstone_stage2.Models.Product;
import tasneem.kurraz.com.capstone_stage2.R;

public class ShoppingDetailsFragment extends Fragment {

    @BindView(R.id.shop_order_phone)
    EditText phone;

    @BindView(R.id.shop_order_address)
    EditText address;

    @BindView(R.id.shop_order_DeliveryDate)
    TextView date;

    @BindView(R.id.shop_order_DeliveryTime)
    TextView time;

    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    FirebaseDatabase database;
    DatabaseReference reference;

    ArrayList<Product> products;
    double totalPrice;

    Validation validation;
    CheckInternetConnect connect;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_shopping_details, container, false);

        ButterKnife.bind(this, view);

        validation = new Validation(getContext());
        connect = new CheckInternetConnect(getContext());

        Bundle bundle = getArguments();
        if (bundle != null) {
            address.setText(bundle.getString(Constants.ADDRESS));
            totalPrice = bundle.getDouble(Constants.TOTAL_PRICE);
            products = bundle.getParcelableArrayList(Constants.SHOP_LIST);
        }

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDate();
            }
        });

        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getTime();

            }
        });

        firebaseAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        firebaseUser = firebaseAuth.getCurrentUser();
        if (firebaseUser != null) {
            if (connect.isOnline()) {
                reference = database.getReference().child(Constants.USERS).child(firebaseUser.getUid());
                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        phone.setText(dataSnapshot.child(Constants.PHONE).getValue().toString());
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            } else {
                Toast.makeText(getContext(), getResources().getString(R.string.fail_internet), Toast.LENGTH_SHORT).show();
            }
        }

        if (savedInstanceState != null){
            date.setText(savedInstanceState.getString(Constants.DELIVERY_DATE));
            time.setText(savedInstanceState.getString(Constants.DELIVERY_TIME));
            phone.setText(savedInstanceState.getString(Constants.PHONE));
            address.setText(savedInstanceState.getString(Constants.ADDRESS));
        }

        return view;
    }

    private void getDate() {
        final Calendar calendar = Calendar.getInstance();
        final int day = calendar.get(Calendar.DAY_OF_MONTH);
        final int month = calendar.get(Calendar.MONTH);
        final int year = calendar.get(Calendar.YEAR);

        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year1, int month1, int day1) {
                date.setText(day1 + "/" + (month1 + 1) + "/" + year1);
            }
        }, day, month, year);
        datePickerDialog.show();
    }

    private void getTime() {
        Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);
        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                time.setText(selectedHour + ":" + selectedMinute);
            }
        }, hour, minute, true);//Yes 24 hour time
        mTimePicker.setTitle(getContext().getResources().getString(R.string.selectTime));
        mTimePicker.show();
    }

    @OnClick(R.id.shop_buyNow)
    void shop_next() {
        if (validation.empty(date) && validation.empty(time) && validation.empty(phone) && validation.empty(address)) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setCancelable(false)
                    .setTitle(getContext().getResources().getString(R.string.confirmOrder))
                    .setMessage(getContext().getResources().getString(R.string.orderDialogMessage))
                    .setPositiveButton(getContext().getString(R.string.confirm), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                            Order order = new Order();
                            order.setDelivery_address(address.getText().toString());
                            order.setDelivery_date(date.getText().toString());
                            order.setDelivery_time(time.getText().toString());
                            order.setOrder_date(Calendar.getInstance().getTime().toString());
                            order.setTotalPrice(totalPrice + "");
                            order.setProducts(products);
                            reference.child(Constants.ORDERS).push().setValue(order).addOnSuccessListener(getActivity(), new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(getContext(), getResources().getString(R.string.addOrderSuccess), Toast.LENGTH_SHORT).show();
                                }
                            });
                            reference.child(Constants.SHOPPING_CART).removeValue(new DatabaseReference.CompletionListener() {
                                @Override
                                public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {

                                }
                            });


                        }
                    })
                    .setNegativeButton(getResources().getString(R.string.cancelDialog), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                        }
                    });

            AlertDialog dialog = builder.create();
            dialog.show();

        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(Constants.DELIVERY_DATE,date.getText().toString());
        outState.putString(Constants.DELIVERY_TIME,time.getText().toString());
        outState.putString(Constants.PHONE,phone.getText().toString());
        outState.putString(Constants.ADDRESS,address.getText().toString());
    }

}
