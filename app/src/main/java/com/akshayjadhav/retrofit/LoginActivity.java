package com.akshayjadhav.retrofit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.akshayjadhav.retrofit.API.RetrofitClient;
import com.akshayjadhav.retrofit.models.LoginResponse;
import com.akshayjadhav.retrofit.storage.SharedPreferencesManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private EditText etEmail , etPassoword;
    private TextView signUp;
    private Button login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etEmail = findViewById(R.id.etEmail);
        etPassoword = findViewById(R.id.etPassword);
        signUp = findViewById(R.id.tvSignUp);
        login = findViewById(R.id.buttonLogin);

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,MainActivity.class));

            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userLogin();
            }
        });
    }

    //check user loggedIn

    @Override
    protected void onStart() {
        super.onStart();
        if(SharedPreferencesManager.getInstance(LoginActivity.this).isLoggedIn()){
            Intent intent = new Intent(LoginActivity.this,ProfileActivity.class);
            //close all previous activity
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }

    public void userLogin(){
        String email = etEmail.getText().toString().trim();
        String password = etPassoword.getText().toString().trim();


        if (email.isEmpty()){
            etEmail.setError("Email is required.");
            etEmail.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            etEmail.setError("Enter a valid email.");
            etEmail.requestFocus();
            return;
        }

        if (password.isEmpty()){
            etPassoword.setError("Password is required.");
            etPassoword.requestFocus();
            return;
        }

        if(password.length() < 6){
            etPassoword.setError("Password should at least 6 character long.");
            etPassoword.requestFocus();
            return;
        }

        Call<LoginResponse> call = RetrofitClient
                .getInstance().getApi().userLogin(email,password);

        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                LoginResponse loginResponse = response.body();
                if(!loginResponse.isError()){
                    //save user open profile
//                    Toast.makeText(LoginActivity.this,loginResponse.getMessage(),Toast.LENGTH_LONG).show();

                    SharedPreferencesManager.getInstance(LoginActivity.this)
                            .saveUser(loginResponse.getUser());

                    Intent intent = new Intent(LoginActivity.this,ProfileActivity.class);
                    //close all previous activity
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);

                }else{
                    Toast.makeText(LoginActivity.this,loginResponse.getMessage(),Toast.LENGTH_LONG).show();

                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {

            }
        });
    }
}
