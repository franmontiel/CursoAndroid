package com.franmontiel.commons.screens;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.franmontiel.commons.R;


public abstract class FragmentContainerActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getSupportFragmentManager().findFragmentById(getFragmentContainer()) == null) {
            Fragment fragment = instantiateFragment();
            fragment.setArguments(getIntent().getExtras());
            getSupportFragmentManager().beginTransaction().add(getFragmentContainer(), fragment).commit();
        }
    }

    @Override
    protected final int getContentViewResource() {
        return R.layout.activity_fragment_container;
    }

    protected final int getFragmentContainer() {
        return R.id.root;
    }

    protected abstract Fragment instantiateFragment();

}
