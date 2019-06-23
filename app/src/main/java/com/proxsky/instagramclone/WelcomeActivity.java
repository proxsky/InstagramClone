package com.proxsky.instagramclone;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.parse.ParseUser;

import org.w3c.dom.Text;

public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        TextView txtWelcome = findViewById(R.id.txtWelcome);
        txtWelcome.setText(getIntent().getStringExtra("username"));

        //ParseUser.getCurrentUser().get("username");

        findViewById(R.id.buttonLogout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ParseUser.logOut();
                finish();

            }
        });
    }
}
