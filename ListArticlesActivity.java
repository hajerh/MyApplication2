package com.pidev.esprit.pidevmobile;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.facebook.CallbackManager;
import com.pidev.esprit.pidevmobile.Entities.Article;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by esprit on 23/11/2017.
 */

public class ListArticlesActivity extends AppCompatActivity {

    Date date1;

    ListView lvArticles;
    int count =0;
    String x= "name: ";
    ArrayList<Article> ArticlesList =  new ArrayList<>();
    ArrayAdapter arrayAdapter;
    RecyclerView rv;
    RecyclerView.LayoutManager rvlayout;
    RecyclerView.Adapter adap;
    CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

       super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_list);
       rv = (RecyclerView) findViewById(R.id.articleListView);
       rvlayout = new LinearLayoutManager(this);
       rv.setLayoutManager(rvlayout);
       rv.setHasFixedSize(true);
       //lvArticles= (ListView) findViewById(R.id.articleListView);
        SimpleDateFormat daetFormat = new SimpleDateFormat("dd/MM/yyyy");


        try {
            date1= daetFormat.parse("01/01/2016");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String url = "http://10.0.2.2:18080/pidev-web/rest/article";
       JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url,null,
               new Response.Listener<JSONArray>(){
                   @Override
                   public void onResponse(JSONArray response) {

                       while(count<response.length()) {
                           try {
                               JSONObject jsonObject = response.getJSONObject(count);
                               //String dateStr = jsonObject.getString("date");


//                               (String name, String body, String image, Date date, boolean favourite, String other)
                               Article article = new
                                       Article(jsonObject.getInt("id"),jsonObject.getString("name"),jsonObject.getString("body"),jsonObject.getString("image"),date1,jsonObject.getBoolean("favourite"),jsonObject.getString("other"));
                               //System.out.println(article.toString());

                               ArticlesList.add(article);
                               count++;

                           }  //end try
                           catch (JSONException e) {
                               Toast.makeText(ListArticlesActivity.this,"oups server error !!",Toast.LENGTH_SHORT).show();

                               e.printStackTrace();
                           }
                       }

                        adap = new MyAdapter(ArticlesList,ListArticlesActivity.this);
                       rv.setAdapter(adap);

////                       Intent intent=new Intent(ListArticlesActivity.this, IndexActivity.class);
//                       startActivity(intent);
                   }
               },
               new Response.ErrorListener(){
                   @Override
                   public void onErrorResponse(VolleyError error) {
                       System.out.println(error.getMessage());
                       error.printStackTrace();
                   }
               });
       MySingleton.getInstance(ListArticlesActivity.this).addToRequestQue(jsonArrayRequest);



    }
    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

}
