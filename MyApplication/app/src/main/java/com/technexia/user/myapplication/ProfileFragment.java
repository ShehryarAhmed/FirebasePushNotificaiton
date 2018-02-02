package com.technexia.user.myapplication;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import de.hdodenhof.circleimageview.CircleImageView;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {

    private Button logout;
    private FirebaseAuth auth;
    private FirebaseFirestore firebaseFirestore;

    private CircleImageView image;
    private TextView mUserName;


    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        auth = FirebaseAuth.getInstance();

        firebaseFirestore = FirebaseFirestore.getInstance();

        image = (CircleImageView) view.findViewById(R.id.profile_dp);
        mUserName = (TextView) view.findViewById(R.id.profile_name);
        logout = (Button) view.findViewById(R.id.logoutBtn);


        firebaseFirestore.collection("Users").document(auth.getCurrentUser().getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
            mUserName.setText(""+documentSnapshot.getString("name"));
                RequestOptions requestOptions = new RequestOptions();
                requestOptions.placeholder(R.mipmap.ic_launcher);
                Glide.with(container.getContext()).setDefaultRequestOptions(requestOptions).load(documentSnapshot.getString("imageUri")).into(image);
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                auth.signOut();
                Intent intent = new Intent(container.getContext(),LoginActivity.class);
                startActivity(intent);

            }
        });

        // Inflate the layout for this fragment
        return view;
    }

}
