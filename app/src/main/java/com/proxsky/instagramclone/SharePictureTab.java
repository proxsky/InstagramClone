package com.proxsky.instagramclone;


import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.SaveCallback;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.io.ByteArrayOutputStream;


/**
 * A simple {@link Fragment} subclass.
 */
public class SharePictureTab extends Fragment implements View.OnClickListener {

    private ImageView imgShare;
    private EditText edtDescription;
    private Button btnShareImage;
    Bitmap receivedImageBitmap;

    public SharePictureTab() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_share_picture_tab, container, false);

        imgShare = view.findViewById(R.id.imgShare);
        edtDescription = view.findViewById(R.id.edtDescription);
        btnShareImage = view.findViewById(R.id.btnShareImage);

        imgShare.setOnClickListener(SharePictureTab.this);
        btnShareImage.setOnClickListener(SharePictureTab.this);

        return view;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId())
        {
            case R.id.imgShare:

                if(android.os.Build.VERSION.SDK_INT >= 23
                && ActivityCompat.checkSelfPermission(getContext(),
                        Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED){
                    requestPermissions(new String[]
                            {Manifest.permission.READ_EXTERNAL_STORAGE},
                            1000);
                }
                else
                {
                    getChosenImage();
                }

                break;
            case R.id.btnShareImage:

                if(receivedImageBitmap != null)
                {
                    if(edtDescription.getText().toString().equals(""))
                    {
                        FancyToast.makeText(getContext(),
                                "ERROR: You must specify a description!",
                                Toast.LENGTH_SHORT,FancyToast.ERROR,false).show();
                    }
                    else
                    {
                        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                        receivedImageBitmap.compress(Bitmap.CompressFormat.PNG,100,byteArrayOutputStream);
                        byte[] bytes = byteArrayOutputStream.toByteArray();
                        ParseFile parseFile = new ParseFile("pic.png",bytes);
                        ParseObject parseObject = new ParseObject("Photo");
                        parseObject.put("picture",parseFile);
                        parseObject.put("image_des",edtDescription.getText().toString());
                        final ProgressDialog dialog = new ProgressDialog(getContext());
                        dialog.setMessage("Loading");
                        dialog.show();
                        parseObject.saveInBackground(new SaveCallback() {
                            @Override
                            public void done(ParseException e) {
                                if(e==null)
                                    FancyToast.makeText(getContext(),
                                            "Done!",
                                            Toast.LENGTH_SHORT,FancyToast.SUCCESS,false).show();
                                    else
                                FancyToast.makeText(getContext(),
                                        "Unknown error!",
                                        Toast.LENGTH_SHORT,FancyToast.ERROR,false).show();

                                    dialog.dismiss();
                            }
                        });
                    }
                }
                else
                {
                    FancyToast.makeText(getContext(),
                            "Must select an image!",
                            Toast.LENGTH_SHORT,FancyToast.ERROR,false).show();

                }

                break;


        }

    }

    private void getChosenImage() {

       // FancyToast.makeText(getContext(),
             //   "Now we can access the images",
             //   Toast.LENGTH_SHORT,FancyToast.SUCCESS,false).show();

        Intent intent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent,2000);


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == 1000)
        {
            if(grantResults.length > 0 && grantResults[0]
            == PackageManager.PERMISSION_GRANTED)
            {
                getChosenImage();
            }
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 2000)
        {
            if(resultCode == Activity.RESULT_OK)
            {
                try
                {
                    Uri selectedImage = data.getData();
                    String[] filePathColumn =
                            {
                                    MediaStore.Images.Media.DATA
                            };
                    Cursor cursor = getActivity().getContentResolver().query(selectedImage,
                            filePathColumn,null,null,null);
                    cursor.moveToFirst();
                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    String picturePath = cursor.getString(columnIndex);
                    receivedImageBitmap = BitmapFactory.decodeFile(picturePath);
                    imgShare.setImageBitmap(receivedImageBitmap);

                }
                catch(Exception e)
                {
                    e.printStackTrace();
                }


            }
        }

    }
}
