package com.example.robbie.FloorPriceCalculator.fragments;


import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.robbie.fitnesstracker.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class CustomerFragment extends Fragment {


    public CustomerFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_customer, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        Button nextButton = (Button) view.findViewById(R.id.buttonNext1);

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RoomFragment nextFrag= new RoomFragment();

                FragmentManager fragmentManager=getActivity().getFragmentManager();
                FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.content_summary_screen,nextFrag,"tag");
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();


            }
        });

    }

}
