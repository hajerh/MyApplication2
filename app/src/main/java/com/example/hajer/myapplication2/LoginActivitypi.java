package com.example.hajer.myapplication2;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
/**
 * A login screen that offers login via email/password.
 */
public class LoginActivitypi extends AppCompatActivity  {


    private EditText etEmail;
    private EditText etPassword;
    private Button btnLogin;
    private TextView signinUser;
    private TextView signinAssociation;

    String tag_json_arry = "json_array_req";
    String url = "http://10.0.2.2:18080/pidev-web/rest/userss/login";

    ProgressDialog pDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_activitypi);
        // Tag used to cancel the request
        pDialog = new ProgressDialog(LoginActivitypi.this);

        signinUser =(TextView) findViewById(R.id.sign_in_user);
        signinAssociation =(TextView) findViewById(R.id.sign_in_association);

        btnLogin = (Button) findViewById(R.id.login);
        etEmail = (EditText) findViewById(R.id.etEmail);
        etPassword = (EditText) findViewById(R.id.etPassword);

        signinUser.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent register = new Intent(LoginActivitypi.this,RegisterActivity.class);
                startActivity(register);
                finish();
            }
        });
        signinAssociation.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent regAssociation = new Intent(LoginActivitypi.this,MainActivityAssociation.class);
                startActivity(regAssociation);
                finish();
            }
        });
        btnLogin.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {



                // Optional Parameters to pass as POST request
                JSONObject js = new JSONObject();
                try {
                    js.put("mailAdress",etEmail.getText().toString());
                    js.put("password",etPassword.getText().toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                url=url+"/"+etEmail.getText().toString()+"/"+etPassword.getText().toString();
                // Make request for JSONObject
                JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                        url, null,
                        new Response.Listener<JSONObject>() {

                            @Override
                            public void onResponse(JSONObject response) {
                                pDialog.hide();
                                Intent profil = new Intent(LoginActivitypi.this,ProfilActivity.class);
                                try {
                                    profil.putExtra("id",response.getInt("id"));
                                    profil.putExtra("firstName",response.getString("firstName"));
                                    profil.putExtra("lastName",response.getString("lastName"));
                                    profil.putExtra("adress",response.getString("adress"));
                                    profil.putExtra("mailAdress",response.getString("mailAdress"));
                                    profil.putExtra("phoneNumber",response.getInt("phoneNumber"));
                                    profil.putExtra("password",response.getString("password"));

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                url = "http://10.0.2.2:18080/pidev-web/rest/userss/login";
                                startActivity(profil);
                                finish();

                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(LoginActivitypi.this,"login failed",Toast.LENGTH_LONG).show();
                        url = "http://10.0.2.2:18080/pidev-web/rest/userss/login";
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
                Volley.newRequestQueue(LoginActivitypi.this).add(jsonObjReq);
            }
        });





    }

}

