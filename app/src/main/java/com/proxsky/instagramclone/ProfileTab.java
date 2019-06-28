package com.proxsky.instagramclone;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.shashank.sony.fancytoastlib.FancyToast;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileTab extends Fragment {

    private EditText edtProfileName, edtProfileBio,
    edtProfileProfession, edtProfileHobbies, edtProfileFavSport;

    private Button btnUpdateinfo;

    public ProfileTab() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile_tab, container, false);
        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        final ParseUser parseUesr = ParseUser.getCurrentUser();

        edtProfileName = view.findViewById(R.id.edtProfileName);
        edtProfileBio = view.findViewById(R.id.edtBio);
        edtProfileProfession = view.findViewById(R.id.edtProfession);
        edtProfileHobbies = view.findViewById(R.id.edtHobbies);
        edtProfileFavSport = view.findViewById(R.id.editSport);

        if(parseUesr.get("profileName") == null)
            edtProfileName.setText("");
        else
            edtProfileName.setText(parseUesr.get("profileName")+"");

        if(parseUesr.get("profileBio") == null)
            edtProfileName.setText("");
        else
            edtProfileBio.setText(parseUesr.get("profileBio")+"");

        if(parseUesr.get("profileProfession") == null)
            edtProfileName.setText("");
        else
            edtProfileProfession.setText(parseUesr.get("profileProfession")+"");

        if(parseUesr.get("profileHobbies") == null)
            edtProfileName.setText("");
        else
            edtProfileHobbies.setText(parseUesr.get("profileHobbies")+"");

        if(parseUesr.get("profileFavSport") == null)
            edtProfileName.setText("");
        else
          edtProfileFavSport.setText(parseUesr.get("profileFavSport")+"");

        btnUpdateinfo = view.findViewById(R.id.btnUpdateInfo);





        btnUpdateinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                parseUesr.put("profileName",edtProfileName.getText().toString());
                parseUesr.put("profileBio",edtProfileBio.getText().toString());
                parseUesr.put("profileProfession",edtProfileProfession.getText().toString());
                parseUesr.put("profileHobbies",edtProfileHobbies.getText().toString());
                parseUesr.put("profileFavSport",edtProfileFavSport.getText().toString());

                progressDialog.setMessage("Updating");
                progressDialog.show();

                parseUesr.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {



                        if(e==null)
                        {
                            FancyToast.makeText(getContext(),"Successfuly Updated!"
                                    ,FancyToast.SUCCESS, Toast.LENGTH_SHORT,false).show();
                        }
                        else
                        {
                            FancyToast.makeText(getContext(),e.getMessage()
                                    ,FancyToast.ERROR, Toast.LENGTH_SHORT,false).show();
                        }

                        progressDialog.dismiss();
                    }
                });

            }
        });

        return view;
    }

}
