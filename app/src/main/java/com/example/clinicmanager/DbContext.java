package com.example.clinicmanager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteCursorDriver;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQuery;
import android.database.sqlite.SQLiteStatement;
import android.os.Build;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * Fiz uma classe baseada no Entity Framework que é o package que uso no trabalho em c#
 */
public class DbContext extends SQLiteOpenHelper {
    private static final String DbName = "consulta.db";
    private static final int DATABASE_VERSION = 1;

    private ArrayList<Medic> Medics;
    private ArrayList<Consult> Consults;
    private ArrayList<Patient> Patients;

    public DbContext(@Nullable Context context) {
        super(context, DbName, null, DATABASE_VERSION);
        refreshContext();
    }

    /**
     * Return new database access object
     * @return
     */
    private SQLiteDatabase startDbConnection(){
        return  this.getWritableDatabase();
    }

    public ArrayList<Consult> getConsultsByMedicId(int medicId){
        ArrayList<Consult> consults = new ArrayList<Consult>();
        for (Consult m:Consults)
            if(m.getMedic_id()==medicId)
                consults.add(m);

        return  consults;
    }

    public Medic getMedicByName(String name){
        if(name == null || name.isEmpty())
            return null;

        for (Medic m:Medics)
            if(m.getName().equals(name))
                return  m;

        return  null;
    }

    public Patient getPatientByName(String name){
        if(name == null || name.isEmpty())
            return null;

        for (Patient m:Patients)
            if(m.getName().equals(name))
                return  m;

        return  null;
    }

    public Medic getMedicById(int id){
        if(id == 0)
            return null;

        for (Medic m:Medics)
            if(m.getId()==id)
                return  m;

        return  null;
    }

    public Patient getPatientById(int id){
        if(id == 0)
            return null;

        for (Patient m:Patients)
            if(m.getId()==id)
                return  m;

        return  null;
    }

    public Consult getConsultById(int id){
        if(id == 0)
            return null;

        for (Consult m:Consults)
            if(m.getId()==id)
                return  m;

        return  null;
    }

    private void tryCreateMedicTable(){
        SQLiteDatabase conn = startDbConnection();

        StringBuilder query = new StringBuilder();
        query.append("CREATE TABLE medic ");
        query.append("(id INTEGER PRIMARY KEY, ");
        query.append("name VARCHAR(50), ");
        query.append("crm VARCHAR(20), ");
        query.append("phone VARCHAR(20), ");
        query.append("fix_phone VARCHAR(20))");

        try{
            conn.execSQL(query.toString());
        }catch (SQLException sqlException){
            //Logger.log(sqlException)
            SQLException e = sqlException;
        }finally {
            conn.close();
        }
    }

    private void tryCreateConsultTable(){
        SQLiteDatabase conn = startDbConnection();

        StringBuilder query = new StringBuilder();
        query.append("CREATE TABLE consult ");
        query.append("(id INTEGER PRIMARY KEY, ");
        query.append("patient_id INTEGER, ");
        query.append("medic_id INTEGER, ");
        query.append("init DATETIME, ");
        query.append("end DATETIME, ");
        query.append("observation VARCHAR(200), ");
        query.append("FOREIGN KEY (medic_id) REFERENCES medic(id), ");
        query.append("FOREIGN KEY (patient_id) REFERENCES patient(id))");

        try{
            conn.execSQL(query.toString());
        }catch (SQLException sqlException){
            //Logger.log(sqlException)
            return;
        }finally {
            conn.close();
        }
    }

    private void tryCreatePatientTable(){
        SQLiteDatabase conn = startDbConnection();

        StringBuilder query = new StringBuilder();
        query.append("CREATE TABLE patient");
        query.append("(id INTEGER PRIMARY KEY, ");
        query.append("name VARCHAR(50), ");
        query.append("grp_blood TINYINT(1), ");
        query.append("phone VARCHAR(20), ");
        query.append("fix_phone VARCHAR(20))");

        try{
            conn.execSQL(query.toString());
        }catch (SQLException sqlException){
            //Logger.log(sqlException)
        }finally {
            conn.close();
        }
    }

