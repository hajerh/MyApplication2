package com.pidev.esprit.pidevmobile;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by esprit on 22/11/2017.
 */

public class IndexActivity extends AppCompatActivity {

    Button btnAdd,btnList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.index_article);
        Context context = getApplicationContext();
        LinearLayout linear = (LinearLayout) findViewById(R.id.index_linearLayout);
        btnAdd = (Button) findViewById(R.id.btnAddArticle);
        btnList = (Button) findViewById(R.id.btnListArticle);

        btnAdd.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                Intent intent=new Intent(IndexActivity.this, AddArticleActivity.class);
                startActivity(intent);
            }
        });
        btnList.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                Intent intent=new Intent(IndexActivity.this, ListArticlesActivity.class);
                startActivity(intent);
            }
        });



    }

//    private void connection(){
//
//        String url = "http://10.0.2.2:18080/pidev-web/rest/article";
//
//        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, url,null,
//                new Response.Listener<JSONObject>(){
//
//                    @Override
//                    public void onResponse(JSONObject response) {
//                        try {
//                            nom.setText(response.getString("name"));
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//
//
//                },
//                new Response.ErrorListener(){
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        Toast.makeText(IndexActivity.this,"oups server error !!",Toast.LENGTH_SHORT).show();
//                    }
//                });
//                MySingleton.getInstance(IndexActivity.this).addToRequestQue(jsonRequest);
//
//        /*    @Override
//            protected Map<String,String> getParams(){
//                Map<String,String> params = new HashMap<>();
//                params.put("name",nom.getText().toString());
//                params.put("body",body.getText().toString());
//                params.put("other",other.getText().toString());
//                params.put("image","0");
//                params.put("user","1");
//                params.put("date",date.getText().toString());
//                return params;
//            }*/
//
//    }

//    View.OnClickListener sign = new View.OnClickListener() {
//        @Override
//        public void onClick(View v) {
//            connection();
//        }
//
//    };
}
