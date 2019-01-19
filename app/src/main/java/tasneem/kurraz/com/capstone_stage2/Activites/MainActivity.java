package tasneem.kurraz.com.capstone_stage2.Activites;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import tasneem.kurraz.com.capstone_stage2.Fragments.AboutFragment;
import tasneem.kurraz.com.capstone_stage2.Fragments.FavoriteFragment;
import tasneem.kurraz.com.capstone_stage2.Fragments.HomeFragment;
import tasneem.kurraz.com.capstone_stage2.Fragments.OrdersFragment;
import tasneem.kurraz.com.capstone_stage2.Fragments.ShoppingCartFragment;
import tasneem.kurraz.com.capstone_stage2.Fragments.StoreFragment;
import tasneem.kurraz.com.capstone_stage2.Helper.CheckInternetConnect;
import tasneem.kurraz.com.capstone_stage2.Helper.Constants;
import tasneem.kurraz.com.capstone_stage2.Helper.FragmentUtils;
import tasneem.kurraz.com.capstone_stage2.Models.User;
import tasneem.kurraz.com.capstone_stage2.R;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;

    @BindView(R.id.nav_view)
    NavigationView navigationView;

    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;

    private FirebaseDatabase database;
    private DatabaseReference reference;

    private CheckInternetConnect connect;

    private Fragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        connect = new CheckInternetConnect(this);

        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        setSupportActionBar(toolbar);

        firebaseAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        firebaseUser = firebaseAuth.getCurrentUser();

        if (savedInstanceState == null) {
            fragment = new HomeFragment();
            FragmentUtils.addFragment(this, R.id.container, fragment, false);
        } else {
            fragment = getSupportFragmentManager().getFragment(savedInstanceState, "myFragment");


        }


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        View header = navigationView.getHeaderView(0);
//        final ImageView userImg = header.findViewById(R.id.userImage);
        final TextView userName = header.findViewById(R.id.userName);

        navigationView.setNavigationItemSelectedListener(this);

        if (connect.isOnline()) {
            if (firebaseUser != null) {
                String id = firebaseUser.getUid();
                reference = database.getReference().child(Constants.USERS).child(id);
                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        User user = dataSnapshot.getValue(User.class);
                        userName.setText(user.getUsername());
//                    Picasso.get().load(Uri.parse(user.getImage())).fit().into(userImg);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Toast.makeText(MainActivity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                });

            }
        } else {
            Toast.makeText(this, getResources().getString(R.string.fail_internet), Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id = menuItem.getItemId();
        switch (id) {
            case R.id.home:
                fragment = new HomeFragment();
                FragmentUtils.replaceFragment(this, R.id.container, fragment, false);
                drawer.closeDrawer(GravityCompat.START);
                toolbar.setTitle(getResources().getString(R.string.home));
                break;
            case R.id.store:
                fragment = new StoreFragment();
                FragmentUtils.replaceFragment(this, R.id.container,fragment , false);
                drawer.closeDrawer(GravityCompat.START);
                toolbar.setTitle(getResources().getString(R.string.store));
                break;
            case R.id.fav:
                fragment = new FavoriteFragment();
                FragmentUtils.replaceFragment(this, R.id.container, fragment, false);
                drawer.closeDrawer(GravityCompat.START);
                toolbar.setTitle(getResources().getString(R.string.fav));
                break;
            case R.id.orders:
                if (firebaseUser != null) {
                    fragment = new OrdersFragment();
                    FragmentUtils.replaceFragment(this, R.id.container, fragment, false);
                    toolbar.setTitle(getResources().getString(R.string.orders));
                } else {
                    Intent intent = new Intent(this, LoginActivity.class);
                    startActivity(intent);
                }
                drawer.closeDrawer(GravityCompat.START);
                break;
            case R.id.shopCart:
                if (firebaseUser != null) {
                    fragment = new ShoppingCartFragment();
                    FragmentUtils.replaceFragment(this, R.id.container, fragment, false);
                    toolbar.setTitle(getResources().getString(R.string.shop));
                } else {
                    Intent intent = new Intent(this, LoginActivity.class);
                    startActivity(intent);
                }
                drawer.closeDrawer(GravityCompat.START);

                break;
            case R.id.about:
                fragment = new AboutFragment();
                FragmentUtils.replaceFragment(this, R.id.container, fragment, false);
                drawer.closeDrawer(GravityCompat.START);
                toolbar.setTitle(getResources().getString(R.string.about));
                break;
        }
        return false;
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (fragment != null) {
            if (fragment.isAdded()) {
                getSupportFragmentManager().putFragment(outState, getResources().getString(R.string.frag), fragment);
            }
        }
    }

}