package com.franmontiel.commons.screens;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by Francisco J. Montiel on 27/11/15.
 */
public abstract class BaseFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (container == null) {
            // We have different layouts, and in one of them this
            // fragment's containing frame doesn't exist. The fragment
            // may still be created from its saved state, but there is
            // no reason to try to create its getBoundView hierarchy because it
            // won't be displayed. Note this is not needed -- we could
            // just run the code below, where we would create and return
            // the getBoundView hierarchy; it would just never be used.
            return null;
        }
        return inflater.inflate(getContentViewResource(), container, false);
    }

    protected abstract int getContentViewResource();

    protected final BaseActivity getBaseActivity() {
        return (BaseActivity) getActivity();
    }

    protected Context getApplicationContext() {
        return getActivity().getApplicationContext();
    }

    public boolean onBackPressed() {
        boolean eventConsumed = false;
        List<Fragment> fragments = getChildFragmentManager().getFragments();
        if (fragments != null) {
            for (Fragment fragment : fragments) {
                if (fragment instanceof BaseFragment) {
                    eventConsumed = eventConsumed
                            || ((BaseFragment) fragment).onBackPressed();
                }
            }
        }
        return eventConsumed;
    }
}
