package com.franmontiel.activitynavigation;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import butterknife.OnClick;

public class Activity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);
        butterknife.ButterKnife.bind(this);
    }

    @OnClick(R.id.toNext)
    public void onClick() {
        NavigationHelper.open(this, Activity3.class, null);
    }
}
