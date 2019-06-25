package com.beta.watsonz.onna927_beta_1.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
/**
 * Created by watsonz on 2015-10-03.
 */
public class SQLiteHandler extends SQLiteOpenHelper {
    private static final String TAG = SQLiteHandler.class.getSimpleName();

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "android_api";

    // Login table name
    private static final String TABLE_USER = "user";
    private static final String TABLE_AUCTION = "auction";
    private static final String TABLE_CUPON = "cupon";
    private static final String TABLE_PURCHASE_CUPON = "purchase_cupon";
    private static final String TABLE_REVIEW = "review";
    private static final String TABLE_WISH = "wish";

    // Login Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_AUCTION_ID = "auction_id";
    private static final String KEY_NAME = "name";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_UID = "uid";
    private static final String KEY_CREATED_AT = "created_at";

    private static final String KEY_place = "place";
    private static final String KEY_people = "people";
    private static final String KEY_menu = "menu";
    private static final String KEY_price = "price";
    private static final String KEY_time = "time";

    private static final String KEY_CUPON_ID = "cupon_id";
    private static final String KEY_STORE = "store";
    private static final String KEY_STORE_UID = "store_uid";
    private static final String KEY_MAIN_A = "mainA";
    private static final String KEY_MAIN_B = "mainB";
    private static final String KEY_MAIN_C = "mainC";
    private static final String KEY_SIDE_A = "sideA";
    private static final String KEY_SIDE_B = "sideB";
    private static final String KEY_SIDE_C = "sideC";
    private static final String KEY_DRINK_A = "drinkA";
    private static final String KEY_DRINK_B = "drinkB";
    private static final String KEY_DRINK_C = "drinkC";

    private static final String KEY_REVIEW_ID = "review_id";
    private static final String KEY_DIRTY = "dirty";

    private static final String KEY_object = "object";
    private static final String KEY_air = "air";
    private static final String KEY_url = "url";


    public ArrayList<String> auction_id = new ArrayList<String>();
    public ArrayList<String> cupon_id = new ArrayList<String>();
    public ArrayList<String> purchase_cupon_id = new ArrayList<String>();
    public ArrayList<String> review_id = new ArrayList<String>();
    public ArrayList<String> wish_id = new ArrayList<String>();

    public SQLiteHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_LOGIN_TABLE = "CREATE TABLE " + TABLE_USER + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT,"
                + KEY_EMAIL + " TEXT UNIQUE," + KEY_UID + " TEXT,"
                + KEY_CREATED_AT + " TEXT" + ")";
        db.execSQL(CREATE_LOGIN_TABLE);

        String CREATE_AUCTION_TABLE = "CREATE TABLE " + TABLE_AUCTION + "("
                + KEY_AUCTION_ID + " TEXT UNIQUE,"
                + KEY_NAME + " TEXT," + KEY_place + " TEXT,"
                + KEY_people + " TEXT,"+ KEY_menu + " TEXT,"
                + KEY_price + " TEXT," + KEY_time + " TEXT" + ")";
        db.execSQL(CREATE_AUCTION_TABLE);

        String CREATE_CUPON_TABLE = "CREATE TABLE " + TABLE_CUPON + "("
                + KEY_CUPON_ID + " TEXT UNIQUE,"
                + KEY_AUCTION_ID + " TEXT,"
                + KEY_STORE_UID + " TEXT,"
                + KEY_STORE + " TEXT,"
                + KEY_price + " TEXT," + KEY_time + " TEXT,"
                + KEY_MAIN_A + " TEXT," + KEY_MAIN_B + " TEXT," + KEY_MAIN_C + " TEXT,"
                + KEY_SIDE_A + " TEXT," + KEY_SIDE_B + " TEXT," + KEY_SIDE_C + " TEXT,"
                + KEY_DRINK_A + " TEXT," + KEY_DRINK_B + " TEXT," + KEY_DRINK_C + " TEXT" + ")";
        db.execSQL(CREATE_CUPON_TABLE);

