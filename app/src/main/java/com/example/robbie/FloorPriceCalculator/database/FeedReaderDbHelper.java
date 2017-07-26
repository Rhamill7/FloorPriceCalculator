package com.example.robbie.FloorPriceCalculator.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.robbie.FloorPriceCalculator.Room;

import java.util.ArrayList;

/**
 * Created by Robbie on 09/02/2017.
 */



public class FeedReaderDbHelper extends SQLiteOpenHelper {
    // If you change the database schema, you must increment the database version.
  //  public static final int DATABASE_VERSION = 1;
   // public static final String DATABASE_NAME = "FeedReader.db";
    public static final String DATABASE_NAME = "SQLiteExample.db";
    private static final int DATABASE_VERSION = 13;
    public static final String GOAL_TABLE_NAME = "goal";
    public static final String GOAL_COLUMN_ID = "_id";
    public static final String GOAL_COLUMN_NAME = "name";
    public static final String GOAL_COLUMN_LENGTH = "length";
    public static final String GOAL_COLUMN_BREADTH = "breadth";


    public static final String TEST_TABLE_NAME = "test";
    public static final String TEST_COLUMN_ID = "_id";
    public static final String TEST_COLUMN_NAME = "name";
    public static final String TEST_COLUMN_WOOD = "wood";
    public static final String TEST_COLUMN_COAT = "coat";
    public static final String TEST_COLUMN_INIT = "init";        ;


    public FeedReaderDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + GOAL_TABLE_NAME +
                "(" + GOAL_COLUMN_ID + " INTEGER PRIMARY KEY, " +
                GOAL_COLUMN_NAME + " TEXT, " +
                GOAL_COLUMN_LENGTH + " REAL, " +
                GOAL_COLUMN_BREADTH + " REAL)");

        db.execSQL("CREATE TABLE " + TEST_TABLE_NAME +
                "(" + TEST_COLUMN_ID + " INTEGER PRIMARY KEY, " +
                TEST_COLUMN_NAME + " TEXT, " +
                TEST_COLUMN_WOOD + " REAL, " +
                TEST_COLUMN_COAT+ " REAL, " +
                TEST_COLUMN_INIT + " INTEGER)");

    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL("DROP TABLE IF EXISTS " + GOAL_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TEST_TABLE_NAME);
        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    ////////////////////////////Test Mode//////////////////////////////

    public boolean initialiseSettings() {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TEST_COLUMN_NAME, "Test");
        contentValues.put(TEST_COLUMN_WOOD, 1);
        contentValues.put(TEST_COLUMN_COAT, 1);
        contentValues.put(TEST_COLUMN_INIT, 1);

        db.insert(TEST_TABLE_NAME, null, contentValues);
        return true;
    }

    public void setWoodPrice(double woodPrice) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues args = new ContentValues();
        args.put(TEST_COLUMN_WOOD,woodPrice);
        // args.put(TEST_COLUMN_ACTIVE, active);
        db.update(TEST_TABLE_NAME, args, TEST_COLUMN_NAME + " = ? ", new String[]{"Test"});
    }

    public double getWoodPrice() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor =  db.rawQuery("SELECT * FROM " + TEST_TABLE_NAME + " WHERE " +
                TEST_COLUMN_NAME + "=?", new String[]{"Test"});
        cursor.moveToFirst();
        double act = Double.parseDouble(cursor.getString(2));
        return act;
    }


    public void setCoatPrice(double coatPrice) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues args = new ContentValues();
        args.put(TEST_COLUMN_COAT,coatPrice);
        // args.put(TEST_COLUMN_ACTIVE, active);
        db.update(TEST_TABLE_NAME, args, TEST_COLUMN_NAME + " = ? ", new String[]{"Test"});
    }

    public double getCoatPrice() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor =  db.rawQuery("SELECT * FROM " + TEST_TABLE_NAME + " WHERE " +
                TEST_COLUMN_NAME + "=?", new String[]{"Test"});
        cursor.moveToFirst();
        double act = Double.parseDouble(cursor.getString(3));
        return act;
    }

    public boolean testInit() {
        boolean bool = false;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor =  db.rawQuery("SELECT * FROM " + TEST_TABLE_NAME + " WHERE " +
                TEST_COLUMN_NAME + "=?", new String[]{"Test"});
        cursor.moveToFirst();
        int act = Integer.parseInt(cursor.getString(4));
        if (act == 1){
            bool = true;
        }
        return bool;
    }

    public String getDate() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor =  db.rawQuery("SELECT * FROM " + TEST_TABLE_NAME + " WHERE " +
                TEST_COLUMN_NAME + " = ? ", new String[]{"Test"});
        cursor.moveToFirst();
        String date = cursor.getString(2);
        return date;
    }

    ///////////////////////////////////////////////////////////////////////

