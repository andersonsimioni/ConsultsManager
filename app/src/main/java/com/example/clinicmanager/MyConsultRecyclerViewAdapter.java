package com.example.clinicmanager;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Build;
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
public class MyConsultRecyclerViewAdapter extends RecyclerView.Adapter<MyConsultRecyclerViewAdapter.ViewHolder> {

    private  FragmentManager fragmentManager;
    private final ArrayList<Consult> mValues;

    public MyConsultRecyclerViewAdapter(ArrayList<Consult> items, FragmentManager fragmentManager) {
        mValues = items;
        this.fragmentManager = fragmentManager;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_consult, parent, false);
        return new ViewHolder(view, fragmentManager);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.consult = mValues.get(position);
        holder.mContentView.setText(holder.consult.toString());
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private Consult consult;
        public final View mView;
        public final TextView mContentView;
        public final Button btnEdit;
        public final Button btnDel;

        public ViewHolder(View view, FragmentManager fragmentManager) {
            super(view);
            mView = view;
            mContentView = (TextView) view.findViewById(R.id.content);
            btnEdit = (Button)view.findViewById(R.id.btnEditConsultItem);
            btnDel = (Button)view.findViewById(R.id.btnDelConsultItem);

            btnEdit.setOnClickListener(v ->{
                if(consult!=null)
                    fragmentManager.beginTransaction().replace(R.id.content_fragment, new EditConsultFragment(fragmentManager,consult.getId())).commit();
            });

            btnDel.setOnClickListener(v -> {
                if(consult!=null){
                    DbContext context = new DbContext(view.getContext());
                    context.deleteConsultById(consult.getId());

                    fragmentManager.beginTransaction().replace(R.id.content_fragment, new ConsultFragment(fragmentManager)).commit();
                }
            });
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}