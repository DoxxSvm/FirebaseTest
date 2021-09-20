package com.example.firebasetest;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class StartUpAdaptor extends ArrayAdapter<EmployeeInfo> {

    public StartUpAdaptor(@NonNull Context context, @NonNull List<EmployeeInfo> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        if(listItemView == null){
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.listviewadap,parent,false
            );
        }
        EmployeeInfo current = getItem(position);
        TextView name = listItemView.findViewById(R.id.sname);
        name.setText(current.getstartUpName());
        TextView pot = listItemView.findViewById(R.id.potential);
        pot.setText(current.getpotential());
        TextView build =listItemView.findViewById(R.id.buildtime);
        build.setText(current.getbuildTime());
        return listItemView;

    }
}
