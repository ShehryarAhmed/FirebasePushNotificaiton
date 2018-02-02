package com.technexia.user.myapplication;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.telecom.TelecomManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by User on 2/2/2018.
 */

public class SendActivity extends AppCompatActivity {
    private TextView user_id_view;

    private String mUserId;
    private String mUserName;
    private String mCurrentId;

    private EditText mMessageView;
    private Button mSendBtn;
    private ProgressBar mProgessBar;

    private FirebaseFirestore mFirestore;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send);

        mFirestore = FirebaseFirestore.getInstance();
        mCurrentId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        user_id_view = (TextView) findViewById(R.id.user_id_view);
        mMessageView = (EditText) findViewById(R.id.send_message);
        mSendBtn = (Button) findViewById(R.id.send_notification_bnt);
        mProgessBar = (ProgressBar) findViewById(R.id.send_progressBar);

        mUserId = getIntent().getStringExtra("user_id");
        mUserName = getIntent().getStringExtra("user_name");

        user_id_view.setText("send To "+mUserName);

        mSendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mProgessBar.setVisibility(View.VISIBLE);
                String message = mMessageView.getText().toString().trim();
                if (!message.isEmpty()){
                    Map<String,Object> notification = new HashMap<>();
                    notification.put("message",message);
                    notification.put("from",mCurrentId);

                    mFirestore.collection("Users/"+ mUserId + "/Notifications").add(notification).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            mProgessBar.setVisibility(View.INVISIBLE);
                            Toast.makeText(SendActivity.this, "Notification Sending", Toast.LENGTH_SHORT).show();
                            mMessageView.setText("");
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            mProgessBar.setVisibility(View.INVISIBLE);
                            Toast.makeText(SendActivity.this, "Error"+e.getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    });

                }
            }
        });

    }
}
