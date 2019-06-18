package com.proxsky.instagramclone;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.SaveCallback;
import com.shashank.sony.fancytoastlib.FancyToast;

public class MainActivity extends AppCompatActivity {

    private EditText name, ps, pp, ks, kp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        name = findViewById(R.id.editText_name);
        ps = findViewById(R.id.editText_ps);
        pp = findViewById(R.id.editText_pp);
        ks = findViewById(R.id.editText_ks);
        kp = findViewById(R.id.editText_kp);
    }

    public void helloWorldTapped(View view)
    {
        /*
        ParseObject boxer = new ParseObject("Boxer");
        boxer.put("punch_speed",200);
        boxer.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if(e==null)
                {
                    Toast.makeText(MainActivity.this,"Boxer object is saved successfully!",Toast.LENGTH_LONG).show();
                }
            }
        });
        */

        try {


            final ParseObject kickBoxer = new ParseObject("KickBoxer");
            kickBoxer.put("name", name.getText().toString());
            kickBoxer.put("punchSpeed", Integer.parseInt(ps.getText().toString()));
            kickBoxer.put("punchPower", Integer.parseInt(pp.getText().toString()));
            kickBoxer.put("kickSpeed", Integer.parseInt(ks.getText().toString()));
            kickBoxer.put("kickPower", Integer.parseInt(kp.getText().toString()));

            kickBoxer.saveInBackground(new SaveCallback() {
                @Override
                public void done(ParseException e) {
                    if (e == null)
                        FancyToast.makeText(MainActivity.this, kickBoxer.get("name")
                                + " is saved to the server!", Toast.LENGTH_SHORT, FancyToast.SUCCESS, false).show();
                    else
                        Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                }
            });

        }
        catch(Exception e)
        {
            FancyToast.makeText(this,e.getMessage(),Toast.LENGTH_SHORT,FancyToast.ERROR,false).show();
        }

    }
}
