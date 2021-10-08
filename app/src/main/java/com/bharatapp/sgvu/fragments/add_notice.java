package com.bharatapp.sgvu.fragments;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.bharatapp.sgvu.R;
import com.bharatapp.sgvu.activities.login;
import com.bharatapp.sgvu.retrofit.RetrofitClient;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class add_notice extends Fragment {

View view;
EditText title,short_des,full_des;
String ntitle,nshort_des,nfull_des,nimag;
ImageButton img;
String img1;
Button add;
int nid=1;
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_add_notice, container, false);
        title=view.findViewById(R.id.title);
        short_des=view.findViewById(R.id.s_des);
        full_des=view.findViewById(R.id.f_des);
        img=view.findViewById(R.id.upload_img);
        add=view.findViewById(R.id.add_notice);
        retrofitClient=new RetrofitClient();
        requestStoragePermission();
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNotice();
            }
        });
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFileChooser();
            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadImg(img1,nid);
            }
        });
        return view;
    }

    private void uploadImg(String img1,int nid) {
        Toast.makeText(getActivity(), "dfgfjkdhkjn", Toast.LENGTH_SHORT).show();
        sharedPreferences= getActivity().getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
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
            Toast.makeText(getActivity(), "Login Again", Toast.LENGTH_SHORT).show();
            Intent i=new Intent(getActivity(), login.class);
            startActivity(i);
        }
        JsonObject image=new JsonObject();
        image.addProperty("nid",nid);
       image.addProperty("img",img1);
        image.add("auth",auth);

        retrofitClient.getWebService().updatenoticeimg(image).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
               Log.d("bharat123",response.body());
                if(response.isSuccessful()) {
                    try {
                        JSONObject obj = new JSONObject(response.body());
                        if(Integer.parseInt(obj.get("code").toString())==200)
                        {
                            Toast.makeText(getActivity(),obj.getString("message"), Toast.LENGTH_SHORT).show();

                        }
                        else if(Integer.parseInt(obj.get("code").toString())==400) {
                            Toast.makeText(getActivity(),obj.getString("message"), Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
                else
                {
                    Toast.makeText(getActivity(), "Something went wrong", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

                Toast.makeText(getActivity(), t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void addNotice() {
         ntitle=title.getText().toString();
        nshort_des=short_des.getText().toString();
        nfull_des=full_des.getText().toString();

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
                bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), filePath);
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
        Cursor cursor = getActivity().getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        String document_id = cursor.getString(0);
        document_id = document_id.substring(document_id.lastIndexOf(":") + 1);
        cursor.close();

        cursor = getActivity().getContentResolver().query(
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                null, MediaStore.Images.Media._ID + " = ? ", new String[]{document_id}, null);
        cursor.moveToFirst();
        String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
        cursor.close();

        return path;
    }
    //Requesting permission
    private void requestStoragePermission() {
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
            return;

        if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE)) {
            //If the user has denied the permission previously your code will come to this block
            //Here you can explain why you need this permission
            //Explain here why you need this permission
        }
        //And finally ask for the permission
        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
    }
    @SuppressLint("MissingSuperCall")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        //Checking the request code of our request

        if (requestCode == STORAGE_PERMISSION_CODE) {

            //If permission is granted
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //Displaying a toast
                Toast.makeText(getActivity(), "Permission granted now you can read the storage", Toast.LENGTH_LONG).show();
            } else {
                //Displaying another toast if permission is not granted
                Toast.makeText(getActivity(), "Oops you just denied the permission", Toast.LENGTH_LONG).show();
            }
        }
    }
}