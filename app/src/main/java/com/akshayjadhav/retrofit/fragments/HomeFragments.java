package com.akshayjadhav.retrofit.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.akshayjadhav.retrofit.R;
import com.akshayjadhav.retrofit.storage.SharedPreferencesManager;

import java.util.zip.Inflater;

public class HomeFragments extends Fragment {

    private TextView tvEmail , tvName, tvSchool;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.home_fragment,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tvEmail = view.findViewById(R.id.tvEmail);
        tvName = view.findViewById(R.id.tvName);
        tvSchool = view.findViewById(R.id.tvSchool);

        tvEmail.setText(SharedPreferencesManager.getInstance(getActivity()).getUser().getEmail());
        tvName.setText(SharedPreferencesManager.getInstance(getActivity()).getUser().getName());
        tvSchool.setText(SharedPreferencesManager.getInstance(getActivity()).getUser().getSchool());
    }
}
