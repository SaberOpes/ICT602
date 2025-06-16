package com.example.electricitybillestimator;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class DetailActivity extends AppCompatActivity {

    TextView month, unit, total, rebate, finalCost;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        setTitle("Bill Details");

        // Bind UI elements
        month = findViewById(R.id.txtMonth);
        unit = findViewById(R.id.txtUnit);
        total = findViewById(R.id.txtTotal);
        rebate = findViewById(R.id.txtRebate);
        finalCost = findViewById(R.id.txtFinal);

        db = new DatabaseHelper(this);
        int id = getIntent().getIntExtra("id", -1);
        if (id != -1) {
            Cursor c = db.getDataById(id);
            if (c.moveToFirst()) {
                String m = c.getString(1);
                int u = c.getInt(2);
                double t = c.getDouble(3);
                double r = c.getDouble(4);
                double f = c.getDouble(5);

                month.setText("Month: " + m);
                unit.setText("Units Used: " + u + " kWh");
                total.setText("Total Charges: RM " + String.format("%.2f", t));
                rebate.setText("Rebate: " + String.format("%.2f", r) + " %");
                finalCost.setText("Final Cost: RM " + String.format("%.2f", f));
            }
        }
    }
}
