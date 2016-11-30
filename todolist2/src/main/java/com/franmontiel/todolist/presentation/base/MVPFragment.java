package com.franmontiel.todolist.presentation.base;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.franmontiel.commons.screens.BaseFragment;

/**
 * Created by Francisco J. Montiel on 30/11/16.
 */

public abstract class MVPFragment<V extends MVPView, P extends MVPPresenter<V>> extends BaseFragment {

    private P presenter;

    public abstract P initalizePresenter();

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        presenter = initalizePresenter();
    }

    public P getPresenter() {
        return presenter;
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.onResume(getViewImp());
    }

    @Override
    public void onPause() {
        super.onPause();
        presenter.onPause(getViewImp());
    }

    private V getViewImp() {
        return (V) this;
    }

}
