package com.franmontiel.introandroid;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by Francisco J. Montiel on 27/11/16.
 */

public class SurnameActivity extends AppCompatActivity {

    public static final String RESULT_SURNAME = "RESULT_SURNAME";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_surname);

        final EditText surname = (EditText) findViewById(R.id.surnameField);
        final Button returnSurname = (Button) findViewById(R.id.returnSurname);

        returnSurname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent resultIntent = new Intent();
                resultIntent.putExtra(RESULT_SURNAME, surname.getText().toString());
                setResult(RESULT_OK, resultIntent);
                finish();
            }
        });
    }
}
