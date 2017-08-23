package com.example.robbie.FloorPriceCalculator.fragments;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.example.robbie.FloorPriceCalculator.Room;
import com.example.robbie.FloorPriceCalculator.database.FeedReaderDbHelper;
import com.example.robbie.fitnesstracker.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;


public class PricesFragment extends Fragment {

    FeedReaderDbHelper db;
    public String date;
    public Boolean testMode, goalsEditable;
    EditText editWood;
    EditText editCoat, customerName;
    EditText extraName, extraCost;
    boolean woodCheck =false, coatCheck = false;
    ArrayList<Double> extraCosts;
    ArrayList<String> extraNames;


    public PricesFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        db = new FeedReaderDbHelper(getContext());
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Floor Price Calculator - Prices");
        return inflater.inflate(R.layout.fragment_prices, container, false);
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        editWood = (EditText)  view.findViewById(R.id.editText4);
        editCoat = (EditText)  view.findViewById(R.id.editText3);
        customerName = (EditText) view.findViewById(R.id.editText7);

        Button pricesButton = (Button) view.findViewById(R.id.button);
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

    private String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                // "yyyy-MM-dd HH:mm:ss", Locale.getDefault();
                //"yyyy-MM-dd"
                "dd-MM-yyy", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date).toString();
    }


}
