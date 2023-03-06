package com.example.heathcare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.heathcare.database.DBConnection;

public class RegistrationActivity extends AppCompatActivity {



    EditText eUsername,ePassword,eEmail;
    Button btn;
    TextView tv;

    Integer id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);



        eUsername = findViewById(R.id.editTextUsername);
        eEmail = findViewById(R.id.editTextEmail);
        ePassword = findViewById(R.id.editTextPassword);


        btn = findViewById(R.id.btnSubmit);
        tv = findViewById(R.id.textViewLoginPage);


        Bundle data = getIntent().getExtras();
        if(data != null){
            id = data.getInt("id");
            String userName = data.getString("userName");

            String password = data.getString("password");
            String email = data.getString("email");


            eUsername.setText(userName);

            ePassword.setText(password);
            eEmail.setText(email);
            btn.setText("Update");
        }


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userName = eUsername.getText().toString();
                String email = eEmail.getText().toString();
                String password = ePassword.getText().toString();



                DBConnection db = new DBConnection(getApplicationContext(), "healthcare", null, 1);
                if (userName.length() == 0  || email.length() == 0 || password.length() == 0) {
                    Toast.makeText(getApplicationContext(), "Please Fill All The Data Field", Toast.LENGTH_SHORT).show();

                } else {

                    if (password.length() >= 8) {
                        if(id != null){
                            db.UpdateUser(id,userName,email,password);
                            startActivity(new Intent(RegistrationActivity.this,ListActivity.class));
                        }else {
                            db.addNewUser(userName, email, password);
                            Toast.makeText(getApplicationContext(), "Record Inserted", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(RegistrationActivity.this, MainActivity.class));
                        }

                    } else {
                        Toast.makeText(getApplicationContext(), "Invalid Password", Toast.LENGTH_SHORT).show();
                    }

                }


            }

        });


        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegistrationActivity.this, MainActivity.class));
            }
        });


    }


}