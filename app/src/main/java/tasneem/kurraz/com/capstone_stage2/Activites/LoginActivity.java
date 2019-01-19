package tasneem.kurraz.com.capstone_stage2.Activites;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import tasneem.kurraz.com.capstone_stage2.Helper.CheckInternetConnect;
import tasneem.kurraz.com.capstone_stage2.Helper.Constants;
import tasneem.kurraz.com.capstone_stage2.Helper.Validation;
import tasneem.kurraz.com.capstone_stage2.R;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;

    @BindView(R.id.userLoginEmail)
    EditText userEmail;

    @BindView(R.id.userLoginPass)
    EditText userPass;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private Validation validation;
    private CheckInternetConnect connect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ButterKnife.bind(this);

        validation = new Validation(this);
        connect = new CheckInternetConnect(this);

        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        toolbar.setTitle(getResources().getString(R.string.login));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        // change the color of back arrow in toolbar
        toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);

        firebaseAuth = FirebaseAuth.getInstance();

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(Constants.EMAIL,userEmail.getText().toString());
        outState.putString(Constants.PASS,userPass.getText().toString());
    }

    @Override
    protected void onRestoreInstanceState(Bundle state) {
        super.onRestoreInstanceState(state);
        userEmail.setText(state.getString(Constants.EMAIL));
        userPass.setText(state.getString(Constants.PASS));
    }



    @OnClick(R.id.userLoginBtn)
    void login() {
        if (validation.emailValidation(userEmail) && validation.passWordValidation(userPass)) {
            if (connect.isOnline()) {
                firebaseAuth.signInWithEmailAndPassword(userEmail.getText().toString(), userPass.getText().toString()).addOnSuccessListener(this, new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        firebaseUser = authResult.getUser();
                        if (firebaseUser != null) {
                            Toast.makeText(LoginActivity.this, getResources().getString(R.string.loginSuccess), Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(LoginActivity.this, getResources().getString(R.string.loginFail), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }else {
                Toast.makeText(this, getResources().getString(R.string.fail_internet), Toast.LENGTH_SHORT).show();
            }
        }
    }

    @OnClick(R.id.userLoginRegBtn)
    void goToReg() {
        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item);
    }

}
