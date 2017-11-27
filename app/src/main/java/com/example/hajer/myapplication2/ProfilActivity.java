package com.example.hajer.myapplication2;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ProfilActivity extends AppCompatActivity {
    EditText firstname;
    EditText lasttname;
    EditText address;
    EditText mailAddress;
    EditText phoneNumber;
    EditText Password;
    Button btnUpdate;

    String tag_json_obj = "json_obj_req";

    String url = "http://10.0.2.2:18080/pidev-web/rest/userss";

    ProgressDialog pDialog ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);
        firstname = (EditText) findViewById(R.id.fname);
        lasttname = (EditText) findViewById(R.id.lname);
        address = (EditText) findViewById(R.id.address);
        mailAddress = (EditText) findViewById(R.id.mail);
        phoneNumber = (EditText) findViewById(R.id.phone);
        Password = (EditText) findViewById(R.id.password);
        btnUpdate = (Button) findViewById(R.id.btnupdate);


        final Intent intent = getIntent();
        firstname.setText(intent.getStringExtra("firstName"));
        lasttname.setText(intent.getStringExtra("lastName"));
        address.setText(intent.getStringExtra("adress"));
        mailAddress .setText(intent.getStringExtra("mailAdress"));
        phoneNumber.setText(String.valueOf(intent.getIntExtra("phoneNumber",0)));
        Password.setText(intent.getStringExtra("password"));
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                pDialog = new ProgressDialog(ProfilActivity.this);
                // Tag used to cancel the request

                pDialog.setMessage("Loading...");
                pDialog.show();

                // Optional Parameters to pass as POST request
                JSONObject js = new JSONObject();
                try {
                    Intent intent = getIntent();
                    js.put("id",intent.getIntExtra("id",0));
                    js.put("firstName",firstname.getText().toString());
                    js.put("lastName",lasttname.getText().toString());
                    js.put("adress",address.getText().toString());
                    js.put("mailAdress",mailAddress.getText().toString());
                    js.put("phoneNumber",phoneNumber.getText().toString());
                    js.put("password",Password.getText().toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Intent intent = getIntent();
                url += "/"+intent.getIntExtra("id",0);
                // Make request for JSONObject
                JsonObjectRequest jsonObjReq = new JsonObjectRequest(
                        Request.Method.PUT, url, js,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {

                                pDialog.hide();
                                Intent register = new Intent(ProfilActivity.this,LoginActivitypi.class);
                                startActivity(register);
                                finish();
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        VolleyLog.d("1", "Error: " + error.getMessage());
                        pDialog.hide();
                    }
                }) {

                    /**
                     * Passing some request headers
                     */
                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        HashMap<String, String> headers = new HashMap<String, String>();
                        headers.put("Content-Type", "application/json; charset=utf-8");
                        return headers;
                    }

                };

                // Adding request to request queue
                Volley.newRequestQueue(ProfilActivity.this).add(jsonObjReq);





            }
        });

    }
}
