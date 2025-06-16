package com.example.electricitybillestimator;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    FloatingActionButton fab;
    Button btnAbout;
    DatabaseHelper db;
    ArrayList<String> bills;
    ArrayList<Integer> ids;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Electricity Bill Estimator");
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.listView);
        fab = findViewById(R.id.fab);
        btnAbout = findViewById(R.id.btnAbout);
        db = new DatabaseHelper(this);
        bills = new ArrayList<>();
        ids = new ArrayList<>();

        fab.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddBillActivity.class);
            startActivity(intent);
        });

        btnAbout.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AboutActivity.class);
            startActivity(intent);
        });

        listView.setOnItemClickListener((parent, view, position, id) -> {
            Intent intent = new Intent(MainActivity.this, DetailActivity.class);
            intent.putExtra("id", ids.get(position));
            startActivity(intent);
        });

        listView.setOnItemLongClickListener((parent, view, position, id) -> {
            int selectedId = ids.get(position);
            new android.app.AlertDialog.Builder(MainActivity.this)
                    .setTitle("Delete")
                    .setMessage("Are you sure you want to delete this record?")
                    .setPositiveButton("Yes", (dialog, which) -> {
                        boolean deleted = db.deleteById(selectedId);
                        if (deleted) {
                            Toast.makeText(MainActivity.this, "Deleted", Toast.LENGTH_SHORT).show();
                            loadData(); // refresh list
                        } else {
                            Toast.makeText(MainActivity.this, "Failed to delete", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .setNegativeButton("Cancel", null)
                    .show();
            return true;
        });


        loadData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData();
    }

    private void loadData() {
        bills.clear();
        ids.clear();
        Cursor cursor = db.getAllData();
        while (cursor.moveToNext()) {
            ids.add(cursor.getInt(0));
            String month = cursor.getString(1);
            double finalCost = cursor.getDouble(5);
            bills.add(month + " - RM " + String.format("%.2f", finalCost));
        }
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, bills);
        listView.setAdapter(adapter);
    }
}
