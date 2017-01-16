package com.franmontiel.pushsample;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;

public class MainActivity extends AppCompatActivity {

    private TextView intentDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        intentDetails = (TextView) findViewById(R.id.intentDetails);

        String token = FirebaseInstanceId.getInstance().getToken();
        Log.d("FCM", "token: " + token);

        FirebaseMessaging.getInstance().subscribeToTopic("push_sample");
        FirebaseMessaging.getInstance().unsubscribeFromTopic("promos_en");
        FirebaseMessaging.getInstance().unsubscribeFromTopic("promos_fr");
        showIntentDetails();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        showIntentDetails();
    }

    private void showIntentDetails() {
        Bundle extras = getIntent().getExtras();
        String intentDetailsLog = "";

        String action = getIntent().getAction();
        if (action != null) {
            intentDetailsLog +=
                    "INTENT ACTION\n" + " - " + action + "\n";
        }

        if (extras != null) {
            intentDetailsLog += "\nINTENT EXTRAS\n";
            for (String key : extras.keySet()) {
                intentDetailsLog += " - " + key + " : " + extras.get(key) + "\n";
            }
        }
        intentDetails.setText(intentDetailsLog);
    }
}
