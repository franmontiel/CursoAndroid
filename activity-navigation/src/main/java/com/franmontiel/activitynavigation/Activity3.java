package com.franmontiel.activitynavigation;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import butterknife.OnClick;

public class Activity3 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_3);
        butterknife.ButterKnife.bind(this);
    }

    @OnClick(R.id.toNext)
    public void onClickNext() {
        NavigationHelper.open(this, Activity1.class, Intent.FLAG_ACTIVITY_CLEAR_TOP);
    }

    @OnClick(R.id.toMySelf)
    public void onClickMySelft() {
        NavigationHelper.open(this, Activity3.class, null);
    }

    @OnClick(R.id.clearStack)
    public void onClickClear() {
        NavigationHelper.open(this, Activity0.class, Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Toast.makeText(getApplicationContext(), "New Intent", Toast.LENGTH_SHORT).show();
    }

    // Interfiriendo en el Up y BackStack
    @Override
    public boolean onSupportNavigateUp() {
//        return super.onSupportNavigateUp();
        this.finish();
        return true;
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        NavigationHelper.open(this, Activity0.class, null);
    }
}
