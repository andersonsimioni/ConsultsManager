package com.example.clinicmanager;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;


public class EditMedicFragment extends Fragment {
    private int medicID;
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

    private Medic getMedicFromThisValues(View view){
        String name = ((TextView)view.findViewById(R.id.medicName)).getText().toString();
        String phone = ((TextView)view.findViewById(R.id.medicPhone)).getText().toString();
        String fixPhone = ((TextView)view.findViewById(R.id.medicFixPhone)).getText().toString();
        String CRM = ((TextView)view.findViewById(R.id.medicCRM)).getText().toString();
        int ID = this.medicID;

        return new Medic(name,phone,fixPhone,ID,CRM);
    }

    private boolean isValidMedic(View view, Medic medic){
        if(medic == null)
            return false;

        if(medic.getName() == null || medic.getName().isEmpty()){
            showErrorMessage(view, "Preencha o nome do médico");
            return false;
        }
        if(medic.getPhone() == null || medic.getPhone().isEmpty()){
            showErrorMessage(view, "Preencha o telefone do médico");
            return false;
        }
        if(medic.getFix_phone() == null || medic.getFix_phone().isEmpty()){
            showErrorMessage(view, "Preencha o telefone fixo do médico");
            return false;
        }
        if(medic.getCrm() == null || medic.getCrm().isEmpty()){
            showErrorMessage(view, "Preencha o CRM do médico");
            return false;
        }

        DbContext db = new DbContext(view.getContext());
        for(Medic m:db.getMedics())
            if(m.getId()!=medic.getId() && m.getCrm().equals(medic.getCrm())){
                showErrorMessage(view, "CRM já cadastrado!!");
                return false;
            }

        return true;
    }

    private void createEvents(View view){
        ((Button)view.findViewById(R.id.save_btn)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Medic newMedic = getMedicFromThisValues(view);
                if(isValidMedic(view, newMedic)) {
                    DbContext db = new DbContext(getContext());
                    db.insertOrUpdateMedic(newMedic);
                    fragmentManager.beginTransaction().replace(R.id.content_fragment, new MedicFragment(fragmentManager)).commit();
                }
            }
        });
    }

    public EditMedicFragment() {
        // Required empty public constructor
    }

    public EditMedicFragment(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
        this.medicID = 0;
    }

    public EditMedicFragment(FragmentManager fragmentManager, int medicID) {
        this.fragmentManager = fragmentManager;
        this.medicID = medicID;
    }

    private void loadMedic(View view){
        if(this.medicID <= 0)
            return;;

        DbContext db = new DbContext(this.getContext());
        Medic m = db.getMedicById(this.medicID);

        if(m!=null){
            ((TextView)view.findViewById(R.id.medicName)).setText(m.getName());
            ((TextView)view.findViewById(R.id.medicCRM)).setText(m.getCrm());
            ((TextView)view.findViewById(R.id.medicPhone)).setText(m.getPhone());
            ((TextView)view.findViewById(R.id.medicFixPhone)).setText(m.getFix_phone());
        }
    }

    public EditMedicFragment(int medicId) {
        this.medicID = medicId;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_edit_medic, container, false);

        createEvents(view);
        loadMedic(view);

        return view;
    }
}