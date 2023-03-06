package com.example.heathcare;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.heathcare.database.DBConnection;

public class MainActivity extends AppCompatActivity {

    EditText eUsername,ePassword;

    Button btn;

    TextView tv;




    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        eUsername = findViewById(R.id.editTextUsername);
        ePassword = findViewById(R.id.editTextPassword);
        tv = findViewById(R.id.textViewNewUser);
        btn = findViewById(R.id.btnSubmitLogin);

        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, RegistrationActivity.class));

            }
        });



        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userName = eUsername.getText().toString();
                String password = ePassword.getText().toString();
                System.out.println("hit");

                DBConnection db = new DBConnection(getApplicationContext(), "healthcare", null, 1);

                System.out.println(userName + " "+ password);
                if(userName.length()==0 || password.length()==0){
                    System.out.println("hit1");

                    Toast.makeText(getApplicationContext(), "Please Fill All The Data Field", Toast.LENGTH_SHORT).show();
                }else {
                    System.out.println("hit2---------");
                    if( db.login(userName,password) == 1){

                        Toast.makeText(getApplicationContext(), "Login Success Full", Toast.LENGTH_SHORT).show();

                        startActivity(new Intent(MainActivity.this, HomeActivity.class));

                    }else {
                        Toast.makeText(getApplicationContext(), "Invalid password", Toast.LENGTH_SHORT).show();

                    }

                }
            }
        });


    }
}