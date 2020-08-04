package com.example.favouritelist;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class CategoryViewHolder extends RecyclerView.ViewHolder {
 private TextView txtCatogeryNumber;
 private TextView txtCatogeryName;
    public CategoryViewHolder(View v){
        super(v);
         txtCatogeryNumber=v.findViewById(R.id.txtCatogoryNumber);
         txtCatogeryName=v.findViewById(R.id.txtCatogeryName);
    }

    public TextView getTxtCatogeryNumber() {
        return txtCatogeryNumber;
    }

    public TextView getTxtCatogeryName() {
        return txtCatogeryName;
    }
}
