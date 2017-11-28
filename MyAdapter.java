package com.pidev.esprit.pidevmobile;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.StringRequest;
import com.facebook.share.ShareApi;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.widget.ShareButton;
import com.facebook.share.widget.ShareDialog;
import com.pidev.esprit.pidevmobile.Entities.Article;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by esprit on 25/11/2017.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    static Activity activity ;

    String idToDelete;
    ArrayList<Article> arrayList = new ArrayList<>();
    private Context context;
    ImageView imageView  ;
    Bitmap bitmap ;
    public ShareDialog shareDialog;
    public MyAdapter(ArrayList<Article> arrayList,Activity activity) {
        this.arrayList = arrayList;
        this.activity = activity;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item,parent,false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        context = parent.getContext();
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        final String quote,name;
        String urlImage="";
        final String x;
        final ImageView imv ;

        holder.id.setText(arrayList.get(position).getId()+"");
        idToDelete = arrayList.get(position).getId()+"".trim();
        holder.name.setText(arrayList.get(position).getName());
        holder.other.setText(arrayList.get(position).getOther());
        holder.body.setText(arrayList.get(position).getBody());
        holder.favourite.setText(arrayList.get(position).isFavourite()+"");
        holder.date.setText(arrayList.get(position).getDate().toString());
        quote=arrayList.get(position).getBody();
        name = arrayList.get(position).getName();
//        urlImage = arrayList.get(position).getImage().toString();

        urlImage= "http://www.fougerolles.fr/images/icones/sante.jpg";

        if(arrayList.get(position).getImage().toString().equals("http://10.0.2.2:8081/upload/secourisme.jpg"))
           urlImage="http://maxsciences.free.fr/Doc/SST/examiner.png";

        if(arrayList.get(position).getImage().toString().equals("http://10.0.2.2:8081/upload/giving.jpg"))
           urlImage="http://sichristianchurch.org/wp-content/uploads/2015/11/Giving-An-Act-Of-Grace.jpg";

        if(arrayList.get(position).getImage().toString().equals("http://10.0.2.2:8081/upload/sante.jpg"))
            urlImage="http://www.fougerolles.fr/images/icones/sante.jpg";

        x=urlImage;

//Share Facebook
        holder.shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareDialog  = new ShareDialog(activity);
                if (ShareDialog.canShow(ShareLinkContent.class)) {
                    ShareLinkContent linkContent = new ShareLinkContent.Builder()
                            .setQuote(name + "  " +quote)
                            .setContentUrl(Uri.parse(x))
                            .build();
                    shareDialog.show(linkContent);
                }
            }
        });

//Get Image
        imageView=holder.image;
        ImageRequest imageRequest = new ImageRequest(urlImage, new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap response) {
                bitmap=response;
                imageView.setImageBitmap(response);
            }
         }, 0, 0, ImageView.ScaleType.CENTER_CROP, null, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println(error.getMessage()  + error);
                error.printStackTrace();
            }
        });

        MySingleton.getInstance(context).addToRequestQue(imageRequest);
//        imv= imageView;
//        holder.image=imv;

        holder.btnRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String url ="http://10.0.2.2:18080/pidev-web/rest/article/"+idToDelete;
                System.out.println(url);
                StringRequest request = new StringRequest(Request.Method.DELETE, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Toast.makeText(activity,"Aricle successfully deleted",Toast.LENGTH_LONG);

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println("Object not deleted   " +error.getMessage() );
                    }
                });
                MySingleton.getInstance(context).addToRequestQue(request);

            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        Button btnRemove;
        Button shareButton;
        ImageView image;
        TextView id, name, body,/*image,*/date,favourite,other;
        public MyViewHolder(View itemView) {
            super(itemView);
            id = (TextView) itemView.findViewById(R.id.tvId);
            name = (TextView) itemView.findViewById(R.id.tvName);
            body = (TextView) itemView.findViewById(R.id.tvBody);
            image = (ImageView) itemView.findViewById(R.id.tvImage);
            date = (TextView) itemView.findViewById(R.id.tvDate);
            favourite = (TextView) itemView.findViewById(R.id.tvFavourite);
            other = (TextView) itemView.findViewById(R.id.tvOther);
            btnRemove = (Button) itemView.findViewById(R.id.btnRemove);
            shareButton = (Button)itemView.findViewById(R.id.fb_share_button);

        }


    }

}
