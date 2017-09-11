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
import android.widget.EditText;
import android.widget.Toast;

import com.example.robbie.FloorPriceCalculator.database.FeedReaderDbHelper;
import com.example.robbie.fitnesstracker.R;

import java.security.spec.ECField;

/**
 * A simple {@link Fragment} subclass.
 */
public class CustomerFragment extends Fragment {

    EditText custName, custNumber, custAddress;
    FeedReaderDbHelper db;

    public CustomerFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        db = new FeedReaderDbHelper(getContext());
        try{
            db.testInit();
        }
        catch(Exception e){
            db.initialiseSettings();
        }


        return inflater.inflate(R.layout.fragment_customer, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        custName = (EditText) view.findViewById(R.id.editTextName);
        custAddress = (EditText) view.findViewById(R.id.editTextAddress);
        custNumber = (EditText) view.findViewById(R.id.editTextNumber);

        Button nextButton = (Button) view.findViewById(R.id.buttonNext1);

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = custName.getText().toString();
                String address = custAddress.getText().toString();
                String number = custNumber.getText().toString();

                try{
                    db.insertCustomer(name,address,number);
                    RoomFragment nextFrag= new RoomFragment();
                    FragmentManager fragmentManager=getActivity().getFragmentManager();
                    FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.content_summary_screen,nextFrag,"tag");
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                }catch  (Exception e){
                    Toast.makeText(getContext(), "Please Enter a Name and/or Cost per meter squared", Toast.LENGTH_LONG).show();

                }




            }
        });

    }

}
