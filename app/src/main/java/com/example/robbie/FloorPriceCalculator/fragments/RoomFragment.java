package com.example.robbie.FloorPriceCalculator.fragments;

import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.robbie.FloorPriceCalculator.helpers.MyAdapter;
import com.example.robbie.FloorPriceCalculator.Room;
import com.example.robbie.FloorPriceCalculator.database.FeedReaderDbHelper;
import com.example.robbie.fitnesstracker.R;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 */
public class RoomFragment extends Fragment {

    FeedReaderDbHelper db;
    View rootView;
    String roomName;
    double roomLength, roomBreadth;
    int currentSteps = 0, goalSteps = 0;
   // public Goal goal=null;
    EditText length, breadth, roomName1;
    String date;
    Spinner s;

    public RoomFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        boolean bool = false;
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Floor Price Calculator - Rooms");
        db = new FeedReaderDbHelper(getContext());

        try{
            db.testInit();
        }
        catch(Exception e){
            db.initialiseSettings();
        }


        rootView = inflater.inflate(R.layout.fragment_rooms, container, false);
        ArrayList<Room> rooms = populateRooms();
       // getActiveGoal();
        RecyclerView rv = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        rv.setHasFixedSize(true);
        FragmentManager fm = getFragmentManager();
        MyAdapter rvAdapter = new MyAdapter(rooms, getContext());
        rv.setAdapter(rvAdapter);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        rv.setLayoutManager(llm);

        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
       calculations();


        com.github.clans.fab.FloatingActionButton fabGoal = (com.github.clans.fab.FloatingActionButton)view.findViewById(R.id.menu2);
        fabGoal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addGoal();

            }
        });


    }


    public void addGoal(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View v =inflater.inflate(R.layout.custom_alert_dialog, null);
        builder.setView(v);
        roomName1 = (EditText) v.findViewById(R.id.roomName1);
        breadth = (EditText) v.findViewById(R.id.roomBreadth);
        length = (EditText) v.findViewById(R.id.roomLength);

        builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog, int id) {
                RecyclerView rv = (RecyclerView) rootView.findViewById(R.id.recycler_view);
                rv.setHasFixedSize(true);
                try{
                    roomName = roomName1.getText().toString();
                    String temp = length.getText().toString();
                    roomLength = Double.parseDouble(temp);
                    String tempTwo = length.getText().toString();
                    roomBreadth = Double.parseDouble(tempTwo);

                    if(db.checkGoalExists(roomName)){
                        Toast.makeText(getContext(), "This room already Exists!", Toast.LENGTH_LONG).show();
                    }else {
                        db.insertGoal(roomName, roomLength, roomBreadth);
                    }
                } catch (Exception e){
                    Toast.makeText(getContext(), "Please Enter a Name and/or Step Goal", Toast.LENGTH_LONG).show();
                }
                ArrayList<Room> rooms = populateRooms();
                MyAdapter adapter = new MyAdapter( rooms, getContext());
                rv.setAdapter(adapter);
                rv.invalidate();
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

//    public void getActiveGoal(){
//        try {
//            goal = db.getActiveGoal(date);
//            Conversion convert= new Conversion(getContext());
//            CardView cv = (CardView) rootView.findViewById(R.id.card_view_active);
//            TextView active = (TextView) cv.findViewById(R.id.Active_Goal_Name) ;
//            TextView current = (TextView) cv.findViewById(R.id.currentSteps5) ;
//            TextView target = (TextView) cv.findViewById(R.id.currentSteps6) ;
//            TextView output = (TextView) cv.findViewById(R.id.textView11);
//            active.setText(goal.getName());
//            String units = db.getUnits();
//            if (!units.equals("Default") ){
//                goal.setUnits(units);
//            }
//            String[] converted = convert.convert(goal);
//            current.setText(converted[0] + " " + converted[1]);
//            target.setText(converted[2]+" "+converted[3]);
//            ProgressBar progress = (ProgressBar) cv.findViewById(R.id.progressBarActive) ;
//            Double cCom = ((double)goal.getSteps()/goal.getTarget()) * 100;
//            int progressPercentage = (int)Math.round(cCom);
//            output.setText(Integer.toString(progressPercentage) + "%");
//            progress.setProgress(progressPercentage);
//
//        } catch (Exception e){
//            Toast.makeText(getContext(), "No active Goal Set", Toast.LENGTH_LONG).show();
//        }
//    }

    public ArrayList<Room> populateRooms(){
        ArrayList<Room> rooms = db.getRooms();

        return rooms;
    }

    public void calculations(){
        ArrayList<Room> rooms = populateRooms();
        Double area = 0.0;
        for (int i = 0; i<rooms.size(); i++){
            area = area + (rooms.get(i).getLength() * rooms.get(i).getBreadth());
        }
        Double totalPrice = area* (db.getWoodPrice());
        Double totalCoatPrice = totalPrice * (db.getCoatPrice());
      //  CardView cv = (CardView) rootView.findViewById(R.id.card_view_active);
        TextView current = (TextView) this.getView().findViewById(R.id.textView5);
        TextView totalPriceText = (TextView) this.getView().findViewById(R.id.currentSteps6);
        TextView totalCoatText = (TextView) this.getView().findViewById(R.id.textView11);
      //  TextView areaText = (TextView) this.getView().findViewById(R.id.textView5);
        current.setText(area.toString());
        totalPriceText.setText(totalPrice.toString());
        totalCoatText.setText(totalCoatPrice.toString());

    }

    private String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
        // "yyyy-MM-dd HH:mm:ss", Locale.getDefault();
        "yyyy-MM-dd", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }

}

