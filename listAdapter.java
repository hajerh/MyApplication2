package com.example.aziz.pidev;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.aziz.pidev.entity.produit;

import java.util.ArrayList;
import java.util.List;

public class listAdapter extends ArrayAdapter<produit> {

    private int resourceId=0;
    private LayoutInflater inflater;
    TextView productName, productCategorie,productQuantite,productDescripton;
    List<produit> l= new ArrayList<>();

    public listAdapter(Context context,int resourceId,  List<produit> listProduit) {
        super(context, 0, listProduit);
        this.resourceId=resourceId;
        inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
l=listProduit;

    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        View view;
        view =inflater.inflate(resourceId, parent,false);
        try{
            productName= (TextView) view.findViewById(R.id.produitName);
            productCategorie= (TextView) view.findViewById(R.id.produitCategorie);
            productQuantite= (TextView) view.findViewById(R.id.produitQuantite);
            productDescripton= (TextView) view.findViewById(R.id.produitDescription);
            //produit p=new produit("produit", 15, 125.0, "c1","bon produit" );
            productName.setText(l.get(position).getName());
            productCategorie.setText(l.get(position).getCategorie());
            productDescripton.setText(l.get(position).getDescription());
            productQuantite.setText(l.get(position).getQuantity()+"");
        }catch(ClassCastException e)
        {
            throw e;
        }





        return view;
    }


    }

