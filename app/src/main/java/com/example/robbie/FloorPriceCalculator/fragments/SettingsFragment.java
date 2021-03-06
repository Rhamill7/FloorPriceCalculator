package com.example.robbie.FloorPriceCalculator.fragments;


import android.app.Dialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import com.example.robbie.FloorPriceCalculator.Room;
import com.example.robbie.FloorPriceCalculator.helpers.MyAdapter;
import com.example.robbie.fitnesstracker.R;
import com.example.robbie.FloorPriceCalculator.database.FeedReaderDbHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;


/**
 * A simple {@link Fragment} subclass.
 */
public class SettingsFragment extends Fragment {

    Spinner s;
    FeedReaderDbHelper db;
    public String date;
    public Boolean testMode, goalsEditable;
    EditText editWood;
    EditText editCoat, customerName;
    EditText extraName, extraCost;
    int h =0, r = 0, d = 0, saf = 0, p=0;
    ArrayList<Double> extraCosts;
    ArrayList<String> extraNames;
    public SettingsFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        db = new FeedReaderDbHelper(getContext());
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Floor Price Calculator - Settings");
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //editWood = (EditText)  view.findViewById(R.id.editText4);
        //editCoat = (EditText)  view.findViewById(R.id.editText3);
      //  customerName = (EditText) view.findViewById(R.id.editText7);

        Button next = (Button) view.findViewById(R.id.button2);
        CheckBox checkBox = (CheckBox) view.findViewById(R.id.checkBox);
        CheckBox checkBox2 = (CheckBox) view.findViewById(R.id.checkBox2);
        CheckBox checkBox3 = (CheckBox) view.findViewById(R.id.checkBox3);
        CheckBox checkBox4 = (CheckBox) view.findViewById(R.id.checkBox4);
        CheckBox checkBox5 = (CheckBox) view.findViewById(R.id.checkBox5);
        Button checkBoxOther = (Button) view.findViewById(R.id.buttonAddMore);
        extraCosts = new ArrayList<Double>();
        extraNames = new ArrayList<String>();


        final String[] spinner = new String[]{"0","1", "2", "3", "4","5","6","7","8","9"};
       // String[] spinner2 = new String[]{"All","Completed","Above %", "Below %"};

         s = (Spinner) view.findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getContext(),
                android.R.layout.simple_dropdown_item_1line, spinner);
        s.setAdapter(adapter);



        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                saf = 1;

            }
        });

        checkBox2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                r = 1;

            }
        });

        checkBox3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                p = 1;

            }
        });

        checkBox4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                h = 1;

            }
        });

        checkBox5.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                d = 1;

            }
        });

        checkBoxOther.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addCosts();
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {

                    d = Integer.parseInt(s.getSelectedItem().toString());
                    db.insertSetup(h, r, d, saf, p);
                    Calculations nextFrag = new Calculations();

                    FragmentManager fragmentManager = getActivity().getFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.content_summary_screen, nextFrag, "tag");
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                }catch (Exception e){
                    Toast.makeText(getContext(), "Please Enter a Name and/or Cost per meter squared", Toast.LENGTH_LONG).show();

                }

            }
        });




    }


    public void addCosts(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View v =inflater.inflate(R.layout.add_step_dialog, null);
        builder.setView(v);
        extraName = (EditText) v.findViewById(R.id.extraName);

        extraCost  = (EditText) v.findViewById(R.id.extraAmount);

        builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog, int id) {
//                RecyclerView rv = (RecyclerView) rootView.findViewById(R.id.recycler_view);
//                rv.setHasFixedSize(true);
                try{
                    String extraNameN = extraName.getText().toString();
                    String temp = extraCost.getText().toString();
                    Double extraCostValue = Double.parseDouble(temp);
                    extraCosts.add(extraCostValue);
                    extraNames.add(extraNameN);
//                    if(db.checkGoalExists(roomName)){
//                        Toast.makeText(getContext(), "This room already Exists!", Toast.LENGTH_LONG).show();
//                    }else {
//                        db.insertGoal(roomName, roomLength, roomBreadth);
//                    }
                } catch (Exception e){
                    Toast.makeText(getContext(), "Please Enter a Name and/or Cost per meter squared", Toast.LENGTH_LONG).show();
                }
           //     ArrayList<Room> rooms = populateRooms();
//                MyAdapter adapter = new MyAdapter( rooms, getContext());
//                rv.setAdapter(adapter);
//                rv.invalidate();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });
        Dialog d = builder.create();
        d.show();
    }

}
