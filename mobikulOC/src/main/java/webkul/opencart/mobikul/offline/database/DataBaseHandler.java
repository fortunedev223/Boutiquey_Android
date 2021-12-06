package webkul.opencart.mobikul.offline.database;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.support.customtabs.BuildConfig;


import java.util.ArrayList;
import java.util.List;

/*
 */
public class DataBaseHandler extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = BuildConfig.APPLICATION_ID;

    // Contacts table name
    private static final String TABLE_OFFLINE_DATA = "OFFLINE";

    public DataBaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_OFFLINE_DATA_TABLE = "CREATE TABLE " + TABLE_OFFLINE_DATA + "(id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, methodname VARCHAR, responsedata VARCHAR, productid VARCHAR, categoryid VARCHAR, profileurlorpageidentifier VARCHAR , orderid VARCHAR)";
        db.execSQL(CREATE_OFFLINE_DATA_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_OFFLINE_DATA);
        // Create tables again
        onCreate(db);
    }
    //    Methods for Offline mode
    public void updateIntoOfflineDB(String methodName, String responseData, String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor;
        try {
            switch (methodName) {
                case "getHomepage":
                    cursor = db.rawQuery("SELECT responsedata FROM " + TABLE_OFFLINE_DATA + " WHERE methodname = '" + methodName + "'", null);
                    if (cursor.getCount() != 0) {
                        cursor.moveToFirst();
                        if (cursor.getString(0).equals(responseData)) {
                            Log.d("DataBase", "updateIntoOfflineDB: DATA IS SAME");
                        } else {
                            ContentValues values = new ContentValues();
                            values.put("responsedata", responseData);
                            String selection = "methodname" + " LIKE ?";
                            String[] selectionArgs = {methodName};
                            int count = db.update(TABLE_OFFLINE_DATA, values, selection, selectionArgs);
                            Log.d("DataBase", "updateIntoOfflineDB: DATA UPDATED : " + count);
                        }
                    } else {
                        ContentValues values = new ContentValues();
                        values.put("methodname", methodName);
                        values.put("responsedata", responseData);
                        long newRowId = db.insert(TABLE_OFFLINE_DATA, null, values);
                        Log.d("DataBase", "updateIntoOfflineDB: DATA INSERTED AT ROW NUMBER : " + newRowId);
                    }
                    cursor.close();
                    break;
                case "addtocart": {
                    cursor = db.rawQuery("SELECT responsedata FROM " + TABLE_OFFLINE_DATA + " WHERE methodname = '" + methodName + "'", null);
                    if (cursor.getCount() != 0) {
                        cursor.moveToFirst();
                        if (cursor.getString(0).equals(responseData)) {
                            Log.d("DataBase", "updateIntoOfflineDB: DATA IS SAME");
                        } else {
                            ContentValues values = new ContentValues();
                            values.put("responsedata", responseData);
                            String selection = "methodname" + " LIKE ?";
                            String[] selectionArgs = {methodName};
                            int count = db.update(TABLE_OFFLINE_DATA, values, selection, selectionArgs);
                            Log.d("DataBase", "updateIntoOfflineDB: DATA UPDATED : " + count);
                        }
                    } else {
                        ContentValues values = new ContentValues();
                        values.put("methodname", methodName);
                        values.put("responsedata", responseData);
                        long newRowId = db.insert(TABLE_OFFLINE_DATA, null, values);
                        Log.d("DataBase", "updateIntoOfflineDB: DATA INSERTED AT ROW NUMBER : " + newRowId);
                    }
                    cursor.close();
                }
                break;
//                case "productSearch":
                case "manufacturerInfo":
//                case "customCollection":
                case "productCategory":
                    Log.d("", "updateIntoOfflineDB: " + responseData);
                    cursor = db.rawQuery("SELECT responsedata FROM " + TABLE_OFFLINE_DATA + " WHERE categoryid = '" + id + "' and methodname = '" + methodName + "'", null);
                    if (cursor.getCount() != 0) {
                        cursor.moveToFirst();
                        if (cursor.getString(0).equals(responseData)) {
                            Log.d("", "updateIntoOfflineDB: DATA IS SAME");
                        } else {
                            ContentValues values = new ContentValues();
                            values.put("responsedata", responseData);
                            String selection = "categoryid" + " LIKE ?";
                            String[] selectionArgs = {id};
                            int count = db.update(TABLE_OFFLINE_DATA, values, selection, selectionArgs);
                            Log.d("", "updateIntoOfflineDB: DATA UPDATED : " + count);
                        }
                    } else {
                        ContentValues values = new ContentValues();
                        values.put("methodname", methodName);
                        values.put("responsedata", responseData);
                        values.put("categoryid", id);
                        long newRowId = db.insert(TABLE_OFFLINE_DATA, null, values);
                        Log.d("DataBase", "updateIntoOfflineDB: DATA INSERTED AT ROW NUMBER : " + newRowId);
                    }
                    cursor.close();
                    break;

                case "getProduct":
                    Log.d("DataBase", "updateIntoOfflineDB: " + responseData);
                    cursor = db.rawQuery("SELECT responsedata FROM " + TABLE_OFFLINE_DATA + " WHERE productid = '" + id + "' and methodname = '" + methodName + "'", null);
                    if (cursor.getCount() != 0) {
                        cursor.moveToFirst();
                        if (cursor.getString(0).equals(responseData)) {
                            Log.d("DataBase", "updateIntoOfflineDB: DATA IS SAME");
                        } else {
                            ContentValues values = new ContentValues();
                            values.put("responsedata", responseData);
                            String selection = "productid" + " LIKE ?";
                            String[] selectionArgs = {id};
                            int count = db.update(TABLE_OFFLINE_DATA, values, selection, selectionArgs);
                            Log.d("DataBase", "updateIntoOfflineDB: DATA UPDATED : " + count);
                        }
                    } else {
                        ContentValues values = new ContentValues();
                        values.put("methodname", methodName);
                        values.put("responsedata", responseData);
                        values.put("productid", id);
                        long newRowId = db.insert(TABLE_OFFLINE_DATA, null, values);
                        Log.d("DataBase", "updateIntoOfflineDB: DATA INSERTED AT ROW NUMBER : " + newRowId);
                    }
                    cursor.close();
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Cursor selectFromOfflineDB(String methodName, String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        Log.d("DataBase", "selectFromOfflineDB: No internet connection");
        switch (methodName) {
            case "getHomepage":
                return db.rawQuery("SELECT responsedata, methodname FROM " + TABLE_OFFLINE_DATA + " WHERE methodname = '" + methodName + "'", null);
//            case "productSearch":
            case "manufacturerInfo":
//            case "customCollection":
            case "productCategory":
                return db.rawQuery("SELECT responsedata, methodname FROM " + TABLE_OFFLINE_DATA + " WHERE categoryid = '" + id + "' and methodname = '" + methodName + "'", null);
            case "getProduct":
                return db.rawQuery("SELECT responsedata, methodname FROM " + TABLE_OFFLINE_DATA + " WHERE productid = '" + id + "'", null);
            case "addtocart":
                return db.rawQuery("SELECT responsedata, methodname FROM " + TABLE_OFFLINE_DATA + " WHERE productid = '" + id + "'", null);
            default:
                return null;
        }
    }
}