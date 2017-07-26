package com.example.robbie.FloorPriceCalculator.fragments;


import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.example.robbie.fitnesstracker.R;
import com.example.robbie.FloorPriceCalculator.database.FeedReaderDbHelper;


/**
 * A simple {@link Fragment} subclass.
 */
public class SettingsFragment extends Fragment {

    FeedReaderDbHelper db;
    public String date;
    public Boolean testMode, goalsEditable;
    EditText editWood;
    EditText editCoat;
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
        editWood = (EditText)  view.findViewById(R.id.editText4);
        editCoat = (EditText)  view.findViewById(R.id.editText3);
        Button pricesButton = (Button) view.findViewById(R.id.button);
        Button deleteAll = (Button) view.findViewById(R.id.button2);
        editWood.setText(Double.toString(db.getWoodPrice()));
        editCoat.setText(Double.toString(db.getCoatPrice()));

        pricesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    Double tempWood = Double.parseDouble(editWood.getText().toString());
                    db.setWoodPrice(tempWood);
                    Toast.makeText(getContext(), "Aye!", Toast.LENGTH_LONG).show();
                }
                catch (Exception e){
                    Toast.makeText(getContext(), "Naw wood!", Toast.LENGTH_LONG).show();
                }
                try{
                    Double tempCoat = Double.parseDouble(editCoat.getText().toString());
                    db.setCoatPrice(tempCoat);
                } catch (Exception e){
                    Toast.makeText(getContext(), "Naw Coat!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }


}
