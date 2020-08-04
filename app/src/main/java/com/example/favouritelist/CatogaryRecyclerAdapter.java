package com.example.favouritelist;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CatogaryRecyclerAdapter extends RecyclerView.Adapter<CategoryViewHolder> {
   // String[] categories ={"Hobbies","Sports","Games","Food","Travel Place"};

    interface CategoryIsClickedInterface{
        void categoryIsClicked(Category category);
    }

    private ArrayList<Category> categories;
    private CategoryIsClickedInterface categoryIsClickedListener;

    public CatogaryRecyclerAdapter(ArrayList<Category> categories,CategoryIsClickedInterface categoryIsClickedListener) {
        this.categories = categories;
        this.categoryIsClickedListener=categoryIsClickedListener;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        View v=layoutInflater.inflate(R.layout.category_view_holder,parent,false);
        return new CategoryViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, final int position) {
           holder.getTxtCatogeryNumber().setText(position+1+". ");
           holder.getTxtCatogeryName().setText(categories.get(position).getName());

           holder.itemView.setOnClickListener(new View.OnClickListener(){
               @Override
               public void onClick(View view){
                   categoryIsClickedListener.categoryIsClicked(categories.get(position));
               }
           });
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public void addCategory(Category category){
        categories.add(category);
        notifyItemInserted(categories.size()-1);
    }
}
