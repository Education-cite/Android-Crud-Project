package com.example.heathcare;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.heathcare.database.DBConnection;
import com.example.heathcare.entity.Category;

public class CategoryActivity extends AppCompatActivity {


    EditText edCateName, edCatDesc;
    Button btn,btnCan;




    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        edCateName = findViewById(R.id.editTextCatName);
        edCatDesc = findViewById(R.id.editTextCatDesc);

        btn= findViewById(R.id.btnSave);
        btnCan =findViewById(R.id.btnCancel);

        DBConnection db = new DBConnection(getApplicationContext(), "healthcare", null, 1);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String categoryName = edCateName.getText().toString();
                String categoryDesc = edCatDesc.getText().toString();


                Category category = new Category(categoryName,categoryDesc);
                db.addCategory(category);
                Toast.makeText(getApplicationContext(), "Add category successfully", Toast.LENGTH_SHORT).show();
            }
        });

    }


}