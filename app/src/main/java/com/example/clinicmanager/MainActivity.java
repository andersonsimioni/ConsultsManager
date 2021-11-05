package com.example.clinicmanager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fragmentManager = getSupportFragmentManager();

        Button btnMedics = findViewById(R.id.btn_medics);
        btnMedics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentManager.beginTransaction().replace(R.id.content_fragment, new MedicFragment(fragmentManager)).commit();
            }
        });

        Button btnPatients = findViewById(R.id.btn_patients);
        btnPatients.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentManager.beginTransaction().replace(R.id.content_fragment, new PatientFragment(fragmentManager)).commit();
            }
        });

        Button btnConsults = findViewById(R.id.btn_consults);
        btnConsults.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentManager.beginTransaction().replace(R.id.content_fragment, new ConsultFragment(fragmentManager)).commit();
            }
        });



        Button btnRegisterMedic = findViewById(R.id.btnRegisterMedic);
        btnRegisterMedic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentManager.beginTransaction().replace(R.id.content_fragment, new EditMedicFragment(fragmentManager)).commit();
            }
        });

        Button btnRegisterPatient = findViewById(R.id.btnRegisterPatient);
        btnRegisterPatient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentManager.beginTransaction().replace(R.id.content_fragment, new EditPatientFragment(fragmentManager)).commit();
            }
        });

        Button btnRegisterConsult = findViewById(R.id.btnRegisterConsult);
        btnRegisterConsult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentManager.beginTransaction().replace(R.id.content_fragment, new EditConsultFragment(fragmentManager)).commit();
            }
        });
    }
}