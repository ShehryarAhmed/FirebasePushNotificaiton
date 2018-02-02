package com.technexia.user.myapplication;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    private TextView mProfileTxt;
    private TextView mAllUsers;
    private TextView mNotification;
    private ViewPager mViewPager;
    private PagerViewAdapter mPagerViewAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        auth = FirebaseAuth.getInstance();
//        FirebaseUser user = auth.getCurrentUser();
//
//        if (user == null){
//            Intent intent = new Intent(MainActivity.this,LoginActivity.class);
//            startActivity(intent);
//        }
        mProfileTxt = (TextView) findViewById(R.id.profile);
        mAllUsers = (TextView) findViewById(R.id.all_users);
        mNotification = (TextView) findViewById(R.id.notification);

        mViewPager = (ViewPager) findViewById(R.id.maina_view_pager);
        mPagerViewAdapter = new PagerViewAdapter(getSupportFragmentManager());

        mViewPager.setAdapter(mPagerViewAdapter);

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                changetab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        mProfileTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mViewPager.setCurrentItem(0);
            }
        });

        mAllUsers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mViewPager.setCurrentItem(1);
            }
        });

        mNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mViewPager.setCurrentItem(2);
            }
        });
    }


    private void changetab(int position) {
        if (position == 0){
            mProfileTxt.setTextColor(Color.parseColor("#ffffff"));
            mProfileTxt.setTextSize(22);

            mAllUsers.setTextColor(Color.parseColor("#FFE6E2E2"));
            mAllUsers.setTextSize(16);

            mNotification.setTextColor(Color.parseColor("#FFE6E2E2"));
            mNotification.setTextSize(16);
        }
        if (position == 1){
            mProfileTxt.setTextColor(Color.parseColor("#FFE6E2E2"));
            mProfileTxt.setTextSize(16);

            mAllUsers.setTextColor(Color.parseColor("#ffffff"));
            mAllUsers.setTextSize(22);

            mNotification.setTextColor(Color.parseColor("#FFE6E2E2"));
            mNotification.setTextSize(16);
        }
        if (position == 2){
            mProfileTxt.setTextColor(Color.parseColor("#FFE6E2E2"));
            mProfileTxt.setTextSize(16);

            mAllUsers.setTextColor(Color.parseColor("#FFE6E2E2"));
            mAllUsers.setTextSize(16);

            mNotification.setTextColor(Color.parseColor("#ffffff"));
            mNotification.setTextSize(22);
        }
    }

}
