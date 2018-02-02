package com.technexia.user.myapplication;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class RegisterActivity extends AppCompatActivity {
//
    FirebaseAuth mFirebaseAuth;
        private FirebaseDatabase mfirebaseDatabase;
        private DatabaseReference mUserDatabase;
        private StorageReference mStroaoge;
        private FirebaseFirestore firebaseFirestore;

    private int GALLERY_REQUEST = 1;
    private Uri imageUri = null;
    private CircleImageView imageButton;
    private EditText name;
    private EditText email;
    private EditText password;
    private Button mBackToLogin;
    private Button mRegister;
    private ProgressBar mRegisterProgressbar;


    @Override
    protected void onStart() {
        super.onStart();


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mFirebaseAuth = FirebaseAuth.getInstance();
        mUserDatabase = FirebaseDatabase.getInstance().getReference().child("users");
        mStroaoge = FirebaseStorage.getInstance().getReference().child("images");
        firebaseFirestore = FirebaseFirestore.getInstance();

        imageButton = (CircleImageView) findViewById(R.id.profile_image);
        name = (EditText) findViewById(R.id.nameEdt);
        email = (EditText) findViewById(R.id.emailEdt);
        password = (EditText) findViewById(R.id.passwordEdt);
        mBackToLogin = (Button) findViewById(R.id.login_btn);
        mRegister = (Button) findViewById(R.id.reg_btn);
        mRegisterProgressbar = (ProgressBar) findViewById(R.id.register_progress_bar);

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent galleryIntent = new Intent();
                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/jpeg");
                galleryIntent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
                startActivityForResult(Intent.createChooser(galleryIntent, "Complet Action "), GALLERY_REQUEST);
            }
        });


        mBackToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        mRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (imageUri != null){
                    mRegisterProgressbar.setVisibility(View.VISIBLE);
                    final String username = name.getText().toString().trim();
                    final String useremail = email.getText().toString().trim();
                    String userpassword = password.getText().toString().trim();

                    if (!useremail.isEmpty() && !username.isEmpty() && !userpassword.isEmpty()){
                        mFirebaseAuth.createUserWithEmailAndPassword(useremail,userpassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()){
                                    final String userId = mFirebaseAuth.getCurrentUser().getUid();
                                    StorageReference mStorageRef = mStroaoge.child(userId+".jpg");

                                    mStorageRef.putFile(imageUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> uploadTask) {
                                            if (uploadTask.isSuccessful()){
                                                String download = uploadTask.getResult().getDownloadUrl().toString();
                                                HashMap<String,Object> map = new HashMap<>();
                                                map.put("name",username);
                                                map.put("imageUri",download);
                                                firebaseFirestore.collection("Users").document(userId).set(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void aVoid) {
                                                        mRegisterProgressbar.setVisibility(View.GONE);
                                                        sendToMain();
                                                    }
                                                });
                                            }
                                            else {
                                                mRegisterProgressbar.setVisibility(View.GONE);
                                                Toast.makeText(RegisterActivity.this, "Error"+uploadTask.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                                }
                                else {
                                    mRegisterProgressbar.setVisibility(View.GONE);
                                    Toast.makeText(RegisterActivity.this, "Error"+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                }
                else {
                    Toast.makeText(RegisterActivity.this, "Enter Data First", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void sendToMain() {
        Intent intent = new Intent(RegisterActivity.this,MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == GALLERY_REQUEST && resultCode == RESULT_OK) {

            imageUri = data.getData();
            imageButton.setImageURI(imageUri);
        }
    }
}
