package com.franmontiel.fakethreading;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.innerAsyncTask)
    Button innerAsyncTask;
    @BindView(R.id.outerAsyncTask)
    Button outerAsyncTask;
    @BindView(R.id.asyncTaskWithWeakReference)
    Button asyncTaskWithWeakReference;
    @BindView(R.id.asyncTaskWithBus)
    Button asyncTaskWithBus;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        butterknife.ButterKnife.bind(this);
    }

    @OnClick({
            R.id.innerAsyncTask,
            R.id.outerAsyncTask,
            R.id.asyncTaskWithWeakReference,
            R.id.asyncTaskWithBus,
            R.id.javaThreadkWithBus,
            R.id.intentServicekWithBus
    })
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.innerAsyncTask:
                innerAsyncTask();
                break;
            case R.id.outerAsyncTask:
                outerAsyncTask();
                break;
            case R.id.asyncTaskWithWeakReference:
                outerAsyncTaskWithWeakReference();
                break;
            case R.id.asyncTaskWithBus:
                javaThreadWithBus();
                break;
            case R.id.javaThreadkWithBus:
                javaThreadWithBus();
                break;
            case R.id.intentServicekWithBus:
                intentServiceWithBus();
                break;
        }
    }

    private void innerAsyncTask() {
        progressDialog = ProgressDialog.show(this, null, "Espere...");
        new AsyncTask<Void, Void, Boolean>() {
            @Override
            protected Boolean doInBackground(Void... voids) {

                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                return true;
            }

            @Override
            protected void onPostExecute(Boolean aBoolean) {
                if (progressDialog != null)
                    progressDialog.dismiss();
                Toast.makeText(MainActivity.this, "finished", Toast.LENGTH_SHORT).show();
            }
        }.execute();
    }

    private void outerAsyncTask() {
        progressDialog = ProgressDialog.show(this, null, "Espere...");

        BackgroundThreads.executeAsyncTaskWithCallback(new BackgroundThreads.Callback() {
            @Override
            public void onResult(boolean bool) {
                if (progressDialog != null)
                    progressDialog.dismiss();
                Toast.makeText(MainActivity.this, "finished", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void outerAsyncTaskWithWeakReference() {
        progressDialog = ProgressDialog.show(this, null, "Espere...");

        BackgroundThreads.executeAsyncTaskWithWeakReferencedCallback(new BackgroundThreads.Callback() {
            @Override
            public void onResult(boolean bool) {
                if (progressDialog != null)
                    progressDialog.dismiss();
                Toast.makeText(MainActivity.this, "finished", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void outerAsyncTaskWithBus() {
        progressDialog = ProgressDialog.show(this, null, "Espere...");

        BackgroundThreads.executeAsyncTaskAndUseBusAsCommunication();
    }

    private void javaThreadWithBus() {
        progressDialog = ProgressDialog.show(this, null, "Espere...");
        BackgroundThreads.executeJavaThreadAndUseBusAsCommunication();

    }

    private void intentServiceWithBus(){
        progressDialog = ProgressDialog.show(this, null, "Espere...");

        // Podriamos pasarle datos en el intent
        Intent intent = new Intent(getApplicationContext(),MyIntentService.class);
        startService(intent);
    }

    @Subscribe
    public void onBusResponse(Boolean aBoolean) {
        if (progressDialog != null)
            progressDialog.dismiss();

        Toast.makeText(MainActivity.this, "finished", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        BusProvider.getInstance().register(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        BusProvider.getInstance().unregister(this);
    }


}
