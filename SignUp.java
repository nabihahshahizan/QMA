    package com.example.qma;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;

import api.ApiClient;
import api.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUp extends AppCompatActivity implements View.OnClickListener {

    EditText NRIC, Password, Fullname, No_Phone, Quarantine_Location, Track_From, Track_Arrival;
    Button buttonSignUp;
    TextView GoBackToLogin;
    String nric, password, fullname, no_phone, quarantine_location, track_from, track_arrival;
    RadioGroup radioGroup;
    RadioButton selectedRadioButton;
    AwesomeValidation awesomevalidation;
    ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        NRIC = findViewById(R.id.editTextNRIC);
        Password = findViewById(R.id.editTextPassword);
        Fullname = findViewById(R.id.editTextFullname);
        No_Phone = findViewById(R.id.editTextNoPhone);
        Quarantine_Location = findViewById(R.id.editTextQuarantineLocation);

        Track_From = findViewById(R.id.editTextTrackFrom);
        Track_Arrival = findViewById(R.id.editTextTrackArrival);

        radioGroup = (RadioGroup) findViewById(R.id.radio_covid_status);

        buttonSignUp = findViewById(R.id.buttonSignUp);
        buttonSignUp.setOnClickListener(this);

        GoBackToLogin = findViewById(R.id.textViewGoBackToLogin);
        GoBackToLogin.setOnClickListener(this);

        awesomevalidation = new AwesomeValidation(ValidationStyle.BASIC);

        awesomevalidation.addValidation(this, R.id.editTextNRIC,
                "[0-9]{12}",R.string.invalid_nric);

        String regexPassword = "(?=.*[a-z])(?=.*[A-Z])(?=.*[~`!@#\\$%\\^&\\*\\(\\)\\-_\\+=\\{\\}\\[\\]\\|\\;:\"<>,./\\?]).{8,}";
        awesomevalidation.addValidation(this, R.id.editTextPassword, regexPassword, R.string.invalid_password);

        awesomevalidation.addValidation(this, R.id.editTextFullname,
                RegexTemplate.NOT_EMPTY,R.string.invalid_fullname);

        awesomevalidation.addValidation(this, R.id.editTextNoPhone,
                "[0]{1}[0-9]{10}", R.string.invalid_number);

        awesomevalidation.addValidation(this, R.id.editTextQuarantineLocation,
                RegexTemplate.NOT_EMPTY,R.string.invalid_quarantinelocation);

        awesomevalidation.addValidation(this, R.id.editTextTrackFrom,
                RegexTemplate.NOT_EMPTY,R.string.invalid_trackfrom);

        awesomevalidation.addValidation(this, R.id.editTextTrackArrival,
                RegexTemplate.NOT_EMPTY,R.string.invalid_trackarrival);

        awesomevalidation.addValidation(this,R.id.radio_covid_status,
                RegexTemplate.NOT_EMPTY, R.string.invalid_covid_status);

        buttonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (awesomevalidation.validate()){
                    Toast.makeText(getApplicationContext()
                            ,"Account has been created ", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext()
                            ,"Please fill in the form", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.buttonSignUp:
                nric = NRIC.getText().toString();
                password = Password.getText().toString();
                fullname = Fullname.getText().toString();
                no_phone = No_Phone.getText().toString();
                quarantine_location = Quarantine_Location.getText().toString();
                track_from = Track_From.getText().toString();
                track_arrival = Track_Arrival.getText().toString();

                selectedRadioButton  = (RadioButton)findViewById(radioGroup.getCheckedRadioButtonId());
                String covid_status = selectedRadioButton.getText().toString();

                signup(nric,password,fullname,no_phone,quarantine_location,covid_status,track_from,track_arrival);
                break;

            case R.id.textViewGoBackToLogin:
                Intent intent = new Intent(this, Login.class);
                startActivity(intent);
                finish();
                break;
        }


    }

    private void signup(String nric, String password, String fullname, String no_phone, String quarantine_location, String covid_status, String track_from, String track_arrival) {

        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<signup.SignUp> call = apiInterface.signupResponse(nric, password, fullname, no_phone, quarantine_location, covid_status, track_from, track_arrival);
        call.enqueue(new Callback<signup.SignUp>() {
            @Override
            public void onResponse(Call<signup.SignUp> call, Response<signup.SignUp> response ) {
                if (response.body() != null && response.isSuccessful() && response.body().isStatus()) {
                    Toast.makeText(SignUp.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(SignUp.this, Login.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(SignUp.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<signup.SignUp> call, Throwable t) {
                Toast.makeText(SignUp.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }
}
