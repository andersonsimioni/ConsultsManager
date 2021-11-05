package com.example.clinicmanager;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class EditPatientFragment extends Fragment {

    private int patientID;
    private FragmentManager fragmentManager;

    private void showErrorMessage(View view, String message){
        AlertDialog.Builder builder1 = new AlertDialog.Builder(view.getContext());
        builder1.setMessage(message);
        builder1.setCancelable(true);

        builder1.setNegativeButton(
                "Ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();
    }

    private Patient getPatientFromThisValues(View view){
        String name = ((TextView)view.findViewById(R.id.patientName)).getText().toString();
        String phone = ((TextView)view.findViewById(R.id.patientPhone)).getText().toString();
        String fixPhone = ((TextView)view.findViewById(R.id.patientFixPhone)).getText().toString();
        boolean blood = ((Spinner)view.findViewById(R.id.patientGrpBlood)).getSelectedItem().toString() == "Positivo";
        int ID = patientID;

        return new Patient(name,phone,fixPhone,ID, blood);
    }

    public boolean isValid(View view, Patient patient){
        if(patient==null)
            return false;

        if(patient.getName() == null || patient.getName().isEmpty()){
            showErrorMessage(view, "Por favor, preencha o nome");
            return false;
        }
        if(patient.getPhone() == null || patient.getPhone().isEmpty()){
            showErrorMessage(view, "Por favor, preencha o telefone");
            return false;
        }
        if(patient.getFix_phone() == null || patient.getFix_phone().isEmpty()){
            showErrorMessage(view, "Por favor, preencha o telefone fixo");
            return false;
        }

        return true;
    }

    private void createEvents(View view){
        // Selection of the spinner
        Spinner spinner = (Spinner)view.findViewById(R.id.patientGrpBlood);

        // Application of the Array to the Spinner
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this.getContext(), android.R.layout.simple_spinner_item);
        spinnerArrayAdapter.add("Positivo");
        spinnerArrayAdapter.add("Negativo");
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
        spinner.setAdapter(spinnerArrayAdapter);


        ((Button)view.findViewById(R.id.save_btn)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Patient newPatient = getPatientFromThisValues(view);
                if(isValid(view,newPatient)) {
                    DbContext db = new DbContext(getContext());
                    db.insertOrUpdatePatient(newPatient);
                    fragmentManager.beginTransaction().replace(R.id.content_fragment, new PatientFragment(fragmentManager)).commit();
                }
            }
        });
    }

    public EditPatientFragment(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
        this.patientID = 0;
    }

    public EditPatientFragment(FragmentManager fragmentManager, int patientID) {
        this.fragmentManager = fragmentManager;
        this.patientID = patientID;
    }

    private void loadMedic(View view){
        if(this.patientID <= 0)
            return;;

        DbContext db = new DbContext(this.getContext());
        Patient m = db.getPatientById(this.patientID);

        if(m!=null){
            ((TextView)view.findViewById(R.id.patientName)).setText(m.getName());
            //((TextView)view.findViewById(R.id.medicCRM)).setText(m.get());
            ((TextView)view.findViewById(R.id.patientPhone)).setText(m.getPhone());
            ((TextView)view.findViewById(R.id.patientFixPhone)).setText(m.getFix_phone());
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_edit_patient, container, false);

        createEvents(view);
        loadMedic(view);

        return  view;
    }
}