package com.example.clinicmanager;

import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.clinicmanager.dummy.DummyContent.DummyItem;

import java.util.ArrayList;
import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyMedicRecyclerViewAdapter extends RecyclerView.Adapter<MyMedicRecyclerViewAdapter.ViewHolder> {
    private final ArrayList<Medic> mValues;
    private FragmentManager fragmentManager;

    public MyMedicRecyclerViewAdapter(ArrayList<Medic> items, FragmentManager fragmentManager) {
        mValues = items;

        this.fragmentManager = fragmentManager;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_medic, parent, false);

        return new ViewHolder(view, fragmentManager);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Medic m = mValues.get(position);
        holder.mContentView.setText(m.toString());
        holder.medic = m;
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public Medic medic;
        public final View mView;
        public final TextView mContentView;
        public final Button btnEdit;
        public final Button btnDel;

        public ViewHolder(View view, FragmentManager fragmentManager) {
            super(view);
            mView = view;
            mContentView = (TextView) view.findViewById(R.id.content);
            btnEdit = (Button)view.findViewById(R.id.btnEditMedicItem);
            btnDel = (Button)view.findViewById(R.id.btnDelMedicItem);

            btnEdit.setOnClickListener(v ->{
                if(medic!=null)
                    fragmentManager.beginTransaction().replace(R.id.content_fragment, new EditMedicFragment(fragmentManager,medic.getId())).commit();
            });

            btnDel.setOnClickListener(v -> {
                if(medic!=null){
                    DbContext context = new DbContext(view.getContext());
                    context.deleteMedicById(medic.getId());

                    fragmentManager.beginTransaction().replace(R.id.content_fragment, new MedicFragment(fragmentManager)).commit();
                }
            });
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}