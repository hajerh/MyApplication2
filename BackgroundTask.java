package com.pidev.esprit.pidevmobile;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.pidev.esprit.pidevmobile.Entities.Article;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by esprit on 23/11/2017.
 */

public class BackgroundTask {
    Context context;
    public ArrayList<Article> listArticles  =  new ArrayList<>();
    public String url = "http://10.0.2.2:18080/pidev-web/rest/article";

    public BackgroundTask(Context context) {
        this.context = context;
    }

    public ArrayList<Article> getList () {

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url,null,
                new Response.Listener<JSONArray>(){
                    @Override
                    public void onResponse(JSONArray response) {
                        int count =0;
                        while(count<response.length()) {
                          try {
                               JSONObject jsonObject = response.getJSONObject(count);
                              Article article = new
                                      Article(jsonObject.getString("name"),jsonObject.getString("body"),jsonObject.getString("image"));
                              listArticles.add(article);
                                count++;

                          }  //end try
                          catch (JSONException e) {
                              Toast.makeText(context,"oups server error !!",Toast.LENGTH_SHORT).show();

                              e.printStackTrace();
                          }
                        }
                        System.out.println(listArticles.toString());
                    }
                },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                });
        System.out.println("2");
        MySingleton.getInstance(context).addToRequestQue(jsonArrayRequest);
        System.out.println("3");
        return listArticles;
    }
}
