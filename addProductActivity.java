package com.example.aziz.pidev;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class addProductActivity extends AppCompatActivity {

    EditText name, quantite,categorie,price, descrition;
    private String mJSONURLString = "http://10.0.2.2:18080/pi_deux-web/api/products";
    Button add;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
        name=(EditText) findViewById(R.id.name);
        quantite=(EditText) findViewById(R.id.quantite);
        categorie=(EditText) findViewById(R.id.categorie);
        price=(EditText) findViewById(R.id.price);
        descrition=(EditText) findViewById(R.id.description);
        add=(Button) findViewById(R.id.add);

        add.setOnClickListener(new View.OnClickListener()
                                  {

                                      @Override
                                      public void onClick(View view) {
                                          System.out.println("clicked");

                                          Map<String,String> params = new HashMap<String,String>();
                                          params.put("name",name.getText().toString());
                                          params.put("categorie",categorie.getText().toString());
                                          params.put("description",descrition.getText().toString());
                                          params.put("price",price.getText().toString());
                                          params.put("quantity",quantite.getText().toString());
                                        //  params.put("nameConductor",couponType);

                                          JsonObjectRequest jsonRequest = new JsonObjectRequest (Request.Method.POST, mJSONURLString,new JSONObject(params),
                                                  new Response.Listener<JSONObject>() {

                                                      @Override
                                                      public void onResponse(JSONObject response) {



                                                         // CharSequence text ="inscription reussite , votre compte sera activé dans une certaine délai";

                                                          int duration = Toast.LENGTH_LONG;

                                                          Toast toast = Toast.makeText(getBaseContext(), "product added succes", duration);
                                                          toast.show();
                                                          Intent i= new Intent(getBaseContext(), ListActivity.class);
                                                          startActivity(i);
                                                      }
                                                  },
                                                  new Response.ErrorListener() {
                                                      @Override
                                                      public void onErrorResponse(VolleyError error) {


                                                          int duration = Toast.LENGTH_LONG;

                                                          Toast toast = Toast.makeText(getBaseContext(), "successfully added", duration);
                                                          toast.show();
                                                          Intent i= new Intent(getBaseContext(), ListActivity.class);
                                                          startActivity(i);

                                                      }
                                                  })

                                          {

                                              @Override
                                              public Map<String, String> getHeaders()  {
                                                  HashMap<String, String> headers = new HashMap<String, String>();
                                                  headers.put("Content-Type", "application/json");
                                                  return headers;
                                              }
                                          };

                                          RequestQueue requestQueue = Volley.newRequestQueue(getBaseContext());
                                          requestQueue.add(jsonRequest);

                                  }

                                  }

        );

    }
}
