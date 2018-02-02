package com.technexia.user.myapplication;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;


/**
 * A simple {@link Fragment} subclass.
 */
public class AllUserFragment extends Fragment {

    RecyclerView recyclerView;
    List<User> userList;
    RecyclerAdapter adapter;
    private FirebaseFirestore firebaseFirestore;

    public AllUserFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_all_user, container, false);

        firebaseFirestore = FirebaseFirestore.getInstance();

        userList = new ArrayList<>();
        recyclerView = (RecyclerView) view.findViewById(R.id.all_users);
        adapter = new RecyclerAdapter(container.getContext(),userList);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(container.getContext()));
        recyclerView.setAdapter(adapter);
        return view;


    }

    @Override
    public void onStart() {
        super.onStart();
        firebaseFirestore.collection("Users").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {

                for (DocumentChange doc : documentSnapshots.getDocumentChanges()){
                    if (doc.getType() == DocumentChange.Type.ADDED){
                        String user_id = doc.getDocument().getId();
                        User user = doc.getDocument().toObject(User.class).withId(user_id);
                        userList.add(user);
                        adapter.notifyDataSetChanged();
                    }
                }
            }
        });
    }
}
