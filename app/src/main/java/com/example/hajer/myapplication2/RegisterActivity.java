package com.example.hajer.myapplication2;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


public class RegisterActivity extends AppCompatActivity {

    private EditText etEmail;
    private EditText etName;
    private EditText etLastName;
    private EditText etAdress;
    private EditText etphone;
    private EditText etPassword;
    private Button btnRegister;



    String tag_json_obj = "json_obj_req";

    String url = "http://10.0.2.2:18080/pidev-web/rest/userss";

    ProgressDialog pDialog ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_register);

        initUI();

    }

    private void initUI() {
        etName = (EditText) findViewById(R.id.etName);
        etLastName = (EditText) findViewById(R.id.etlastName);
        etAdress  = (EditText) findViewById(R.id.address);
        etEmail = (EditText) findViewById(R.id.etEmail);
        etphone = (EditText) findViewById(R.id.phone);
        etPassword = (EditText) findViewById(R.id.etPassword);

        btnRegister = (Button) findViewById(R.id.btnRegister);



        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               pDialog = new ProgressDialog(RegisterActivity.this);
                // Tag used to cancel the request

                pDialog.setMessage("Loading...");
                pDialog.show();

                // Optional Parameters to pass as POST request
                JSONObject js = new JSONObject();
                try {
                    js.put("firstName",etName.getText().toString());
                    js.put("lastName",etLastName.getText().toString());
                    js.put("adress",etAdress.getText().toString());
                    js.put("mailAdress",etEmail.getText().toString());
                    js.put("phoneNumber",Integer.valueOf(etphone.getText().toString()));
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

                                new Thread() {
                                    @Override
                                    public void run() {
                                        Retrievedata task = new Retrievedata();
                                        task.execute();
                                    }
                                }.start();

                             Intent register = new Intent(RegisterActivity.this,LoginActivitypi.class);
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
                Volley.newRequestQueue(RegisterActivity.this).add(jsonObjReq);



            }
        });
    }



}
class Retrievedata extends AsyncTask<String, Void, String> {
    @Override
    protected String doInBackground(String... params) {
        try {
            // Recipient's email ID needs to be mentioned.
            String to = "volunteeringplatform1@gmail.com";

            // Sender's email ID needs to be mentioned
            String from = "volunteeringplatform1@gmail.com";
            final String username = "volunteeringplatform1@gmail.com";//change accordingly
            final String password = "azerty1234";//change accordingly

            // Assuming you are sending email through relay.jangosmtp.net
            String host = "smtp.gmail.com";

            Properties props = new Properties();
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.host", host);
            props.put("mail.smtp.port", "587");
            props.put("mail.smtp.socketFactory.port", "465");
            props.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
            props.put("mail.smtp.starttls.enable", "true");

            // Get the Session object.
            Session session = Session.getInstance(props,
                    new javax.mail.Authenticator() {
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(username, password);
                        }
                    });

            try {
                // Create a default MimeMessage object.
                Message message = new MimeMessage(session);

                // Set From: header field of the header.
                message.setFrom(new InternetAddress(from));

                // Set To: header field of the header.
                message.setRecipients(Message.RecipientType.TO,
                        InternetAddress.parse(to));

                // Set Subject: header field
                message.setSubject("Testing Subject");

                // Now set the actual message
                message.setText("Hello, this is sample for to check send " +
                        "email using JavaMailAPI ");

                // Send message
                Transport.send(message);

                System.out.println("Sent message successfully....");

            } catch (MessagingException e) {
                throw new RuntimeException(e);
            }
        } catch (Exception e) {
            Log.e("log_tag", "Error in http connection " + e.toString());
        }
        return null;
    }
}