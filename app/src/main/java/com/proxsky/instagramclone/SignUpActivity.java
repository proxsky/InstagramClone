package com.proxsky.instagramclone;

import android.app.ActionBar;
import android.app.ProgressDialog;
import android.content.Intent;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.concurrent.ExecutionException;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText edtEmailSignUp, edtUsernameSignUp, edtPasswordSignup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        getSupportActionBar().setTitle("Sign Up");

        edtEmailSignUp = findViewById(R.id.etEmailSignUp);
        edtUsernameSignUp = findViewById(R.id.etUserNameSignUp);
        edtPasswordSignup = findViewById(R.id.etPasswordSignUp);

        edtPasswordSignup.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if(keyCode == KeyEvent.KEYCODE_ENTER && event.getAction()==KeyEvent.ACTION_DOWN)
                {
                    onClick( findViewById(R.id.bSignUp));
                }
                return false;
            }
        });

        findViewById(R.id.bSignUp).setOnClickListener(this);

        findViewById(R.id.bLoginIn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(SignUpActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();

            }
        });

    }

    public void onClick(View view)
    {
        ParseUser appUser = new ParseUser();

        if(edtEmailSignUp.getText().toString().equals("")
                ||edtUsernameSignUp.getText().toString().equals("")
                || edtPasswordSignup.getText().toString().equals(""))
        {
            FancyToast.makeText(SignUpActivity.this,"Email, Username, or Password is empty!"
                    ,FancyToast.ERROR, Toast.LENGTH_LONG,false).show();
        }
        else {
            appUser.setEmail(edtEmailSignUp.getText().toString());
            appUser.setUsername(edtUsernameSignUp.getText().toString());
            appUser.setPassword(edtPasswordSignup.getText().toString());

            final ProgressDialog progressDialog = new ProgressDialog(SignUpActivity.this);
            progressDialog.setMessage("Signing up " + edtUsernameSignUp.getText());
            progressDialog.show();

            appUser.signUpInBackground(new SignUpCallback() {
                @Override
                public void done(ParseException e) {
                    if (e == null)
                        FancyToast.makeText(SignUpActivity.this, "Signed up successfully!"
                                , FancyToast.SUCCESS, Toast.LENGTH_LONG, false).show();
                    else
                        FancyToast.makeText(SignUpActivity.this, e.getMessage()
                                , FancyToast.ERROR, Toast.LENGTH_LONG, false).show();

                    progressDialog.dismiss();
                }
            });
        }
        if(ParseUser.getCurrentUser()!=null)
            ParseUser.logOut();
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
}
