package com.example.qma;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

public class AfterLoginUser extends AppCompatActivity {

    Button profile, countdown, checkup;
    String nric, fullname, no_phone, quarantine_application, covid_status, track_from, track_arrival;
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_login_user);

        sessionManager = new SessionManager(AfterLoginUser.this);
        if (!sessionManager.isLoggedIn()){
            moveToLogin();
        }

        profile = findViewById(R.id.buttonProfile);
        countdown = findViewById(R.id.buttonCountdown);
        checkup = findViewById(R.id.buttonCheckup);

        nric = sessionManager.getUserDetail().get(SessionManager.NRIC);
        fullname = sessionManager.getUserDetail().get(SessionManager.FULLNAME);
        no_phone = sessionManager.getUserDetail().get(SessionManager.NO_PHONE);
        quarantine_application = sessionManager.getUserDetail().get(SessionManager.QUARANTINE_LOCATION);
        covid_status = sessionManager.getUserDetail().get(SessionManager.COVID_STATUS);
        track_from = sessionManager.getUserDetail().get(SessionManager.TRACK_FROM);
        track_arrival = sessionManager.getUserDetail().get(SessionManager.TRACK_ARRIVAL);

    }

    private void moveToLogin() {
        Intent intent = new Intent(AfterLoginUser.this, Login.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);
        finish();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.actionLogout:
                sessionManager.logoutSession();
                moveToLogin();
                        break;
        }
        return super.onOptionsItemSelected(item);
    }
}
