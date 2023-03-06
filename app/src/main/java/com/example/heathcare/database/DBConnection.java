package com.example.heathcare.database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.heathcare.entity.Category;

import java.util.ArrayList;
import java.util.HashMap;

public class DBConnection extends SQLiteOpenHelper {


    public DBConnection(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public DBConnection(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version, @Nullable DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }

//    public DBConnection(@Nullable Context context, @Nullable String name, int version, @NonNull SQLiteDatabase.OpenParams openParams) {
//        super(context, name, version, openParams);
//    }


    private  static  final  String TABLE_NAME = "users";

    private  static  final  String ID_COL = "id";

    private  static  final  String USER_NAME_COL = "userName";
    private  static  final  String PASSWORD_COL = "password";
    private  static  final  String EMAIL_COl = "email";

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String query = "CREATE TABLE " +
                TABLE_NAME +"("+
                ID_COL+" INTEGER PRIMARY KEY AUTOINCREMENT,"+
                USER_NAME_COL +" TEXT,"+
                EMAIL_COl +" TEXT,"+
                PASSWORD_COL+ " TEXT)";

        sqLiteDatabase.execSQL(query);


        String query2 = "CREATE TABLE " +
                "category" +"("+
                ID_COL+" INTEGER PRIMARY KEY AUTOINCREMENT,"+
                "catName" +" TEXT,"+
                "catDesc" +" TEXT)";

        sqLiteDatabase.execSQL(query2);



    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public  void  addCategory(Category category){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put("catName",category.getCatName());
        values.put("catDesc",category.getCatDesc());

        db.insert("category",null,values);
        db.close();

    }


    public void addNewUser(String userName,String email,String password){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(USER_NAME_COL,userName);
        values.put(EMAIL_COl,email);
        values.put(PASSWORD_COL,password);

        db.insert(TABLE_NAME,null,values);

    }

    public boolean UpdateUser(Integer id,String userName,String email,String password){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(USER_NAME_COL,userName);
        values.put(EMAIL_COl,email);
        values.put(PASSWORD_COL,password);

        long result = db.update(TABLE_NAME,values,"id=?",new String[]{id+""});
        db.close();

        return result > 0;

    }

    public  int login(String userName, String password){
        String[] arr = new String[2];
        arr[0] =userName;
        arr[1] = password;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery("select * from users where userName=? and password=?",arr);

        if(c.moveToFirst()){
            return 1;
        }
        return 0;

    }

    public ArrayList<HashMap<String,String>> getUserList(){
        HashMap<String,String> user;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery("select * from users",null);
        ArrayList<HashMap<String,String>> userList = new ArrayList<>(c.getCount());

        if(c.moveToFirst()){
            do{
                user = new HashMap<>();
                user.put("id",c.getString(0));
                user.put("userName",c.getString(1));
                user.put("email",c.getString(2));
                user.put("password",c.getString(3));

                userList.add(user);
            } while (c.moveToNext());
        }

        db.close();
        return  userList;
    }


    public  boolean deleteUser(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        int rowCount = db. delete(TABLE_NAME,"id=?",new String[]{id+""});
        db.close();
        return rowCount > 0;
    }




}
