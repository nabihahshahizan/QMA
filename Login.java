package com.example.qma;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import api.ApiClient;
import api.ApiInterface;
import login.LoginData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends AppCompatActivity implements View.OnClickListener{

    EditText NRIC, Password;
    Button buttonLogin;
    String nric, password;
    TextView SignUp;
    ApiInterface apiInterface;
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        NRIC = findViewById(R.id.editTextNRIC);
        Password = findViewById(R.id.editTextPassword);
        buttonLogin = findViewById(R.id.buttonLogin);
        buttonLogin.setOnClickListener(this);
        SignUp = findViewById(R.id.textViewAskToSignUp);
        SignUp.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.buttonLogin:
                nric = NRIC.getText().toString();
                password = Password.getText().toString();
                login(nric,password);
                break;

            case R.id.textViewAskToSignUp:
                Intent intent = new Intent(this, SignUp.class);
                startActivity(intent);
                break;
        }
    }

    private void login(String nric, String password) {

        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<login.Login> loginCall = apiInterface.loginResponse(nric, password);
        loginCall.enqueue(new Callback<login.Login>() {
            @Override
            public void onResponse(Call<login.Login> call, Response<login.Login> response) {
                if(response.body() != null && response.isSuccessful() && response.body().isStatus()){

                    sessionManager = new SessionManager(Login.this);
                    LoginData loginData = response.body().getData();
                    sessionManager.createLoginSession(loginData);

                    Toast.makeText(Login.this, response.body().getData().getFullname(), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Login.this, AfterLoginUser.class);
                    startActivity(intent);
                }else {
                    Toast.makeText(Login.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<login.Login> call, Throwable t) {
                Toast.makeText(Login.this,t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