        String CREATE_PURHCASE_CUPON_TABLE = "CREATE TABLE " + TABLE_PURCHASE_CUPON + "("
                + KEY_CUPON_ID + " TEXT UNIQUE," + KEY_STORE + " TEXT,"
                + KEY_price + " TEXT," + KEY_time + " TEXT,"
                + KEY_MAIN_A + " TEXT," + KEY_MAIN_B + " TEXT," + KEY_MAIN_C + " TEXT,"
                + KEY_SIDE_A + " TEXT," + KEY_SIDE_B + " TEXT," + KEY_SIDE_C + " TEXT,"
                + KEY_DRINK_A + " TEXT," + KEY_DRINK_B + " TEXT," + KEY_DRINK_C + " TEXT" + ")";
        db.execSQL(CREATE_PURHCASE_CUPON_TABLE);

        String CREATE_REVIEW_TABLE = "CREATE TABLE " + TABLE_REVIEW + "("
                + KEY_REVIEW_ID + " TEXT UNIQUE," + KEY_STORE + " TEXT,"
                + KEY_time + " TEXT,"
                + KEY_DIRTY + " TEXT"+ ")";
        db.execSQL(CREATE_REVIEW_TABLE);


        String CREATE_WISH_TABLE = "CREATE TABLE " + TABLE_WISH + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_STORE + " TEXT UNIQUE,"
                + KEY_url + " TEXT,"
                + KEY_place + " TEXT,"
                + KEY_object + " TEXT,"
                + KEY_air + " TEXT,"
                + KEY_people+ " TEXT"+ ")";
        db.execSQL(CREATE_WISH_TABLE);

