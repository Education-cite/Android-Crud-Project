package com.example.heathcare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.example.heathcare.database.DBConnection;

import java.util.ArrayList;
import java.util.HashMap;

public class ListActivity extends AppCompatActivity {

    ArrayList list;

    SimpleAdapter sa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);


        DBConnection db = new DBConnection(getApplicationContext(), "healthcare", null, 1);

        list = db.getUserList();

        System.out.println(list.size());
        System.out.println("........................");


        sa = new SimpleAdapter(this,
                list,
                R.layout.categorylist,
                new String[]{"id","userName","email","password"},
                new int[]{R.id.textViewId,R.id.textViewName,R.id.textViewEmail,R.id.textViewPassword}
                ){

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {

                View v  = super.getView(position,convertView,parent);
                ImageView editBtn = v.findViewById(R.id.imageEditBtn);
                ImageView deleteBtn = v.findViewById(R.id.imageDeleteBtn);

                editBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        System.out.println(position);

                        HashMap<String,String> user;

                        try {
                            user = (HashMap<String, String>) list.get(position);
                            System.out.println(user);

                            Intent intent = new Intent(ListActivity.this,RegistrationActivity.class);
                            intent.putExtra("id",Integer.parseInt(user.get("id")));
                            intent.putExtra("userName",user.get("userName"));
                            intent.putExtra("email",user.get("email"));
                            intent.putExtra("password",user.get("password"));
                            startActivity(intent);

                        }catch (Exception e){

                        }
                    }
                });

                deleteBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        System.out.println(position);

                        HashMap<String,String> user;

                        try {
                            user = (HashMap<String, String>) list.get(position);

                            boolean delete = db.deleteUser(Integer.parseInt(user.get("id")));
                            if(delete){
                                list.remove(position);
                                notifyDataSetChanged();
                            }

                            String message = delete?"Successfully deleted" : "Failed to delete";
                            Toast.makeText(getApplicationContext(),message,Toast.LENGTH_SHORT).show();
                            System.out.println(user);

                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                });



                return v;
            }
        };

        ListView lv = findViewById(R.id.listView);
        lv.setAdapter(sa);

    }





}