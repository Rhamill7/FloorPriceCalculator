package com.example.robbie.FloorPriceCalculator.helpers;

/**
 * Created by Robbie on 11/02/2017.
 */

import android.app.FragmentManager;
import android.content.Context;
import android.support.v7.widget.*;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.robbie.fitnesstracker.R;
import com.example.robbie.FloorPriceCalculator.Room;
import com.example.robbie.FloorPriceCalculator.database.FeedReaderDbHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    private String[] mDataset;
    private ArrayList<Room> rooms;
    private Context mContext;
    private ViewGroup vg;
    private FragmentManager fm;
    EditText step, name;
    private String date;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public CardView mCardView;
        public TextView mTextView, mTextViewLower, mOptions;
        public  FeedReaderDbHelper db;

      //  public TextView mOptions;
        public FragmentManager fm;

        public MyViewHolder(View v) {
            super(v);
            mCardView = (CardView) v.findViewById(R.id.card_view);
            mTextView = (TextView) v.findViewById(R.id.tv_text);
            mTextViewLower = (TextView) v.findViewById(R.id.lowerText);
            mOptions = (TextView) v.findViewById(R.id.txtOptionDigit);
            db = new FeedReaderDbHelper(v.getContext());


        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public MyAdapter(ArrayList<Room> rooms, Context mContext) {
        this.rooms = rooms;
        this.mContext = mContext;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                     int viewType) {
        vg = parent;
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_view, parent, false);
        // set the view's size, margins, paddings and layout parameters
        final MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        boolean bool = false;

//        String units = holder.db.getUnits();
//        if (!units.equals("Default")){
//            goals.get(position).setUnits(units);
//        }
//        Conversion convert = new Conversion(mContext);
//        String[] stepEntry = convert.convert(rooms.get(position));

//        int one = Integer.parseInt(stepEntry[0]);
//        int two = Integer.parseInt(stepEntry[2]);
        holder.mTextView.setText(rooms.get(position).getName());
        holder.mTextViewLower.setText("Area (m^2): " + Double.toString((rooms.get(position).getLength() * rooms.get(position).getBreadth())));
       // holder.mCardView.findViewById(R.id.card_view).setOnClickListener(new holder.);
        holder.mOptions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                PopupMenu popupMenu = new PopupMenu(mContext, holder.mOptions );
                popupMenu.inflate(R.menu.summary_screen);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()){
                            case R.id.action_edit:
//                                if (holder.db.testEditable()){
//                              // Toast.makeText(mContext, "Edited", Toast.LENGTH_LONG).show();
//                               final String tim = holder.mTextView.getText().toString();
//                               // Goal goalEdit = holder.db.getGoal(tim);
//                                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
//                                Activity active = (Activity)mContext;
//                                LayoutInflater inflater = active.getLayoutInflater();
//                                final View v =inflater.inflate(R.layout.custom_alert_dialog, null);
//                                builder.setView(v);
//                                TextView txt = (TextView) v.findViewById(R.id.titleFour);
//                                txt.setText("Edit Goal");
//                                step = (EditText) v.findViewById(R.id.goalSteps1);
//                                 name = (EditText) v.findViewById(R.id.goalName1);
//                                    String[] spinner = new String[]{"Steps", "Meters", "Kilometers",
//                                            "Yards", "Miles"};
//                                   final Spinner s = (Spinner) v.findViewById(R.id.spinner3);
//                                    ArrayAdapter<String> adapter = new ArrayAdapter<String>((mContext),
//                                            android.R.layout.simple_dropdown_item_1line, spinner);
//                                    s.setAdapter(adapter);
//
//
//                                    builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener(){
//                                    public void onClick(DialogInterface dialog, int id) {
//
//                                        try{
//                                            String newGoalName = name.getText().toString();
//                                            int newGoalSteps = Integer.parseInt(step.getText().toString());
//                                            Goal g = holder.db.getGoal(tim, date);
//                                            for (int i = 0; i < goals.size(); i++) {
//                                                if (goals.get(i).getName().equals(tim)) {
//                                                    goals.remove(i);
//                                                }
//                                            }
//                                            holder.db.updateGoal(g.getName(), newGoalName, g.getTarget(), newGoalSteps, s.getSelectedItem().toString());
//                                            g = holder.db.getGoal(newGoalName, date);
//                                            goals.add(g);
//                                        }
//                                        catch (Exception e){
//                                            Toast.makeText(mContext, "Please Enter a Name and/or Step Goal", Toast.LENGTH_LONG).show();
//                                        }
//                                       notifyDataSetChanged();
//                                    }
//                                });
//                                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener(){
//                                    public void onClick(DialogInterface dialog, int id) {
//                                        dialog.dismiss();
//                                    }
//                                });
//                                Dialog d = builder.create();
//                                d.show();}
//                                else {
//                                    Toast.makeText(mContext, "Goals not Editable", Toast.LENGTH_LONG).show();
//
//                                }
                                break;

                            case R.id.action_delete:
                                Toast.makeText(mContext, "Deleted", Toast.LENGTH_LONG).show();
                                String temp = holder.mTextView.getText().toString();
                                holder.db.deleteGoal(temp);
                                for (int i = 0; i< rooms.size(); i++){
                                    if (rooms.get(i).getName().equals(temp)){
                                        rooms.remove(i);
                                       notifyDataSetChanged();
                                    }
                                }
                                break;

                            default:
                                break;
                        }
                        return false;
                    }
                });
                popupMenu.show();
            }
        });

    }

    private String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                // "yyyy-MM-dd HH:mm:ss", Locale.getDefault();
                "yyyy-MM-dd", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }

    @Override
    public int getItemCount() {
        return rooms.size();
    }
}