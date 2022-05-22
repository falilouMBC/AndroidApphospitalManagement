package com.example.tawfekh;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;


public class InscriptionFragment extends Fragment {
    private EditText txtFirstName, txtLastName, txtAge, txtLogin, txtPassword;
    private RadioButton rdGender;
    private Button btnSignUp;
    private String firstName, lastName, age, login, password, sex;
    private int selectedId;
    private RadioGroup radioGroup;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        View view = inflater.inflate(R.layout.fragment_inscription, container, false);
        
        return view;

    }
}