        Log.d(TAG, "Database tables created");
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);

        // Create tables again
        onCreate(db);
    }

    /**
     * Storing user details in database
     * */
    public void addUser(String name, String email, String uid, String created_at) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, name); // Name
        values.put(KEY_EMAIL, email); // Email
        values.put(KEY_UID, uid); // Email
        values.put(KEY_CREATED_AT, created_at); // Created At

        // Inserting Row
        long id = db.insert(TABLE_USER, null, values);
        db.close(); // Closing database connection

        Log.d(TAG, "New user inserted into sqlite: " + id);
    }

    public void addAuction(String auction_id, String name, String place, String people, String menu,String price, String time) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_AUCTION_ID, auction_id);
        values.put(KEY_NAME, name); // Name
        values.put(KEY_place, place);
        values.put(KEY_people,people);
        values.put(KEY_menu, menu);
        values.put(KEY_price, price);
        values.put(KEY_time, time);

        // Inserting Row
        long id = db.insert(TABLE_AUCTION, null, values);
        db.close(); // Closing database connection

        Log.d(TAG, "New Auction inserted into sqlite: " + id);
    }

    public void addCupon(String cupon_id, String auction_id, String owner_id, String store, String price, String time, String mainA, String mainB, String mainC,
                         String sideA, String sideB, String sideC, String drinkA, String drinkB, String drinkC) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_CUPON_ID, cupon_id);
        values.put(KEY_AUCTION_ID, auction_id);
        values.put(KEY_STORE_UID, owner_id);
        values.put(KEY_STORE, store); // Name
        values.put(KEY_price, price);
        values.put(KEY_time,time);
        values.put(KEY_MAIN_A, mainA);
        values.put(KEY_MAIN_B, mainB);
        values.put(KEY_MAIN_C, mainC);
        values.put(KEY_SIDE_A, sideA);
        values.put(KEY_SIDE_B, sideB);
        values.put(KEY_SIDE_C, sideC);
        values.put(KEY_DRINK_A, drinkA);
        values.put(KEY_DRINK_B, drinkB);
        values.put(KEY_DRINK_C, drinkC);

        // Inserting Row
        long id = db.insert(TABLE_CUPON, null, values);
        db.close(); // Closing database connection

        Log.d(TAG, "New Cupon inserted into sqlite: " + id);
    }

    public void addPurchaseCupon(String cupon_id, String store, String price, String time, String mainA, String mainB, String mainC,
                         String sideA, String sideB, String sideC, String drinkA, String drinkB, String drinkC) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_CUPON_ID, cupon_id);
        values.put(KEY_STORE, store); // Name
        values.put(KEY_price, price);
        values.put(KEY_time,time);
        values.put(KEY_MAIN_A, mainA);
        values.put(KEY_MAIN_B, mainB);
        values.put(KEY_MAIN_C, mainC);
        values.put(KEY_SIDE_A, sideA);
        values.put(KEY_SIDE_B, sideB);
        values.put(KEY_SIDE_C, sideC);
        values.put(KEY_DRINK_A, drinkA);
        values.put(KEY_DRINK_B, drinkB);
        values.put(KEY_DRINK_C, drinkC);

        // Inserting Row
        long id = db.insert(TABLE_PURCHASE_CUPON, null, values);
        db.close(); // Closing database connection

        Log.d(TAG, "New Purchased Cupon inserted into sqlite: " + id);
    }

    public void addReview(String cupon_id, String store_name, String time, String dirty ) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_REVIEW_ID, cupon_id);
        values.put(KEY_STORE, store_name); // Name
        values.put(KEY_time,time);
        values.put(KEY_DIRTY,dirty);
        // Inserting Row
        long id = db.insert(TABLE_REVIEW, null, values);
        db.close(); // Closing database connection

        Log.d(TAG, "New Review inserted into sqlite: " + id);
    }


    public boolean addWish(String store_name, String url, String place, String object, String air, String people) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_STORE, store_name);
        values.put(KEY_url, url); // Name
        values.put(KEY_place,place);
        values.put(KEY_object,object);
        values.put(KEY_air,air);
        values.put(KEY_people,people);
        // Inserting Row
        long id = db.insert(TABLE_WISH, null, values);
        db.close(); // Closing database connection

        Log.d(TAG, "New WISH inserted into sqlite: " + id);

        if(id == -1)return false;
        else
            return true;
    }
    /**
     * Getting user data from database
     * */
    public HashMap<String, String> getUserDetails() {
        HashMap<String, String> user = new HashMap<String, String>();
        String selectQuery = "SELECT  * FROM " + TABLE_USER;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // Move to first row
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            user.put("name", cursor.getString(1));
            user.put("email", cursor.getString(2));
            user.put("uid", cursor.getString(3));
            user.put("created_at", cursor.getString(4));
        }
        cursor.close();
        db.close();
        // return user
        Log.d(TAG, "Fetching user from Sqlite: " + user.toString());

        return user;
    }
    public HashMap<String, String> getAuction(String id) {
        HashMap<String, String> auction_list = new HashMap<String, String>();
        String selectQuery = "SELECT  * FROM " + TABLE_AUCTION + " WHERE auction_id = " + id;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // Move to first row
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            auction_list.put("auction_id", cursor.getString(0));
            auction_list.put("name", cursor.getString(1));
            auction_list.put("place", cursor.getString(2));
            auction_list.put("people", cursor.getString(3));
            auction_list.put("menu", cursor.getString(4));
            auction_list.put("price", cursor.getString(5));
            auction_list.put("time", cursor.getString(6));
        }
        cursor.close();
        db.close();
        // return user
        Log.d(TAG, "Fetching Auction from Sqlite: " + auction_list.toString());

        return auction_list;
    }
    public HashMap<String, String> getCupon(String id) {
        HashMap<String, String> cupon_list = new HashMap<String, String>();
        String selectQuery = "SELECT  * FROM " + TABLE_CUPON + " WHERE cupon_id = " + id;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // Move to first row
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            cupon_list.put("cupon_id", cursor.getString(0));
            cupon_list.put("auction_id", cursor.getString(1));
            cupon_list.put("store_uid", cursor.getString(2));
            cupon_list.put("store", cursor.getString(3));
            cupon_list.put("price", cursor.getString(4));
            cupon_list.put("time", cursor.getString(5));
            cupon_list.put("mainA", cursor.getString(6));
            cupon_list.put("mainB", cursor.getString(7));
            cupon_list.put("mainC", cursor.getString(8));
            cupon_list.put("sideA", cursor.getString(9));
            cupon_list.put("sideB", cursor.getString(10));
            cupon_list.put("sideC", cursor.getString(11));
            cupon_list.put("drinkA", cursor.getString(12));
            cupon_list.put("drinkB", cursor.getString(13));
            cupon_list.put("drinkC", cursor.getString(14));
        }
        cursor.close();
        db.close();
        // return user
        Log.d(TAG, "Fetching cupon from Sqlite: " + cupon_list.toString());

        return cupon_list;
    }

    public HashMap<String, String> getPurchaseCupon(String id) {
        HashMap<String, String> cupon_list = new HashMap<String, String>();
        String selectQuery = "SELECT  * FROM " + TABLE_PURCHASE_CUPON + " WHERE cupon_id = " + id;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // Move to first row
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            cupon_list.put("cupon_id", cursor.getString(0));
            cupon_list.put("store", cursor.getString(1));
            cupon_list.put("price", cursor.getString(2));
            cupon_list.put("time", cursor.getString(3));
            cupon_list.put("mainA", cursor.getString(4));
            cupon_list.put("mainB", cursor.getString(5));
            cupon_list.put("mainC", cursor.getString(6));
            cupon_list.put("sideA", cursor.getString(7));
            cupon_list.put("sideB", cursor.getString(8));
            cupon_list.put("sideC", cursor.getString(9));
            cupon_list.put("drinkA", cursor.getString(10));
            cupon_list.put("drinkB", cursor.getString(11));
            cupon_list.put("drinkC", cursor.getString(12));
        }
        cursor.close();
        db.close();
        // return user
        Log.d(TAG, "Fetching cupon from Sqlite: " + cupon_list.toString());

        return cupon_list;
    }

    public HashMap<String, String> getReview(String id) {
        HashMap<String, String> review_list = new HashMap<String, String>();
        String selectQuery = "SELECT  * FROM " + TABLE_REVIEW + " WHERE review_id = " + id;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // Move to first row
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            review_list.put("review_id", cursor.getString(0));
            review_list.put("store", cursor.getString(1));
            review_list.put("time", cursor.getString(2));
        }
        cursor.close();
        db.close();
        // return user
        Log.d(TAG, "Fetching cupon from Sqlite: " + review_list.toString());

        return review_list;
    }

    public HashMap<String, String> getWish(String id) {
        HashMap<String, String> wish_list = new HashMap<String, String>();
        String selectQuery = "SELECT  * FROM " + TABLE_WISH + " WHERE id = " + id;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // Move to first row
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            wish_list.put("id", cursor.getString(0));
            wish_list.put("store", cursor.getString(1));
            wish_list.put("url", cursor.getString(2));
            wish_list.put("place", cursor.getString(3));
            wish_list.put("object", cursor.getString(4));
            wish_list.put("air", cursor.getString(5));
            wish_list.put("people", cursor.getString(6));
        }
        cursor.close();
        db.close();
        // return user
        Log.d(TAG, "Fetching cupon from Sqlite: " + wish_list.toString());

        return wish_list;
    }
    /**
     * DB에서 받아온 값을 ArrayList에 Add
     */
    public ArrayList<Infoauction> doWhileCursorToArray(){
        int index = 0;
        Cursor mCursor;
        Infoauction mInfoClass;
        ArrayList<Infoauction> mInfoArray;
        String selectQuery = "SELECT * FROM " + TABLE_AUCTION;

        SQLiteDatabase db = this.getReadableDatabase();
        mCursor = db.rawQuery(selectQuery, null);
        mInfoArray = new ArrayList<Infoauction>();
        while (mCursor.moveToNext()) {

            mInfoClass = new Infoauction(
                    mCursor.getString(mCursor.getColumnIndex("auction_id")),
                    mCursor.getString(mCursor.getColumnIndex("name")),
                    mCursor.getString(mCursor.getColumnIndex("place")),
                    mCursor.getString(mCursor.getColumnIndex("people")),
                    mCursor.getString(mCursor.getColumnIndex("menu")),
                    mCursor.getString(mCursor.getColumnIndex("price")),
                    mCursor.getString(mCursor.getColumnIndex("time")));
            auction_id.add(mCursor.getString(mCursor.getColumnIndex("auction_id")));
            mInfoArray.add(mInfoClass);
            index++;
        }

        mCursor.close();
        return mInfoArray;
    }
    public ArrayList<InfoCupon> doWhileCursorToArray_cupon(){
        Cursor mCursor;
        InfoCupon mInfoClass;
        ArrayList<InfoCupon> mInfoArray;
        String selectQuery = "SELECT * FROM " + TABLE_CUPON;

        SQLiteDatabase db = this.getReadableDatabase();
        mCursor = db.rawQuery(selectQuery, null);
        mInfoArray = new ArrayList<InfoCupon>();
        while (mCursor.moveToNext()) {

            mInfoClass = new InfoCupon(
                    mCursor.getString(mCursor.getColumnIndex("cupon_id")),
                    mCursor.getString(mCursor.getColumnIndex("auction_id")),
                    mCursor.getString(mCursor.getColumnIndex("store_uid")),
                    mCursor.getString(mCursor.getColumnIndex("store")),
                    mCursor.getString(mCursor.getColumnIndex("price")),
                    mCursor.getString(mCursor.getColumnIndex("time")),
                    mCursor.getString(mCursor.getColumnIndex("mainA")),
                    mCursor.getString(mCursor.getColumnIndex("mainB")),
                    mCursor.getString(mCursor.getColumnIndex("mainC")),
                    mCursor.getString(mCursor.getColumnIndex("sideA")),
                    mCursor.getString(mCursor.getColumnIndex("sideB")),
                    mCursor.getString(mCursor.getColumnIndex("sideC")),
                    mCursor.getString(mCursor.getColumnIndex("drinkA")),
                    mCursor.getString(mCursor.getColumnIndex("drinkB")),
                    mCursor.getString(mCursor.getColumnIndex("drinkC")));
            cupon_id.add(mCursor.getString(mCursor.getColumnIndex("cupon_id")));
            mInfoArray.add(mInfoClass);
        }

        mCursor.close();
        return mInfoArray;
    }

    public ArrayList<InfoCupon> doWhileCursorToArray_Purchasecupon(){
        Cursor mCursor;
        InfoCupon mInfoClass;
        ArrayList<InfoCupon> mInfoArray;
        String selectQuery = "SELECT * FROM " + TABLE_PURCHASE_CUPON;

        SQLiteDatabase db = this.getReadableDatabase();
        mCursor = db.rawQuery(selectQuery, null);
        mInfoArray = new ArrayList<InfoCupon>();
        while (mCursor.moveToNext()) {

            mInfoClass = new InfoCupon(
                    mCursor.getString(mCursor.getColumnIndex("cupon_id")),
                    "","",
                    mCursor.getString(mCursor.getColumnIndex("store")),
                    mCursor.getString(mCursor.getColumnIndex("price")),
                    mCursor.getString(mCursor.getColumnIndex("time")),
                    mCursor.getString(mCursor.getColumnIndex("mainA")),
                    mCursor.getString(mCursor.getColumnIndex("mainB")),
                    mCursor.getString(mCursor.getColumnIndex("mainC")),
                    mCursor.getString(mCursor.getColumnIndex("sideA")),
                    mCursor.getString(mCursor.getColumnIndex("sideB")),
                    mCursor.getString(mCursor.getColumnIndex("sideC")),
                    mCursor.getString(mCursor.getColumnIndex("drinkA")),
                    mCursor.getString(mCursor.getColumnIndex("drinkB")),
                    mCursor.getString(mCursor.getColumnIndex("drinkC")));
            purchase_cupon_id.add(mCursor.getString(mCursor.getColumnIndex("cupon_id")));
            mInfoArray.add(mInfoClass);
        }

        mCursor.close();
        return mInfoArray;
    }
    public void setReviewDirty(String id) {
        String updateQuery = "UPDATE " + TABLE_REVIEW + " set dirty = 'true'" + " WHERE review_id = " + id;
        SQLiteDatabase db = this.getReadableDatabase();

        db.execSQL(updateQuery);
        // Move to first row
        db.close();
        // return user
    }

    public ArrayList<InfoReview> doWhileCursorToArray_Review(){
        Cursor mCursor;
        InfoReview mInfoClass;
        ArrayList<InfoReview> mInfoArray;
        String selectQuery = "SELECT * FROM " + TABLE_REVIEW;

        SQLiteDatabase db = this.getReadableDatabase();
        mCursor = db.rawQuery(selectQuery, null);
        mInfoArray = new ArrayList<InfoReview>();
        while (mCursor.moveToNext()) {
            String dirty = mCursor.getString(mCursor.getColumnIndex("dirty"));
            if(dirty.equals("false")) {
                mInfoClass = new InfoReview(
                        mCursor.getString(mCursor.getColumnIndex("review_id")),
                        mCursor.getString(mCursor.getColumnIndex("store")),
                        mCursor.getString(mCursor.getColumnIndex("time")));
                review_id.add(mCursor.getString(mCursor.getColumnIndex("review_id")));
                mInfoArray.add(mInfoClass);
            }
        }

        mCursor.close();
        return mInfoArray;
    }

    public ArrayList<InfoWish> doWhileCursorToArray_Wish(){
        Cursor mCursor;
        InfoWish mInfoClass;
        ArrayList<InfoWish> mInfoArray;
        String selectQuery = "SELECT * FROM " + TABLE_WISH;

        SQLiteDatabase db = this.getReadableDatabase();
        mCursor = db.rawQuery(selectQuery, null);
        mInfoArray = new ArrayList<InfoWish>();
        while (mCursor.moveToNext()) {
                mInfoClass = new InfoWish(
                        mCursor.getString(mCursor.getColumnIndex("id")),
                        mCursor.getString(mCursor.getColumnIndex("store")),
                        mCursor.getString(mCursor.getColumnIndex("url")),
                        mCursor.getString(mCursor.getColumnIndex("place")),
                        mCursor.getString(mCursor.getColumnIndex("object")),
                        mCursor.getString(mCursor.getColumnIndex("air")),
                        mCursor.getString(mCursor.getColumnIndex("people"))
                );
                wish_id.add(mCursor.getString(mCursor.getColumnIndex("id")));
                mInfoArray.add(mInfoClass);
        }
        mCursor.close();
        return mInfoArray;
    }
    /**
     * Re crate database Delete all tables and create them again
     * */
    public void deleteUsers() {
        SQLiteDatabase db = this.getWritableDatabase();
        // Delete All Rows
        db.delete(TABLE_USER, null, null);
        db.close();

        Log.d(TAG, "Deleted all user info from sqlite");
    }
    public void deleteAuction(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_AUCTION, KEY_AUCTION_ID+"=?", new String[]{id});
        db.close();
        Log.d(TAG, "Deleted response :"+id);
    }

    public void deleteCupon(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CUPON, KEY_AUCTION_ID+"=?", new String[]{id});
        db.close();
        Log.d(TAG, "Deleted response :"+id);
    }


    public void deleteWish(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_WISH, KEY_ID+"=?", new String[]{id});
        db.close();
        Log.d(TAG, "Deleted response :"+id);
    }
}
