package com.example.clinicmanager;

import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.clinicmanager.dummy.DummyContent.DummyItem;

import java.util.ArrayList;
import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyPatientRecyclerViewAdapter extends RecyclerView.Adapter<MyPatientRecyclerViewAdapter.ViewHolder> {

    private final ArrayList<Patient> mValues;
    private FragmentManager fragmentManager;

    public MyPatientRecyclerViewAdapter(ArrayList<Patient> items, FragmentManager _fragmentManager) {
       mValues = items;

        fragmentManager = _fragmentManager;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_patient, parent, false);
        return new ViewHolder(view, fragmentManager);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.patient = mValues.get(position);
        holder.mContentView.setText(holder.patient.toString());
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public Patient patient;
        public final View mView;
        public final TextView mContentView;
        public final Button btnEdit;
        public final Button btnDel;

        public ViewHolder(View view, FragmentManager fragmentManager) {
            super(view);
            mView = view;
            mContentView = (TextView) view.findViewById(R.id.content);
            btnEdit = (Button)view.findViewById(R.id.btnEditPatientItem);
            btnDel = (Button)view.findViewById(R.id.btnDelPatientItem);

            btnEdit.setOnClickListener(v ->{
                if(patient!=null)
                    fragmentManager.beginTransaction().replace(R.id.content_fragment, new EditPatientFragment(fragmentManager,patient.getId())).commit();
            });

            btnDel.setOnClickListener(v -> {
                if(patient!=null){
                    DbContext context = new DbContext(view.getContext());
                    context.deletePatientById(patient.getId());

                    fragmentManager.beginTransaction().replace(R.id.content_fragment, new PatientFragment(fragmentManager)).commit();
                }
            });
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}