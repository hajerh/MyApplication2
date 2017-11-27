package com.example.hajer.myapplication2;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

public class MainActivityAssociation extends AppCompatActivity {
    private EditText etNameAss;
    private EditText address;
    private EditText phone;
    private EditText etEmail;
    private EditText etPassword;
    private Button btnRegisterAss;

    String tag_json_obj = "json_obj_req";

    String url = "http://10.0.2.2:18080/pidev-web/rest/userss";

    ProgressDialog pDialog ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.content_main_activity_association);

        initUI();
    }

    private void initUI() {
        etNameAss = (EditText) findViewById(R.id.etNameAss);
        address = (EditText) findViewById(R.id.address);
        phone  = (EditText) findViewById(R.id.phone);
        etEmail = (EditText) findViewById(R.id.etEmail);
        etPassword = (EditText) findViewById(R.id.etPassword);

        btnRegisterAss = (Button) findViewById(R.id.btnRegisterAss);
        btnRegisterAss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                pDialog = new ProgressDialog(MainActivityAssociation.this);
                // Tag used to cancel the request

                pDialog.setMessage("Loading...");
                pDialog.show();

                // Optional Parameters to pass as POST request
                JSONObject js = new JSONObject();
                try {
                    js.put("nameAssociation",etNameAss.getText().toString());
                    js.put("adress", address.getText().toString());
                    js.put("phoneNumber",Integer.valueOf(phone.getText().toString()));
                    js.put("mailAdress",etEmail.getText().toString());
                    js.put("password",etPassword.getText().toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                // Make request for JSONObject
                JsonObjectRequest jsonObjReq = new JsonObjectRequest(
                        Request.Method.POST, url, js,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {

                                pDialog.hide();
                            Intent register = new Intent(MainActivityAssociation.this,LoginActivitypi.class);
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
                Volley.newRequestQueue(MainActivityAssociation.this).add(jsonObjReq);



            }
        });
    }

}
