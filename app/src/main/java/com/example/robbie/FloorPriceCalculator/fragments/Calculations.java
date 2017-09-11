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
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.robbie.FloorPriceCalculator.Customer;
import com.example.robbie.FloorPriceCalculator.Room;
import com.example.robbie.FloorPriceCalculator.database.FeedReaderDbHelper;
import com.example.robbie.FloorPriceCalculator.helpers.MyAdapter;
import com.example.robbie.fitnesstracker.R;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 */
public class Calculations extends Fragment {
    FeedReaderDbHelper db;
    String output = "";

    public Calculations() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        db = new FeedReaderDbHelper(getContext());
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_calculations, container, false);
    }


    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
       // output = db.getCust().getName();
        TextView text = (TextView) view.findViewById(R.id.textViewResult);

        Button shareNow = (Button) view.findViewById(R.id.buttonShareNow);



        ArrayList<Room> rooms = db.getRooms();
        Double area = 0.0;
        for (int i = 0; i<rooms.size(); i++){
            Double roomArea = (rooms.get(i).getLength() * rooms.get(i).getBreadth());
            area = area + roomArea;
            output = output + "\n" + "Name: " + rooms.get(i).getName()
                    + "\n Length: " + rooms.get(i).getLength()
                    + "\n Breadth: " + rooms.get(i).getBreadth()
                    + "\n Room Area: " + roomArea + "\n\n";
        }
        Double totalPrice = 0.0;
        ArrayList<Integer> list = db.getSetup();
        if (list.get(0)== 1){
            output = output + "\n Hardwood Price £ " +  area * (db.getHardPrice());
            totalPrice = totalPrice + (area * (db.getHardPrice()));
        }
        if (list.get(1)== 1){
            output = output + "\n Repairs Price £ " +  area * (db.getRadPrice());
            totalPrice = totalPrice + (area * (db.getRadPrice()));
        }
        if (list.get(2)!= 0){
            output = output + "\n Doors Price £ " +  list.get(2) * (db.getDoorPrice());
            totalPrice = totalPrice + (area * (db.getDoorPrice()));
        }
        if (list.get(3)== 1){
            output = output + "\n Sand+Fin Price £ " +  area * (db.getSafPrice());
            totalPrice = totalPrice + (area * (db.getSafPrice()));
        }
        if (list.get(4)== 1){
            output = output + "\n Pine Price £ " +  area * (db.getPinePrice());
            totalPrice = totalPrice + (area * (db.getPinePrice()));
        }

        output = output + "\n Total Price £ " + totalPrice;

        text.setText(output);

        shareNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // String output = "";

//                ArrayList<Room> rooms = db.getRooms();
//                Double area = 0.0;
//                for (int i = 0; i<rooms.size(); i++){
//                    Double roomArea = (rooms.get(i).getLength() * rooms.get(i).getBreadth());
//                    area = area + roomArea;
//                    output = output + "\n" + "Name: " + rooms.get(i).getName()
//                            + "\n Length: " + rooms.get(i).getLength()
//                            + "\n Breadth: " + rooms.get(i).getBreadth()
//                            + "\n Room Area: " + roomArea + "\n";
//                }
//                Customer c = db.getCust();
//                output = output + c.getName();
//                Double safPrice = area* (db.getSafPrice());
//                Double radPrice = area * (db.getRadPrice());
//                Double pinePrice = area * (db.getPinePrice());
//                Double hardPrice = area * (db.getHardPrice());
                //  Double totalCostPlusExtras = totalPrice;
              //  output = output + "\n " + "Total Area: " + area;
//                if (safCheck == (true)){
//                    output = output + "\n Sand + Finish: £ " + safPrice;}
//                if (radCheck == true){
//                    output = output + "\n repairs + doors: £ " + radPrice;}
//                if (pineCheck == true){
//                    output = output + "\n pine: £ " + pinePrice;}
//                if (hardCheck == true){
//                    output = output + "\n hardwood: £ " + hardPrice;}
//                if (!extraCosts.isEmpty()){
//
//                    for (int n =0; n<extraCosts.size(); n++){
//                        totalCostPlusExtras = totalCostPlusExtras * extraCosts.get(n);
//                    }
//
//                    output = output + "\n Total Cost with Extras " + totalCoatPrice;}
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("message/rfc822");
                i.putExtra(Intent.EXTRA_EMAIL  , new String[]{"roblin@blueyonder.co.uk"});
                i.putExtra(Intent.EXTRA_SUBJECT,  " Job Pricing " + getDateTime() );
                //  i.putExtra(Intent.EXTRA_TEXT   , "body of email");
                i.putExtra(Intent.EXTRA_TEXT   , output);
                try {
                    startActivity(Intent.createChooser(i, "Send mail..."));
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(getContext(), "There are no email clients installed.", Toast.LENGTH_SHORT).show();
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
