package com.akshayjadhav.retrofit.fragments;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.akshayjadhav.retrofit.API.RetrofitClient;
import com.akshayjadhav.retrofit.LoginActivity;
import com.akshayjadhav.retrofit.MainActivity;
import com.akshayjadhav.retrofit.ProfileActivity;
import com.akshayjadhav.retrofit.R;
import com.akshayjadhav.retrofit.models.DefaultResponse;
import com.akshayjadhav.retrofit.models.LoginResponse;
import com.akshayjadhav.retrofit.models.User;
import com.akshayjadhav.retrofit.storage.SharedPreferencesManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SettingsFragment extends Fragment {

    EditText etEmail , etName, etSchool , etcurrentPassword ,etNewPassword;
    Button updateProfile , savePassword, logout, deleteProfile;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.settings_fragment,container,false);


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        etEmail = view.findViewById(R.id.etEmail);
        etName = view.findViewById(R.id.etName);
        etSchool = view.findViewById(R.id.etSchool);
        etcurrentPassword = view.findViewById(R.id.etcurrentPassword);
        etNewPassword = view.findViewById(R.id.etNewPassword);

        //listener to buttons
       updateProfile =  view.findViewById(R.id.buttonUpdate);
        savePassword =  view.findViewById(R.id.updatePassword);
       logout =  view.findViewById(R.id.btnLogout);
       deleteProfile =  view.findViewById(R.id.deleteProfile);

       updateProfile.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               updateProfile();
           }
       });

       savePassword.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               updatePassword();
           }
       });

       logout.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               userLogout();
           }
       });


       deleteProfile.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               deleteProfile();
           }
       });
    }

    private void updateProfile(){
        String email = etEmail.getText().toString().trim();
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

        User user = SharedPreferencesManager.getInstance(getActivity()).getUser();
        Call<LoginResponse> call = RetrofitClient.getInstance()
                .getApi().updateUser(
                        user.getId(),
                        email,
                        name,
                        school
                );

        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                Toast.makeText(getActivity(),response.body().getMessage(),Toast.LENGTH_SHORT).show();

                if(!response.body().isError()){
                    SharedPreferencesManager.getInstance(getActivity()).saveUser(response.body().getUser());
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {

            }
        });
    }


    private void updatePassword(){
        String currentPassword = etcurrentPassword.getText().toString().trim();
        String newPassword = etNewPassword.getText().toString().trim();

        if (currentPassword.isEmpty()){
            etcurrentPassword.setError("Current Password required.");
            etcurrentPassword.requestFocus();
            return;
        }

        if (newPassword.isEmpty()){
            etNewPassword.setError("New Password required.");
            etNewPassword.requestFocus();
            return;
        }

        if(newPassword.length() < 6){
            etNewPassword.setError("Password should at least 6 character long.");
            etNewPassword.requestFocus();
            return;
        }

        User user = SharedPreferencesManager.getInstance(getActivity()).getUser();

        Call<DefaultResponse> call = RetrofitClient
                .getInstance().getApi().updatePassword(
                currentPassword,
                        newPassword,
                        user.getEmail()
        );

        call.enqueue(new Callback<DefaultResponse>() {
            @Override
            public void onResponse(Call<DefaultResponse> call, Response<DefaultResponse> response) {
                Toast.makeText(getActivity(),response.body().getMessage(),Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<DefaultResponse> call, Throwable t) {

            }
        });
    }


    private void  userLogout(){

        SharedPreferencesManager.getInstance(getActivity()).clear();
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        //close all previous activity
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    private void deleteProfile(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Are you sure?");
        builder.setMessage("Once you delete account you cannot retrive back");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                final User user = SharedPreferencesManager.getInstance(getActivity()).getUser();
                Call<DefaultResponse> call = RetrofitClient.getInstance()
                        .getApi().deleteUser(
                                user.getId()
                        );

                call.enqueue(new Callback<DefaultResponse>() {
                    @Override
                    public void onResponse(Call<DefaultResponse> call, Response<DefaultResponse> response) {

                        if(!response.body().isError()){
                            SharedPreferencesManager.getInstance(getActivity()).clear();
                            Intent intent = new Intent(getActivity(), LoginActivity.class);
                            //close all previous activity
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                        }
                    }

                    @Override
                    public void onFailure(Call<DefaultResponse> call, Throwable t) {

                    }
                });
            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

}
