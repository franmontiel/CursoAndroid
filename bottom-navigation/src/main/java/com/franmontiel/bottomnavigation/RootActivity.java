package com.franmontiel.bottomnavigation;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RootActivity extends AppCompatActivity {

    @BindView(R.id.bottomNavigation)
    BottomNavigationView bottomNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_root);
        ButterKnife.bind(this);

        bottomNavigation.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.itemNumbers:
                                onItemSelected(IncreasingFragment.newInstance(1));
                                break;
                            case R.id.itemLetters:
                                onItemSelected(IncreasingFragment.newInstance('A'));
                                break;
                            case R.id.itemNothing:
                                clearBackStack();
                                break;
                        }
                        return true;
                    }
                });
    }

    private static final String BACK_STACK_ROOT_TAG = "BACK_STACK_ROOT_TAG";

    private void onItemSelected(Fragment fragmentToShow) {
        clearBackStack();

        addFirstFragmentToBackStack(fragmentToShow);
    }

    private void clearBackStack() {

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.popBackStack(BACK_STACK_ROOT_TAG, FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }

    private void addFirstFragmentToBackStack(Fragment fragment) {

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainer, fragment)
                .addToBackStack(BACK_STACK_ROOT_TAG)
                .commit();
    }


}
