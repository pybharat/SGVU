package com.bharatapp.sgvu.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.bharatapp.sgvu.R;
import com.bharatapp.sgvu.process;
import com.bharatapp.sgvu.retrofit.RetrofitClient;
import com.bumptech.glide.Glide;
import com.google.gson.JsonObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class update_profile extends AppCompatActivity {
String uname,ucontact,uemail,uimg,img1;
EditText name,contact,email;
Button upload,update;
ImageButton img;
process process;
RetrofitClient retrofitClient;
SharedPreferences sharedPreferences;
private  static  final String SHARED_PREF_NAME="sgvu";
private  static  final String KEY_USERID="userid";
private  static  final String KEY_TOKEN="token";
private int PICK_IMAGE_REQUEST = 1;
private static final int STORAGE_PERMISSION_CODE = 123;
private Bitmap bitmap;
private Uri filePath;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);
        process=new process(update_profile.this);
        name=findViewById(R.id.name);
        contact=findViewById(R.id.contact1);
        email=findViewById(R.id.email7);
        upload=findViewById(R.id.uploadimg);
        update=findViewById(R.id.update1);
        img=findViewById(R.id.upload_img);
        Bundle bundle=getIntent().getExtras();
        if(bundle!= null)
        {
         uname=bundle.getString("name");
         ucontact=bundle.getString("contact");
         uemail=bundle.getString("email");
         uimg=bundle.getString("img_url");
        }
        if(!uname.isEmpty())
        {
            name.setText(uname);
        }
        if(!ucontact.isEmpty())
        {
            contact.setText(ucontact);
        }
        if(!uemail.isEmpty())
        {
            email.setText(uemail);
        }
        if(!uimg.isEmpty())
        {
            Glide.with(update_profile.this)
                    .load(uimg)
                    .centerCrop()
                    .into(img);
        }
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFileChooser();
                upload.setVisibility(View.VISIBLE);
            }
        });
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                process.show();
                uploadImg(img1);
            }
        });
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                process.show();
                uploadtext();
            }
        });
    }

    private void uploadtext() {
        uname=name.getText().toString();
        ucontact=contact.getText().toString();
        uemail=email.getText().toString();
        if(uname.isEmpty())
        {
            name.requestFocus();
            name.setError("Enter Title");
            return;
        }
        else if(ucontact.isEmpty())
        {
            contact.requestFocus();
            contact.setError("Enter Short Description.");
        }
        else if(uemail.isEmpty())
        {
            email.requestFocus();
            email.setError("Enter Full Description.");
        }
        if(img1==null) {
            uimg = "default.jpeg";
        }
        else
        {
            uimg=img1;
        }
        sharedPreferences= getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        int userid=sharedPreferences.getInt(KEY_USERID,0);
        String token=sharedPreferences.getString(KEY_TOKEN,null);
        JsonObject auth=new JsonObject();

        if(userid != 0 || token!=null)
        {
            auth.addProperty("id",userid);
            auth.addProperty("token",token);
        }
        else
        {
            Toast.makeText(update_profile.this, "Login Again", Toast.LENGTH_SHORT).show();
            Intent i=new Intent(update_profile.this, login.class);
            startActivity(i);
        }
        JsonObject profiledata=new JsonObject();
        profiledata.addProperty("name",uname);
        profiledata.addProperty("contact",ucontact);
        profiledata.addProperty("email",uemail);
        profiledata.addProperty("img_url",uimg);
        profiledata.add("auth",auth);
        Log.d("bharat123",profiledata.toString());
    }

    private void uploadImg(String img1) {
        sharedPreferences= getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        int userid=sharedPreferences.getInt(KEY_USERID,0);
        String token=sharedPreferences.getString(KEY_TOKEN,null);
        JsonObject auth=new JsonObject();

        if(userid != 0 || token!=null)
        {
            auth.addProperty("id",userid);
            auth.addProperty("token",token);
        }
        else
        {
            Toast.makeText(this, "Login Again", Toast.LENGTH_SHORT).show();
            Intent i=new Intent(update_profile.this, login.class);
            startActivity(i);
        }
        JsonObject image=new JsonObject();
        image.addProperty("nid",userid);
        image.addProperty("img",img1);
        image.add("auth",auth);
        Log.d("bharat123",image.toString());
    }

    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                img.setImageBitmap(bitmap);
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                byte[] byteArray = byteArrayOutputStream .toByteArray();
                img1 = Base64.encodeToString(byteArray, Base64.DEFAULT);


            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public String getPath(Uri uri) {
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        String document_id = cursor.getString(0);
        document_id = document_id.substring(document_id.lastIndexOf(":") + 1);
        cursor.close();

        cursor = getContentResolver().query(
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                null, MediaStore.Images.Media._ID + " = ? ", new String[]{document_id}, null);
        cursor.moveToFirst();
        String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
        cursor.close();

        return path;
    }
    //Requesting permission
    private void requestStoragePermission() {
        if (ContextCompat.checkSelfPermission(update_profile.this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
            return;

        if (ActivityCompat.shouldShowRequestPermissionRationale(update_profile.this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            //If the user has denied the permission previously your code will come to this block
            //Here you can explain why you need this permission
            //Explain here why you need this permission
        }
        //And finally ask for the permission
        ActivityCompat.requestPermissions(update_profile.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
    }
    @SuppressLint("MissingSuperCall")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        //Checking the request code of our request

        if (requestCode == STORAGE_PERMISSION_CODE) {

            //If permission is granted
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //Displaying a toast
                Toast.makeText(update_profile.this, "Permission granted now you can read the storage", Toast.LENGTH_LONG).show();
            } else {
                //Displaying another toast if permission is not granted
                Toast.makeText(update_profile.this, "Oops you just denied the permission", Toast.LENGTH_LONG).show();
            }
        }
    }
}