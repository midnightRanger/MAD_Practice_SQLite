package com.example.mad_practice_sqlite;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText nameET, phoneET, birthdateET;
    Button insert, select;
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nameET = findViewById(R.id.txtName);
        phoneET = findViewById(R.id.txtNumber);
        birthdateET = findViewById(R.id.txtDate);
        insert = findViewById(R.id.btnInsert);
        insert.setOnClickListener(view -> insert());
        select = findViewById(R.id.btnSelect);
        select.setOnClickListener(view -> select());

        databaseHelper = new DatabaseHelper(this);
    }

    private void insert() {
        Boolean checkInsertData = databaseHelper.insert(
                nameET.getText().toString(),
                phoneET.getText().toString(),
                birthdateET.getText().toString()
        );
        if (checkInsertData)
            Toast.makeText(getApplicationContext(),
                    "Данные успешно добавлены",
                    Toast.LENGTH_LONG).show();
        else Toast.makeText(getApplicationContext(),
                "Произошла ошибка",
                Toast.LENGTH_LONG).show();
    }

    private void select() {
        Cursor res = databaseHelper.getData();
        if (res.getCount() == 0) {
            Toast.makeText(getApplicationContext(),
                    "База данных пуста",
                    Toast.LENGTH_LONG).show();
            return;
        }
        StringBuilder buffer = new StringBuilder();
        while (res.moveToNext()) {
            String name = res.getString(0);
            String phone = res.getString(1);
            String birthdate = res.getString(2);
            String row = String.format("Имя: [%s], номер телефона: [%s], дата рождения: [%s]\n",
                    name, phone, birthdate);
            buffer.append(row);
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setCancelable(true);
        builder.setTitle("Данные пользователей");
        builder.setMessage(buffer.toString());
        builder.show();
    }
}