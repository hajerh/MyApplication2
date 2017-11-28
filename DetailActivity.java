package com.example.aziz.pidev;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.aziz.pidev.entity.produit;

import org.json.JSONObject;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class DetailActivity extends Activity {

    int id;
    String name="";
    String quantite="";
    String price="";
    String description="";
    String categorie="";
    private String mJSONURLStringDelete = "http://10.0.2.2:18080/pi_deux-web/api/products";

    TextView TVname,TVquantite,TVprice,TVcategorie,TVdescription;
    Button delete;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        try{
            TVname= (TextView) findViewById(R.id.name);
            TVquantite= (TextView) findViewById(R.id.quantite);
            TVprice= (TextView) findViewById(R.id.price);
            TVcategorie= (TextView) findViewById(R.id.categorie);
            TVdescription=(TextView) findViewById((R.id.description));
            delete=(Button) findViewById(R.id.delete);

            //produit p=new produit("produit", 15, 125.0, "c1","bon produit" );
            /*productName.setText(l.get(position).getName());
            productCategorie.setText(l.get(position).getCategorie());
            productDescripton.setText(l.get(position).getDescription());
            productQuantite.setText(l.get(position).getQuantity()+"");*/
        }catch(ClassCastException e)
        {
            throw e;
        }
        SharedPreferences preferences= PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        id=preferences.getInt("id",10);
        name= preferences.getString("name","");
        quantite=preferences.getString("quantite","");
        price=preferences.getString("price","");
        description=preferences.getString("description","");
        categorie=preferences.getString("categorie","");

        System.out.println(name+" this name");
        TVname.setText(name);
        TVquantite.setText(quantite);
        TVprice.setText(price);
        TVdescription.setText(description);
        TVcategorie.setText(categorie);
       // produit p= new produit(preferences.getInt("id",100), preferences.getString("name",""), preferences.getFloat("quantite",""));
        final RequestQueue requestQueue = Volley.newRequestQueue(getBaseContext());

        delete.setOnClickListener(new View.OnClickListener()
                                  {

                                      @Override
                                      public void onClick(View view) {
                                          System.out.println("clicked");
                                          //////////////////////////////
                                          AlertDialog alertDialog = new AlertDialog.Builder(DetailActivity.this).create();
                                          alertDialog.setTitle("Alert");
                                          alertDialog.setMessage("Are you sure to delete this product");
                                          alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK",
                                                  new DialogInterface.OnClickListener() {
                                                      public void onClick(DialogInterface dialog, int which) {

                                                          mJSONURLStringDelete = mJSONURLStringDelete+"/"+id;

                                                          JsonObjectRequest request = new  JsonObjectRequest(Request.Method.DELETE,mJSONURLStringDelete,null,
                                                                  new Response.Listener<JSONObject>() {
                                                                      @Override
                                                                      public void onResponse(JSONObject response) {

                                                                          Toast.makeText(getBaseContext(),"Deleted",Toast.LENGTH_SHORT).show();
                                                                          Intent i= new Intent(getBaseContext(), ListActivity.class);
                                                                          startActivity(i);

                                                                      }
                                                                  },
                                                                  new Response.ErrorListener() {
                                                                      @Override
                                                                      public void onErrorResponse(VolleyError error) {
                                                                          Toast.makeText(getBaseContext(),"Deleted",Toast.LENGTH_SHORT).show();
                                                                          Intent i= new Intent(getBaseContext(), ListActivity.class);
                                                                          startActivity(i);

                                                                      }


                                                                  }){

                                                              @Override
                                                              public Map<String, String> getHeaders() throws AuthFailureError {
                                                                  Map<String, String> headers = super.getHeaders();

                                                                  if (headers == null
                                                                          || headers.equals(Collections.emptyMap())) {
                                                                      headers = new HashMap<>();
                                                                  }

                                                                  headers.put("access_token", "access_token");
                                                                  headers.put("username", "username");


                                                                  return headers;
                                                              }
                                                          };

                                                          requestQueue.add(request);
                                                          dialog.dismiss();
                                                      }
                                                  });

                                          alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "cancel",
                                                  new DialogInterface.OnClickListener() {
                                                      public void onClick(DialogInterface dialog, int which) {
                                                          dialog.dismiss();
                                                      }
                                                  });
                                          alertDialog.show();

                                          /////////////////////////////

                                      }
                                  }

        );

    }


}
