package com.proxsky.instagramclone;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;
import com.shashank.sony.fancytoastlib.FancyToast;

public class SignUpLoginActivity extends AppCompatActivity {

    private EditText edtUserNameSignup,edtUserNameLogin,
    edtPasswordSignup, edtPasswordLogin;

    private Button btnSignup, btnLogin;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_login_activity);

        edtUserNameLogin = findViewById(R.id.edtUsernameLogin);
        edtUserNameSignup = findViewById(R.id.edtUsernameSignup);
        edtPasswordLogin = findViewById(R.id.etPasswordLogin);
        edtPasswordSignup = findViewById(R.id.edtPasswordSignup);

        btnSignup = findViewById(R.id.btnSignup);
        btnLogin = findViewById(R.id.btnLogin);

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ParseUser appUser = new ParseUser();
                appUser.setUsername(edtUserNameSignup.getText().toString());
                appUser.setPassword(edtPasswordSignup.getText().toString());

                appUser.signUpInBackground(new SignUpCallback() {
                    @Override
                    public void done(ParseException e) {
                        if(e==null)
                            FancyToast.makeText(SignUpLoginActivity.this,"Signed up successfully!"
                                    ,FancyToast.SUCCESS, Toast.LENGTH_LONG,false).show();
                        else
                            FancyToast.makeText(SignUpLoginActivity.this,e.getMessage()
                                    ,FancyToast.ERROR, Toast.LENGTH_LONG,false).show();
                    }
                });

            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ParseUser.logInInBackground(edtUserNameLogin.getText().toString(),
                        edtPasswordLogin.getText().toString(), new LogInCallback() {
                            @Override
                            public void done(ParseUser user, ParseException e) {
                                if(user != null && e == null)
                                {
                                    FancyToast.makeText(SignUpLoginActivity.this,"Login successfully!"
                                           ,FancyToast.SUCCESS, Toast.LENGTH_LONG,false).show();

                                    Intent intent = new Intent(SignUpLoginActivity.this,WelcomeActivity.class);
                                    intent.putExtra("username", user.get("username").toString());
                                    startActivity(intent);


                                }
                                else
                                {
                                   FancyToast.makeText(SignUpLoginActivity.this,e.getMessage()
                                           ,FancyToast.ERROR, Toast.LENGTH_LONG,false).show();

                                }

                            }
                        });

            }
        });

    }
}
