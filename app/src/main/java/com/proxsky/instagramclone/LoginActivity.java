package com.proxsky.instagramclone;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.shashank.sony.fancytoastlib.FancyToast;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText etEmail, etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getSupportActionBar().setTitle("Log In");

        etEmail = findViewById(R.id.etEmailLogin);
        etPassword = findViewById(R.id.etPasswordLogin);

        etPassword.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if(keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN)
                    onClick( findViewById(R.id.btnLoginIn2));

                return false;
            }
        });

        if(ParseUser.getCurrentUser()!=null)
            ParseUser.logOut();

        findViewById(R.id.btnLoginIn2).setOnClickListener(this);

        findViewById(R.id.btnSignUp2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,SignUpActivity.class);
                startActivity(intent);
                finish();

            }
        });
    }

    public void routeLayoutClick(View view)
    {
        try
        {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),0);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {

        if(etEmail.getText().toString().equals("")||etPassword.getText().toString().equals(""))
        {
            FancyToast.makeText(LoginActivity.this,"Email or Password can not be empty!"
                    ,FancyToast.ERROR, Toast.LENGTH_LONG,false).show();
        }
        else
        {
            final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this);
            progressDialog.setMessage("Signing In!");
            progressDialog.show();
            ParseUser.logInInBackground(etEmail.getText().toString(), etPassword.getText().toString(), new LogInCallback() {
                @Override
                public void done(ParseUser user, ParseException e) {


                    if(user!=null && e==null)
                    {

                        FancyToast.makeText(LoginActivity.this,"Login successfully!"
                                ,FancyToast.SUCCESS, Toast.LENGTH_LONG,false).show();
                        transitionToSocialMediaActivity();
                    }
                    else
                    {
                        FancyToast.makeText(LoginActivity.this,e.getMessage()
                                ,FancyToast.ERROR, Toast.LENGTH_LONG,false).show();
                    }
                    progressDialog.dismiss();
                }
            });
        }
    }


    private void transitionToSocialMediaActivity()
    {
        Intent intent = new Intent(LoginActivity.this,SocialMediaActivity.class);
        startActivity(intent);
        finish();
    }

}
