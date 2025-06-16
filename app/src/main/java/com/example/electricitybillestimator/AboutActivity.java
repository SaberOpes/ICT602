package com.example.electricitybillestimator;

import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class AboutActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        setTitle("About");

        // Set student info
        TextView info = findViewById(R.id.textViewInfo);
        info.setText("Name: Muhammad Sabri Bin Mohd Azhar\n" +
                "Student ID: 2023114231\n" +
                "Course: ICT602 - MOBILE TECHNOLOGY AND DEVELOPMENT\n" +
                "© 2025");

        // Set clickable GitHub link
        TextView url = findViewById(R.id.textViewURL);
        url.setText(Html.fromHtml("<a href='https://github.com/SaberOpes'>https://github.com/SaberOpes</a>"));
        url.setMovementMethod(LinkMovementMethod.getInstance());
    }
}
