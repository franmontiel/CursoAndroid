package com.franmontiel.introandroid;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private static final String SAVED_NAME = "SAVED_NAME";
    private static final int REQUEST_SURNAME = 1;

    private EditText nameField;
    private Button sayHi;
    private TextView helloSomeBody;

    private String personToGreet; // Queremos guardar este dato para restaurar el estado del Activity

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate");

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        // Inicialización de las vistas
        nameField = (EditText) findViewById(R.id.nameField);
        sayHi = ((Button) findViewById(R.id.sayHi));
        helloSomeBody = (TextView) findViewById(R.id.helloWorld);

        // Evento click
        sayHi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                personToGreet = nameField.getText().toString();
                showGreetings();
                clearFormFields();
            }
        });

        personToGreet = "";

        // Restauración de estado
        if (savedInstanceState != null && savedInstanceState.containsKey(SAVED_NAME)) {
            personToGreet = savedInstanceState.getString(SAVED_NAME);
            showGreetings();
        }
    }

    // Otra alternativa para restaurar el estado en lugar de en onCreate()
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        Log.d(TAG, "onRestoreInstanceState");

        super.onRestoreInstanceState(savedInstanceState);
    }

    private void showGreetings() {
        if (!personToGreet.trim().isEmpty()) {
            helloSomeBody.setText(getString(R.string.hello_somebody, personToGreet));
        } else {
            Toast.makeText(getBaseContext(), R.string.error_no_name, Toast.LENGTH_SHORT).show();
        }
    }

    private void clearFormFields() {
        nameField.setText("");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.d(TAG, "onCreateOptionsMenu");

        getMenuInflater().inflate(R.menu.main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.infoItem:
                goToInfo();
                break;
            case R.id.surnameItem:
                goToSurnameExpectingResult();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void goToSurnameExpectingResult() {
        Intent intent = new Intent(this, SurnameActivity.class);
        startActivityForResult(intent, REQUEST_SURNAME);
    }

    private void goToInfo() {
        Intent intent = new Intent(this, InfoActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        Log.d(TAG, "onResume");
        super.onResume();
    }

    @Override
    protected void onPause() {
        Log.d(TAG, "onPause");
        super.onPause();
    }

    // Guardado de estado
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        Log.d(TAG, "onSaveInstanceState");

        if (personToGreet != null)
            outState.putString(SAVED_NAME, personToGreet);

        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onDestroy() {
        Log.d(TAG, "onDestroy");
        super.onDestroy();
    }

    // Aquí obtenemos el resultado desde otro Activity, se llama justo antes de onResume()
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d(TAG, "onActivityResult");

        if (requestCode == REQUEST_SURNAME && resultCode == RESULT_OK) {
            String surname = data.getStringExtra(SurnameActivity.RESULT_SURNAME);
            if (!personToGreet.isEmpty() && !surname.isEmpty()) {
                personToGreet += " " + surname;
                showGreetings();
            }
        }
    }
}