    private void createTables(){
        SQLiteDatabase conn = startDbConnection();

        tryCreateMedicTable();
        tryCreatePatientTable();
        tryCreateConsultTable();
    }

    private void refreshPatientsContext(){
        SQLiteDatabase conn = startDbConnection();

        try{
            StringBuilder query = new StringBuilder();
            query.append("SELECT * FROM patient");

            Cursor dados = conn.rawQuery(query.toString(), null);

            this.Patients = new ArrayList<Patient>();
            this.Patients.clear();
            while (dados.moveToNext()){
                this.Patients.add(new Patient(
                        dados.getString(dados.getColumnIndex("name")),
                        dados.getString(dados.getColumnIndex("phone")),
                        dados.getString(dados.getColumnIndex("fix_phone")),
                        dados.getInt(dados.getColumnIndex("id")),
                        dados.getShort(dados.getColumnIndex("grp_blood"))==1
                ));
            }

        }catch (SQLException sqlException){
            //Logger.log(sqlException)
        }finally {
            conn.close();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void refreshConsultsContext(){
        SQLiteDatabase conn = startDbConnection();

        try{
            StringBuilder query = new StringBuilder();
            query.append("SELECT * FROM consult");

            Cursor dados = conn.rawQuery(query.toString(), null);

            this.Consults = new ArrayList<Consult>();
            this.Consults.clear();
            while (dados.moveToNext()){
                this.Consults.add(new Consult(
                        dados.getInt(dados.getColumnIndex("id")),
                        dados.getInt(dados.getColumnIndex("patient_id")),
                        dados.getInt(dados.getColumnIndex("medic_id")),
                        LocalDateTime.parse(dados.getString(dados.getColumnIndex("init"))),
                        LocalDateTime.parse(dados.getString(dados.getColumnIndex("end"))),
                        dados.getString(dados.getColumnIndex("observation"))
                ));
            }

        }catch (SQLException sqlException){
            //Logger.log(sqlException)
        }finally {
            conn.close();
        }
    }

    private void refreshMedicsContext(){
        SQLiteDatabase conn = startDbConnection();

        try{
            StringBuilder query = new StringBuilder();
            query.append("SELECT * FROM medic");

            Cursor dados = conn.rawQuery(query.toString(), null);

            this.Medics = new ArrayList<Medic>();
            this.Medics.clear();
            while (dados.moveToNext()){
                this.Medics.add(new Medic(
                        dados.getString(dados.getColumnIndex("name")),
                        dados.getString(dados.getColumnIndex("phone")),
                        dados.getString(dados.getColumnIndex("fix_phone")),
                        dados.getInt(dados.getColumnIndex("id")),
                        dados.getString(dados.getColumnIndex("crm"))
                ));
            }

        }catch (SQLException sqlException){
            //Logger.log(sqlException)
            SQLException e = sqlException;
        }finally {
            conn.close();
        }
    }

    @SuppressLint("NewApi")
    public void refreshContext(){
        createTables();
        refreshConsultsContext();
        refreshMedicsContext();
        refreshPatientsContext();
    }

    public ArrayList<Medic> getMedics() {
        return Medics == null ? new ArrayList<Medic>() : Medics;
    }

    public ArrayList<Consult> getConsults() {
        return Consults == null ? new ArrayList<Consult>() : Consults;
    }

    public ArrayList<Patient> getPatients() {
        return Patients == null ? new ArrayList<Patient>() : Patients;
    }

    /**
     * Check if medic exist, and if exist update, if not, insert new medic
     * @param medic
     * @return
     */
    public boolean insertOrUpdateMedic(Medic medic){
        SQLiteDatabase conn = startDbConnection();

        try{
            StringBuilder query = new StringBuilder();

            if(getMedicById(medic.getId()) == null)
                query.append("INSERT INTO medic VALUES(NULL,'"+medic.getName()+"','"+medic.getCrm()+"','"+medic.getPhone()+"','"+medic.getFix_phone()+"')");
            else{
                query.append("UPDATE medic ");
                query.append("SET name = '" + medic.getName() + "', ");
                query.append("crm = '" + medic.getCrm() + "', ");
                query.append("phone = '" + medic.getPhone() + "', ");
                query.append("fix_phone = '" + medic.getFix_phone() + "' ");
                query.append("WHERE id = " + medic.getId());
            }

            conn.execSQL(query.toString());

        }catch (SQLException sqlException){
            //Logger.log(sqlException)
            return false;
        }finally {
            conn.close();
            return true;
        }
    }

    public boolean insertOrUpdateConsult(Consult consult){
        SQLiteDatabase conn = startDbConnection();

        try{
            StringBuilder query = new StringBuilder();

            if(getConsultById(consult.getId()) == null)
                query.append("INSERT INTO consult VALUES(NULL,"+consult.getPatient_id()+","+consult.getMedic_id()+",'"+consult.getInit()+"','"+consult.getEnd()+"','"+consult.getObservation()+"')");
            else{
                query.append("UPDATE consult ");
                query.append("SET patient_id = " + consult.getPatient_id() + ", ");
                query.append("medic_id = " + consult.getMedic_id() + ", ");
                query.append("init = '" + consult.getInit() + "', ");
                query.append("end = '" + consult.getEnd() + "', ");
                query.append("observation = '" + consult.getObservation() + "' ");
                query.append("WHERE id = " + consult.getId());
            }

            conn.execSQL(query.toString());

        }catch (SQLException sqlException){
            //Logger.log(sqlException)
            return false;
        }finally {
            conn.close();
            return true;
        }
    }

    public boolean insertOrUpdatePatient(Patient patient){
        SQLiteDatabase conn = startDbConnection();

        try{
            StringBuilder query = new StringBuilder();

            if(getPatientById(patient.getId()) == null)
                query.append("INSERT INTO patient VALUES(NULL,'"+patient.getName()+"','"+(patient.isGrp_blood()?1:0) +"','"+patient.getPhone()+"','"+patient.getFix_phone()+"')");
            else{
                query.append("UPDATE patient ");
                query.append("SET name = '" + patient.getName() + "', ");
                query.append("grp_blood = '" + (patient.isGrp_blood()?1:0) + "', ");
                query.append("phone = '" + patient.getPhone() + "', ");
                query.append("fix_phone = '" + patient.getFix_phone() + "' ");
                query.append("WHERE id = " + patient.getId());
            }

            conn.execSQL(query.toString());

        }catch (SQLException sqlException){
            //Logger.log(sqlException)
            return false;
        }finally {
            conn.close();
            return true;
        }
    }

    public void deleteMedicById(int id){
        SQLiteDatabase conn = startDbConnection();

        try{
            StringBuilder query = new StringBuilder();
            query.append("DELETE FROM medic WHERE id = "+ id);
            conn.execSQL(query.toString());

        }catch (SQLException sqlException){
            //Logger.log(sqlException)
        }finally {
            conn.close();
        }
    }

    public void deletePatientById(int id){
        SQLiteDatabase conn = startDbConnection();

        try{
            StringBuilder query = new StringBuilder();
            query.append("DELETE FROM patient WHERE id = "+ id);
            conn.execSQL(query.toString());

        }catch (SQLException sqlException){
            //Logger.log(sqlException)
        }finally {
            conn.close();
        }
    }

    public void deleteConsultById(int id){
        SQLiteDatabase conn = startDbConnection();

        try{
            StringBuilder query = new StringBuilder();
            query.append("DELETE FROM consult WHERE id = "+ id);
            conn.execSQL(query.toString());

        }catch (SQLException sqlException){
            //Logger.log(sqlException)
        }finally {
            conn.close();
        }
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