///////////////*Goal Tables Edits Here*//////////////////////
    public boolean insertGoal(String name, double length, double breadth) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(GOAL_COLUMN_NAME, name);
        contentValues.put(GOAL_COLUMN_LENGTH, length);
        contentValues.put(GOAL_COLUMN_BREADTH, breadth);
        db.insert(GOAL_TABLE_NAME, null, contentValues);
        return true;
    }

//    public boolean updateGoal( String oldName, String newName, int oldTarget, int newTarget, String units) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues args = new ContentValues();
//        args.put(GOAL_COLUMN_NAME,newName);
//        args.put(GOAL_COLUMN_TARGET, newTarget);
//        args.put(GOAL_COLUMN_UNITS, units);
//        db.update(GOAL_TABLE_NAME, args, GOAL_COLUMN_NAME + " = ? AND " + GOAL_COLUMN_TARGET + " = ? ", new String[]{oldName, Integer.toString(oldTarget)});
//        return true;
//    }

    public void deleteGoal(String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(GOAL_TABLE_NAME,
            GOAL_COLUMN_NAME + " = ? ", new String[] { name });
    }

    public void deleteAllGoals(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from "+ GOAL_TABLE_NAME);
    }

    public boolean checkGoalExists(String name) {
       boolean check = false;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor =  db.rawQuery("SELECT * FROM " + GOAL_TABLE_NAME + " WHERE " +
                GOAL_COLUMN_NAME + " = ? ", new String[]{name});
        cursor.moveToFirst();
        try {
            String roomName = cursor.getString(1);
            check = true;
        } catch (Exception e){

        }

        return check;
    }

//    public Goal getGoal(String name, String date) {
//        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor cursor =  db.rawQuery("SELECT * FROM " + GOAL_TABLE_NAME + " WHERE " +
//                GOAL_COLUMN_NAME + " = ? AND " + GOAL_COLUMN_DATE + " = ? ", new String[]{name,date});
//        cursor.moveToFirst();
//        String goalName = cursor.getString(1);
//        int steps = Integer.parseInt(cursor.getString(2));
//        int target = Integer.parseInt(cursor.getString(3));
//        String gdate = cursor.getString(4);
//        int active = Integer.parseInt(cursor.getString(5));
//        String  units = cursor.getString(6);
//
//        Goal object = new Goal(name, steps, target, gdate, active, units );
//        return object;
//    }

    public ArrayList<Room> getRooms() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor =  db.rawQuery( "SELECT * FROM " + GOAL_TABLE_NAME, null );
        ArrayList<Room> rooms = new ArrayList<Room>();
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                //the .getString(int x) method of the cursor returns the column
                //of the table your query returned
                String name = cursor.getString(1);
                double length = Integer.parseInt(cursor.getString(2));
                double breadth = Integer.parseInt(cursor.getString(3));
                Room object = new Room(name, length, breadth);
                // Adding contact to list
                rooms.add(object);
            } while (cursor.moveToNext());
        }
        return rooms;

    }
}