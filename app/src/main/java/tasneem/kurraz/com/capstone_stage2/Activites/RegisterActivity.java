package tasneem.kurraz.com.capstone_stage2.Activites;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import tasneem.kurraz.com.capstone_stage2.Helper.CheckInternetConnect;
import tasneem.kurraz.com.capstone_stage2.Helper.Constants;
import tasneem.kurraz.com.capstone_stage2.Helper.Validation;
import tasneem.kurraz.com.capstone_stage2.Models.User;
import tasneem.kurraz.com.capstone_stage2.R;

public class RegisterActivity extends AppCompatActivity {

//    private static final int SELECT_IMAGE = 100 ;
//    String imageUrl;

    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;

    private FirebaseDatabase database;
    private DatabaseReference reference;

    private FirebaseStorage firebaseStorage;
    private StorageReference storageReference;

    @BindView(R.id.userRegUserEmail)
    EditText userEmail;

//    @BindView(R.id.userRegUserImage)
//    CircleImageView userImage;

    @BindView(R.id.userRegUsername)
    EditText userName;

    @BindView(R.id.userRegUserPhone)
    EditText userPhone;

    @BindView(R.id.userRegUserPass)
    EditText userPass;

    @BindView(R.id.userRegUserRePass)
    EditText userRePass;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private Validation validation;
    private CheckInternetConnect connect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        ButterKnife.bind(this);

        validation = new Validation(this);
        connect = new CheckInternetConnect(this);

        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        toolbar.setTitle(getResources().getString(R.string.register));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        // change the color of back arrow in toolbar
        toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);


        firebaseAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();

        storageReference = firebaseStorage.getReference().child(Constants.USERS_IMAGES);

//        userImage.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
//                intent.setType("image/jpeg");
//                intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
//                startActivityForResult(Intent.createChooser(intent, "Complete action using"), SELECT_IMAGE);
//
//            }
//        });

    }

    @OnClick(R.id.userRegBtn)
    void register(){
        if (connect.isOnline()){
        CreateNewUser();
        RegUserInDataBase();
        }else {
            Toast.makeText(this, getResources().getString(R.string.fail_internet), Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(Constants.EMAIL,userEmail.getText().toString());
        outState.putString(Constants.PASS,userPass.getText().toString());
        outState.putString(Constants.NAME,userName.getText().toString());
        outState.putString(Constants.PHONE,userPhone.getText().toString());
    }

    @Override
    protected void onRestoreInstanceState(Bundle state) {
        super.onRestoreInstanceState(state);
        userEmail.setText(state.getString(Constants.EMAIL));
        userPass.setText(state.getString(Constants.PASS));
        userPhone.setText(state.getString(Constants.PHONE));
        userName.setText(state.getString(Constants.NAME));
    }

    private void RegUserInDataBase() {
        if (validation.emailValidation(userEmail) && validation.passWordValidation(userPass)&&validation.userNameValidation(userName)&&validation.empty(userPhone) ) {
            if (firebaseUser != null ) {
                    User user = new User(userEmail.getText().toString(), userName.getText().toString(), userPhone.getText().toString());
                    reference = database.getReference().child(Constants.USERS).child(firebaseUser.getUid());
                    reference.setValue(user).addOnSuccessListener(this, aVoid -> {
                        Toast.makeText(RegisterActivity.this, getResources().getString(R.string.registerSuccess), Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    });
            }else {
                    Toast.makeText(RegisterActivity.this, getResources().getString(R.string.registerFail), Toast.LENGTH_LONG).show();
                }

        }
    }

    private void CreateNewUser() {
        if (validation.emailValidation(userEmail) && validation.passWordValidation(userPass)&&validation.userNameValidation(userName)&&validation.empty(userPhone) ) {
            if (userPass.getText().toString().equals(userRePass.getText().toString())) {
                firebaseAuth.createUserWithEmailAndPassword(userEmail.getText().toString(), userPass.getText().toString()).addOnSuccessListener(this, new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        firebaseUser = authResult.getUser();
                    }
                });
            }else {
                Toast.makeText(this, getResources().getString(R.string.pass_and_repass), Toast.LENGTH_SHORT).show();
            }
        }
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if (requestCode == SELECT_IMAGE){
//            if (resultCode == RESULT_OK){
//                final Uri uri = data.getData();
//                if (uri != null) {
//                    final StorageReference imgReference = storageReference.child(uri.getLastPathSegment());
//                    UploadTask uploadTask = imgReference.putFile(uri);
//
//                    uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
//                        @Override
//                        public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
//                            if (!task.isSuccessful()) {
//                                throw task.getException();
//                            }
//
//                            return imgReference.getDownloadUrl();
//                        }
//                    }).addOnCompleteListener(new OnCompleteListener<Uri>() {
//                        @Override
//                        public void onComplete(@NonNull Task<Uri> task) {
//                            if (task.isSuccessful()) {
//                                Uri taskResult = task.getResult();
//                                imageUrl = Constants.PHOTO_URL + taskResult.getPath();
//                                Picasso.get().load(uri).fit().into(userImage);
//                                Log.d("gggggggggg",imageUrl);
//                            }
//                        }
//                    });
//                }
//
//            }
//        }
//    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item);
    }

}












