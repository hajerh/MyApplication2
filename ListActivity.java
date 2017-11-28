package com.example.aziz.pidev;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.*;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.aziz.pidev.entity.produit;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



public class ListActivity extends AppCompatActivity {
    private String mJSONURLString = "http://10.0.2.2:18080/pi_deux-web/api/products";
    private String mJSONURLStringDelete = "http://10.0.2.2:18080/pi_deux-web/api/products";
    ListView l;
    listAdapter listAdapter;
    Button add;
    List<produit> listProduit= new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        l = (ListView) findViewById(R.id.listProduit);

        add=(Button) findViewById(R.id.add);

        Toast.makeText(getBaseContext(), "hello", Toast.LENGTH_LONG);
        final RequestQueue requestQueue = Volley.newRequestQueue(getBaseContext());

        // Initialize a new JsonArrayRequest instance
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                mJSONURLString,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        int count = 0;
                        while (count < response.length()) {
                            try {
                                // Loop through the array elements

                                // Get current json object
                                JSONObject produit = response.getJSONObject(count);
                                Log.i("1", produit.toString());
                                // Get the current student (json object) data
                               /* String firstName = produit.getString("nameInsured");
                                String lastName = produit.getString("nameConductor");
                                String age = produit.getString("tel");*/
                                Toast.makeText(getBaseContext(), produit.toString(), Toast.LENGTH_LONG);
                                listProduit.add(new produit(Integer.parseInt(produit.getString("id")),produit.getString("name"),Integer.parseInt(produit.getString("quantity")), Double.parseDouble(produit.getString("price")), produit.getString("description"),produit.getString("categorie") ));

                                // Display the formatted json data in text view
                                /*mTextView.append(firstName +" " + lastName +"\nAge : " + age);
                                mTextView.append("\n\n");*/
                                count++;
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        listAdapter= new listAdapter(getApplicationContext(), R.layout.activity_list_adapter, listProduit);
                        System.out.println("haithemtest");
                        l.setAdapter(listAdapter);



                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Do something when error occurred
                Log.i("1", "*****");

            }
        }
        );
        // Add JsonArrayRequest to the RequestQueue
        requestQueue.add(jsonArrayRequest);






        l.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                produit selected= listProduit.get(i);

                SharedPreferences preferences= PreferenceManager.getDefaultSharedPreferences(getBaseContext());
                SharedPreferences.Editor editor= preferences.edit();
                editor.putInt("id",selected.getId());
                editor.putString("name",selected.getName());
                editor.putString("categorie",selected.getCategorie());
                editor.putString("description",selected.getDescription());
                editor.putString("quantite",String.valueOf(selected.getQuantity()));
                editor.putString("price",String.valueOf(selected.getPrice()));
                editor.apply();
                String test="";
                preferences.getString("name", test);
                System.out.println(test);

                Intent intent= new Intent(getBaseContext(),DetailActivity.class);
                startActivity(intent);
            }
        });





        add.setOnClickListener(new View.OnClickListener() {

                                   @Override
                                   public void onClick(View view) {
                                       System.out.println("clicked");

                                 Intent intent= new Intent(getBaseContext(),addProductActivity.class);
                                 startActivity(intent);
                                   }
                               }

        );







    /* listProduit.add(new produit("produit", 15, 125.0, "c1","bon produit" ));
        listProduit.add(new produit("produit", 15, 125.0, "c1","bon produit" ));
        listProduit.add(new produit("produit", 15, 125.0, "c1","bon produit" ));
        System.out.println("omar");*/

    }
}
