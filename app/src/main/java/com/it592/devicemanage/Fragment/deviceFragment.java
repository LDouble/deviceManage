package com.it592.devicemanage.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.it592.devicemanage.AddDeviceActivity;
import com.it592.devicemanage.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class deviceFragment extends Fragment {


    public deviceFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_device, container, false);
        FloatingActionButton floatingActionButton = (FloatingActionButton) view.findViewById(R.id.add_device);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), AddDeviceActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }

}
