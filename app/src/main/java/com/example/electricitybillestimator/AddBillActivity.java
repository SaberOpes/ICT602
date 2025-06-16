package com.example.electricitybillestimator;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

public class AddBillActivity extends AppCompatActivity {

    EditText unitInput, rebateInput;
    Spinner monthSpinner;
    Button calculateBtn;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_bill);
        setTitle("Add New Bill");

        // Initialize views
        unitInput = findViewById(R.id.editUnit);
        rebateInput = findViewById(R.id.editRebate);
        monthSpinner = findViewById(R.id.spinnerMonth);
        calculateBtn = findViewById(R.id.buttonCalculate);
        db = new DatabaseHelper(this);

        // Populate the month spinner
        String[] months = {"January", "February", "March", "April", "May", "June",
                "July", "August", "September", "October", "November", "December"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, months);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        monthSpinner.setAdapter(adapter);

        // Button click logic
        calculateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String unitText = unitInput.getText().toString().trim();
                String rebateText = rebateInput.getText().toString().trim();

                if (unitText.isEmpty() || rebateText.isEmpty()) {
                    Toast.makeText(AddBillActivity.this, "Please enter all fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                int unit = Integer.parseInt(unitText);
                double rebatePercent = Double.parseDouble(rebateText);
                if (rebatePercent < 0 || rebatePercent > 5) {
                    Toast.makeText(AddBillActivity.this, "Rebate must be between 0% and 5%", Toast.LENGTH_SHORT).show();
                    return;
                }

                String month = monthSpinner.getSelectedItem().toString();
                double total = calculateCharges(unit);
                double rebate = total * (rebatePercent / 100);
                double finalCost = total - rebate;

                boolean inserted = db.insertData(month, unit, total, rebatePercent, finalCost);
                if (inserted) {
                    Toast.makeText(AddBillActivity.this, "Saved Successfully", Toast.LENGTH_SHORT).show();
                    finish(); // Go back to main screen
                } else {
                    Toast.makeText(AddBillActivity.this, "Error saving data", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private double calculateCharges(int unit) {
        double cost = 0.0;
        if (unit <= 200) {
            cost = unit * 0.218;
        } else if (unit <= 300) {
            cost = 200 * 0.218 + (unit - 200) * 0.334;
        } else if (unit <= 600) {
            cost = 200 * 0.218 + 100 * 0.334 + (unit - 300) * 0.516;
        } else {
            cost = 200 * 0.218 + 100 * 0.334 + 300 * 0.516 + (unit - 600) * 0.546;
        }
        return cost;
    }
}
