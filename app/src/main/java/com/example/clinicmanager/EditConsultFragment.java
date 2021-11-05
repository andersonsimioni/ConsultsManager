package com.example.clinicmanager;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.graphics.Paint;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import org.w3c.dom.Text;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.EventListener;

public class EditConsultFragment extends Fragment {

    private int consultID;
    private FragmentManager fragmentManager;

    private LocalDateTime initDatetimeToSave;
    private LocalDateTime endDatetimeToSave;

    public EditConsultFragment() {
        // Required empty public constructor
    }

    public EditConsultFragment(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
        this.consultID = 0;
    }

    public EditConsultFragment(FragmentManager fragmentManager, int consultID) {
        this.fragmentManager = fragmentManager;
        this.consultID = consultID;
    }

    private void createSpinners(View view){
        DbContext db = new DbContext(view.getContext());

        Spinner spinner = (Spinner)view.findViewById(R.id.spinnerPatient);
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this.getContext(), android.R.layout.simple_spinner_item);
        for(Patient p:db.getPatients()){
            spinnerArrayAdapter.add(p.getName());
        }
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
        spinner.setAdapter(spinnerArrayAdapter);

        Spinner spinner2 = (Spinner)view.findViewById(R.id.spinnerMedic);
        ArrayAdapter<String> spinnerArrayAdapter2 = new ArrayAdapter<String>(this.getContext(), android.R.layout.simple_spinner_item);
        for(Medic p:db.getMedics()){
            spinnerArrayAdapter2.add(p.getName());
        }
        spinnerArrayAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
        spinner2.setAdapter(spinnerArrayAdapter2);
    }

    private void createInitDateEvent(View view){
        Button button = view.findViewById(R.id.btnEditConsultGetInitDatetime);
        button.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int startYear = calendar.get(Calendar.YEAR);
                int startMonth = calendar.get(Calendar.MONTH);
                int startDay = calendar.get(Calendar.DAY_OF_MONTH);
                int startHour = 0;
                int startMinute = 0;

                if(initDatetimeToSave != null)
                {
                    startYear = initDatetimeToSave.getYear();
                    startMonth = initDatetimeToSave.getMonthValue();
                    startDay = initDatetimeToSave.getDayOfMonth();
                    startHour = initDatetimeToSave.getHour();
                    startMinute = initDatetimeToSave.getMinute();
                }

                int finalStartMinute = startMinute;
                int finalStartHour = startHour;
                DatePickerDialog datePickerDialog = new DatePickerDialog(view.getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        TimePickerDialog timePickerDialog = new TimePickerDialog(view.getContext(), new TimePickerDialog.OnTimeSetListener() {
                            @RequiresApi(api = Build.VERSION_CODES.O)
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                String datetime = dayOfMonth + "/" + month + "/" + year + " " + hourOfDay + ":" + minute;
                                initDatetimeToSave = LocalDateTime.of(year,month,dayOfMonth,hourOfDay,minute);
                            }
                        }, finalStartHour, finalStartMinute, DateFormat.is24HourFormat(view.getContext()));
                        timePickerDialog.show();
                    }
                }, startYear, startMonth, startDay);

                datePickerDialog.show();
            }
        });
    }

    private void createEndDateEvent(View view){
        Button button = view.findViewById(R.id.btnEditConsultGetEndDatetime);
        button.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int startYear = calendar.get(Calendar.YEAR);
                int startMonth = calendar.get(Calendar.MONTH);
                int startDay = calendar.get(Calendar.DAY_OF_MONTH);
                int startHour = 0;
                int startMinute = 0;

                if(endDatetimeToSave != null)
                {
                    startYear = endDatetimeToSave.getYear();
                    startMonth = endDatetimeToSave.getMonthValue();
                    startDay = endDatetimeToSave.getDayOfMonth();
                    startHour = endDatetimeToSave.getHour();
                    startMinute = endDatetimeToSave.getMinute();
                }

                int finalStartMinute = startMinute;
                int finalStartHour = startHour;
                DatePickerDialog datePickerDialog = new DatePickerDialog(view.getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        TimePickerDialog timePickerDialog = new TimePickerDialog(view.getContext(), new TimePickerDialog.OnTimeSetListener() {
                            @RequiresApi(api = Build.VERSION_CODES.O)
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                String datetime = dayOfMonth + "/" + month + "/" + year + " " + hourOfDay + ":" + minute;
                                endDatetimeToSave = LocalDateTime.of(year,month,dayOfMonth,hourOfDay,minute);
                            }
                        }, finalStartHour, finalStartMinute, DateFormat.is24HourFormat(view.getContext()));
                        timePickerDialog.show();
                    }
                }, startYear, startMonth, startDay);

                datePickerDialog.show();
            }
        });
    }

    private void initEditorIfAlreadyHaveID(View view){
        if(this.consultID <= 0)
            return;

        DbContext db = new DbContext(this.getContext());
        Consult c = db.getConsultById(consultID);
        c.loadRels(db);
        initDatetimeToSave = c.getInit();
        endDatetimeToSave = c.getEnd();

        ((TextView)view.findViewById(R.id.txtObservation)).setText(c.getObservation());

        Spinner medicSpinner = (Spinner)view.findViewById(R.id.spinnerMedic);
        Spinner patientSpinner = (Spinner)view.findViewById(R.id.spinnerPatient);

        for (int i = 0; i< medicSpinner.getCount(); i++)
            if(medicSpinner.getItemAtPosition(i).toString() == c.getRel_medic().getName())
                medicSpinner.setSelection(i);

        for (int i = 0; i< patientSpinner.getCount(); i++)
            if(patientSpinner.getItemAtPosition(i).toString() == c.getRel_patient().getName())
                patientSpinner.setSelection(i);
    }

    private Consult getFromGUI(View view){
        String obs = ((TextView)view.findViewById(R.id.txtObservation)).getText().toString();

        DbContext db = new DbContext(view.getContext());
        String pName = ((Spinner)view.findViewById(R.id.spinnerPatient)).getSelectedItem().toString();
        Patient p = db.getPatientByName(pName);
        String mName = ((Spinner)view.findViewById(R.id.spinnerMedic)).getSelectedItem().toString();
        Medic m = db.getMedicByName(mName);

        int pID = p.getId()<=0?0:p.getId();
        int mID = m.getId()<=0?0:m.getId();

        Consult newConsult = new Consult(consultID,pID,mID,initDatetimeToSave,endDatetimeToSave,obs);

        return newConsult;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private boolean isValid(View view, Consult consult){
        try {
            if(consult.getPatient_id() <= 0)
            {
                showErrorMessage(view,"Insira o paciente");
                return false;
            }
            if(consult.getMedic_id() <= 0)
            {
                showErrorMessage(view,"Insira o médico");
                return false;
            }
            if(consult.getInit().isBefore(consult.getEnd()) == false)
            {
                showErrorMessage(view,"Data de início maior que a data final");
                return false;
            }

            if(consult.getId() == 0){
                LocalDateTime now = LocalDateTime.now();
                if(consult.getInit().isBefore(now) || consult.getEnd().isBefore(now))
                {
                    showErrorMessage(view,"Essa data já passou");
                    return false;
                }
            }

            int day = consult.getInit().getDayOfWeek().getValue();
            if(day==0 || day==7)
            {
                showErrorMessage(view,"Não é permitido consutlas fora do expediente");
                return false;
            }

            int initTime = consult.getInit().getHour()*60 + consult.getInit().getMinute();
            int endTime = consult.getEnd().getHour()*60 + consult.getEnd().getMinute();

            if(
             !((initTime>=(8*60) && initTime<=(12*60)) ||
             (endTime>=(8*60) && endTime<=(12*60)) ||
             (initTime>=((13*60)+30) && initTime<=((17*60)+30)) ||
             (endTime>=((13*60)+30) && endTime<=((17*60)+30)))
            ){
                showErrorMessage(view,"Não é permitido consutlas fora do expediente");
                return false;
            }

            DbContext db = new DbContext(view.getContext());
            ArrayList<Consult> medicOtherConsults = db.getConsultsByMedicId(consult.getMedic_id());
            for (Consult c:medicOtherConsults){
                if(c.getId()!=consult.getId()){
                    if((consult.getInit().isAfter(c.getInit()) && consult.getInit().isBefore(c.getEnd()) ||
                       (consult.getEnd().isAfter(c.getInit()) && consult.getEnd().isBefore(c.getEnd()))))
                    {
                        showErrorMessage(view,"Já existe uma consulta para esta data e horário");
                        return false;
                    }
                }
            }

            return  true;
        }catch(IllegalArgumentException ex){
            showErrorMessage(view,"Dados inválidos");
            return false;
        }
    }

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

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void createEvents(View view ){
        createInitDateEvent(view);
        createEndDateEvent(view);
        createSpinners(view);

        view.findViewById(R.id.save_btn).setOnClickListener(v -> {
            Consult newConsult = getFromGUI(view);

            if(isValid(view, newConsult)) {
                DbContext db = new DbContext(view.getContext());
                db.insertOrUpdateConsult(newConsult);
                fragmentManager.beginTransaction().replace(R.id.content_fragment, new ConsultFragment(fragmentManager)).commit();
            }
        });
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_edit_consult, container, false);

        createEvents(view);
        initEditorIfAlreadyHaveID(view);

        return  view;
    }
}