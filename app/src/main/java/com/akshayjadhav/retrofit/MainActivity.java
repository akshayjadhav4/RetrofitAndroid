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
import com.akshayjadhav.retrofit.models.DefaultResponse;
import com.akshayjadhav.retrofit.storage.SharedPreferencesManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private EditText etEmail , etPassoword , etName , etSchool;
    private TextView logIn;
    private Button signUp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etEmail = findViewById(R.id.etEmail);
        etPassoword = findViewById(R.id.etPassword);
        etName = findViewById(R.id.etName);
        etSchool = findViewById(R.id.etSchool);
        logIn = findViewById(R.id.tvLogIn);
        signUp = findViewById(R.id.buttonSignUp);


        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userSignUp();
            }
        });

        logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(MainActivity.this,LoginActivity.class));
            }
        });
    }
    @Override
    protected void onStart() {
        super.onStart();
        if(SharedPreferencesManager.getInstance(this).isLoggedIn()){
            Intent intent = new Intent(MainActivity.this,ProfileActivity.class);
            //close all previous activity
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }
    private void  userSignUp(){
        String email = etEmail.getText().toString().trim();
        String password = etPassoword.getText().toString().trim();
        String name = etName.getText().toString().trim();
        String school = etSchool.getText().toString().trim();

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

        if (name.isEmpty()){
            etName.setError("Name is required.");
            etName.requestFocus();
            return;
        }

        if (school.isEmpty()){
            etSchool.setError("School name is required.");
            etSchool.requestFocus();
            return;
        }

//        User registration using API call
        Call<DefaultResponse> call = RetrofitClient
                .getInstance()
                .getApi()
                .createUser(email,password,name,school);

        call.enqueue(new Callback<DefaultResponse>() {
            @Override
            public void onResponse(Call<DefaultResponse> call, Response<DefaultResponse> response) {
                if(response.code() == 201){
                    DefaultResponse defaultResponse = response.body();
                    Toast.makeText(MainActivity.this,defaultResponse.getMessage(),Toast.LENGTH_SHORT).show();

                }else if(response.code() == 422){
                    Toast.makeText(MainActivity.this,"User already exists..",Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<DefaultResponse> call, Throwable t) {

            }
        });

    }
}
