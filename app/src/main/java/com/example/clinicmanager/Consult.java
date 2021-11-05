package com.example.clinicmanager;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Consult {
    private int id;
    private int patient_id;
    private int medic_id;
    private LocalDateTime init;
    private LocalDateTime end;
    private String observation;

    private Medic rel_medic;
    private Patient rel_patient;

    public void loadRels(DbContext context){
        rel_medic = context.getMedicById(this.medic_id);
        rel_patient = context.getPatientById(this.patient_id);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public String toString() {
        String ret = "";
        if(rel_medic != null && rel_patient != null)
            ret = rel_medic.getName() + " | " + rel_patient.getName() + " | ";
        else
            ret = "Consulta as ";

        DateTimeFormatter onlyeDateFormatter = DateTimeFormatter.ofPattern("dd/MM");
        DateTimeFormatter onlyHourFormatter = DateTimeFormatter.ofPattern("HH:mm");
        ret += init.format(onlyeDateFormatter).toString() + " " + init.format(onlyHourFormatter).toString() + "-" + end.format(onlyHourFormatter).toString();

        return ret;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPatient_id() {
        return patient_id;
    }

    public void setPatient_id(int patient_id) {
        this.patient_id = patient_id;
    }

    public int getMedic_id() {
        return medic_id;
    }

    public void setMedic_id(int medic_id) {
        this.medic_id = medic_id;
    }

    public LocalDateTime getInit() {
        return init;
    }

    public void setInit(LocalDateTime init) {
        this.init = init;
    }

    public LocalDateTime getEnd() {
        return end;
    }

    public void setEnd(LocalDateTime end) {
        this.end = end;
    }

    public String getObservation() {
        return observation;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }

    public Medic getRel_medic() {
        return rel_medic;
    }

    public void setRel_medic(Medic rel_medic) {
        this.rel_medic = rel_medic;
    }

    public Patient getRel_patient() {
        return rel_patient;
    }

    public void setRel_patient(Patient rel_patient) {
        this.rel_patient = rel_patient;
    }

    public Consult(int id, int patient_id, int medic_id, LocalDateTime init, LocalDateTime end, String observation) {
        this.id = id;
        this.patient_id = patient_id;
        this.medic_id = medic_id;
        this.init = init;
        this.end = end;
        this.observation = observation;
    }
}
