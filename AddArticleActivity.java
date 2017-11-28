package com.pidev.esprit.pidevmobile;


import android.app.DownloadManager;
import android.content.AbstractThreadedSyncAdapter;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by esprit on 25/11/2017.
 */

public class AddArticleActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnAdd, btnChoose;
    EditText name, body, image;
    ImageView imageView;
    String url = "http://10.0.2.2:18080/pidev-web/rest/article";
    AlertDialog.Builder builder;
    private final int IMG_REQUEST = 1;
    private Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_article);
        final Context context = getApplicationContext();
        LinearLayout linear = (LinearLayout) findViewById(R.id.add_linearLayout);


        imageView = (ImageView) findViewById(R.id.ivImage);
        btnChoose = (Button) findViewById(R.id.btnChooseImage);
        btnAdd = (Button) findViewById(R.id.btnAdd);
        name = (EditText) findViewById(R.id.artcName);
        body = (EditText) findViewById(R.id.artcBody);
        image = (EditText) findViewById(R.id.artcImage);


        btnChoose.setOnClickListener(this);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String valueName, valueBody, valueImage;
                valueName = name.getText().toString().trim();
                valueBody = body.getText().toString().trim();
                valueImage = image.getText().toString().trim();



                Map<String, String> params = new HashMap<String, String>();
                params.put("name", valueName);
                params.put("body", valueBody);
                params.put("image", "http://10.0.2.2:8081/upload/"+"valueImage.trim()"+".jpg");
                params.put("date","11111");
                params.put("favourite","true");
                params.put("other", "xx");
                params.put("user", null);


                JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(params), new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Context context = getApplicationContext();
                        CharSequence text ="it's ok check ur database";
                        int duration = Toast.LENGTH_LONG;

                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        Context context = getApplicationContext();
                        CharSequence text = error.getMessage();
                        int duration = Toast.LENGTH_LONG;

                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();
                    }
                }) {
                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        HashMap<String, String> headers = new HashMap<String, String>();
                        headers.put("Content-Type", "application/json");

                        return headers;
                    }
                };

                MySingleton.getInstance(AddArticleActivity.this).addToRequestQue(request);
            }
        });

    }


    public void selectmage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, IMG_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        System.out.println(requestCode + " ///******//// " + resultCode);
        if (requestCode == IMG_REQUEST && resultCode == RESULT_OK) {
            Uri path = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), path);
                imageView.setImageBitmap(bitmap);
                imageView.setVisibility(View.VISIBLE);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btnChooseImage:
                selectmage();
                break;
//            case R.id.btnAdd:
//                uplodeImage();
//                break;
        }
    }

    private void uplodeImage(){
//        URL urlImage = null;
//        try {
//            urlImage = new URL(c.getString("logo"));
//            bitmap = BitmapFactory.decodeStream(urlImage.openConnection().getInputStream());
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    private String imageToString(Bitmap bitmap){
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
        byte[] imgBytes = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(imgBytes,Base64.DEFAULT);

    }
}
