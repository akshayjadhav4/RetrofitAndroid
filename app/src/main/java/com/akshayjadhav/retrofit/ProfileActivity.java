package com.akshayjadhav.retrofit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.akshayjadhav.retrofit.fragments.HomeFragments;
import com.akshayjadhav.retrofit.fragments.SettingsFragment;
import com.akshayjadhav.retrofit.fragments.UsersFragment;
import com.akshayjadhav.retrofit.models.User;
import com.akshayjadhav.retrofit.storage.SharedPreferencesManager;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ProfileActivity extends AppCompatActivity  implements BottomNavigationView.OnNavigationItemSelectedListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        BottomNavigationView navigationView = findViewById(R.id.bottom_nav);

        navigationView.setOnNavigationItemSelectedListener(this);

        displayFragment(new HomeFragments());
    }

    private  void  displayFragment(Fragment fragment){
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.container,fragment)
                    .commit();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(!SharedPreferencesManager.getInstance(this).isLoggedIn()){
            Intent intent = new Intent(this,LoginActivity.class);
            //close all previous activity
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        Fragment fragment = null;
        switch (menuItem.getItemId()){
            case R.id.menu_home:
                fragment = new HomeFragments();
                break;
            case R.id.menu_users:
                fragment = new UsersFragment();
                break;
            case R.id.menu_settings:
                fragment = new SettingsFragment();
                break;
        }

        if(fragment != null){
            displayFragment(fragment);
        }
        return false;
    }
}
