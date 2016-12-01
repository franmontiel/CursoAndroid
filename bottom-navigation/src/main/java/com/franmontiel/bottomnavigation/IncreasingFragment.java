package com.franmontiel.bottomnavigation;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Francisco J. Montiel on 1/12/16.
 */

public class IncreasingFragment extends Fragment {

    private static final String EXTRA_NUMBER = "EXTRA_NUMBER";
    private static final String EXTRA_LETTER = "EXTRA_LETTER";

    public static IncreasingFragment newInstance(int number) {

        Bundle args = new Bundle();
        args.putInt(EXTRA_NUMBER, number);

        IncreasingFragment fragment = new IncreasingFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public static IncreasingFragment newInstance(char letter) {

        Bundle args = new Bundle();
        args.putChar(EXTRA_LETTER, letter);

        IncreasingFragment fragment = new IncreasingFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @BindView(R.id.placeholder)
    TextView placeholder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_increasing, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (getArguments() != null && getArguments().containsKey(EXTRA_NUMBER)) {
            int number = getArguments().getInt(EXTRA_NUMBER);

            placeholder.setText(String.valueOf(number));

            final int nextNumber = ++number;
            getView().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    addFragmentToBackStack(IncreasingFragment.newInstance(nextNumber));
                }
            });
        }

        if (getArguments() != null && getArguments().containsKey(EXTRA_LETTER)) {
            char letter = getArguments().getChar(EXTRA_LETTER);

            placeholder.setText(String.valueOf(letter));

            final char nextLetter = ++letter;
            getView().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    addFragmentToBackStack(IncreasingFragment.newInstance(nextLetter));
                }
            });
        }
    }

    private void addFragmentToBackStack(Fragment fragment) {
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentContainer, fragment)
                .addToBackStack(null)
                .commit();
    }


}
